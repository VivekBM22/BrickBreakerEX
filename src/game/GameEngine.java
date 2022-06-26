package game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import Vector2D.Vector2D;
import highScore.HighScore;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

class GameEngine {
	final static int GAME_LENGTH = 1280;
	final static int GAME_HEIGHT = 720;
	final static int FRAME_RATE = 60;
	final static long NANO_FRAME_TIME = 1000000000/FRAME_RATE;
	
	final static int IN_GAME = 0;
	final static int WON = 1;
	final static int LOST = 2;
	final static int INITIAL = 3;
	final static int PAUSED = 4;
	final static int WAITING = 5;
	
	static final int UPPER_WALL = 10;
	static final int LEFT_WALL = 10;
	static final int RIGHT_WALL = GAME_LENGTH - 10;
	static final int BOTTOM_LIMIT = GAME_HEIGHT - 10;
	
	private static Image lifeImg;
	private final static Color TIME_FILL_COLOR = Color.BLACK;
	private final static int TIME_FONT_SIZE = 25;
	private final static Font TIME_FONT = Font.font("Courier New", FontWeight.BOLD, FontPosture.REGULAR, TIME_FONT_SIZE);
	private final static Color COUNTDOWN_FILL_COLOR = Color.GREENYELLOW;
	private final static int COUNTDOWN_FONT_SIZE = 50;
	private final static Font COUNTDOWN_FONT = Font.font("Courier New", FontWeight.BOLD, FontPosture.REGULAR, COUNTDOWN_FONT_SIZE);
	private final static Color LEVEL_FILL_COLOR = Color.GREENYELLOW;
	private final static int LEVEL_FONT_SIZE = 50;
	private final static Font LEVEL_FONT = Font.font("Courier New", FontWeight.BOLD, FontPosture.REGULAR, LEVEL_FONT_SIZE);
	private final static Color WIN_FILL_COLOR = Color.BLACK;
	private final static int WIN_FONT_SIZE = 50;
	private final static Font WIN_FONT = Font.font("Courier New", FontWeight.BOLD, FontPosture.REGULAR, WIN_FONT_SIZE);
	private final static Color LOSE_FILL_COLOR = Color.BLACK;
	private final static int LOSE_FONT_SIZE = 50;
	private final static Font LOSE_FONT = Font.font("Courier New", FontWeight.BOLD, FontPosture.REGULAR, LOSE_FONT_SIZE);
	
	private static Image bgImg;
	private static Color bgColor;
	
	private long startTime;
	private long levelTime;
	private long lastNanoTime;
	private long elapsedNanoTime;
	private long totalPauseTime;
	private long totalWaitTime;
	private long powerUpSpawnTime;
	private long lastPUSpawnTime;
	private int frameCount = 0;
	private long pauseTime;
	private long waitTime;
	private boolean pauseRequest = false;
	private boolean waitRequest = false;
	
	private int status;
	private int level;
	private int mode;
	private Random powerUpIDGenerator;
	
	LinkedList<Ball> ballList;
	Paddle paddle;
	LinkedList<Brick> brickList;
	ListIterator<Ball> ballIter;
	ListIterator<Brick> brickIter;
	PowerUp powerUp;
	private int lives;
	private int damage;
	private int displayedLives = 3;
	
	LevelInfo[] levelInfo;
	int livesLost;
	
	StackPane canvasPane;
	GraphicsContext bgGC, ballGC, brickPaddleGC, powerUpGC, UIGC;
	Canvas bgCanvas, ballCanvas, brickPaddleCanvas, powerUpCanvas, UICanvas;
	AnimationTimer gameTimer, waitTimer, levelTimer;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	static {
		setTheme(1);
		
		try {
			lifeImg = new Image("Life.png");
		}
		catch(IllegalArgumentException iae) {
			lifeImg = null;
		}
	}
	
	static void setTheme(int themeNo) {
		try {
			bgImg = new Image("Theme " + themeNo + "/Background.png");
		}
		catch(IllegalArgumentException npe) {
			bgImg = null;
			bgColor = Color.LIGHTGREEN;;
		}
	}
	
	GameEngine() {
		status = INITIAL;
		levelInfo = new LevelInfo[5];
		
		canvasPane = new StackPane();
		
		bgCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		bgGC = bgCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(bgCanvas);
		
		ballCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		ballGC = ballCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(ballCanvas);
		
		brickPaddleCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		brickPaddleGC = brickPaddleCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(brickPaddleCanvas);
		
		powerUpCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		powerUpGC = powerUpCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(powerUpCanvas);
		
		UICanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		UIGC = UICanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(UICanvas);
		
		gameTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				updateGame(currentNanoTime);
				drawFrame(currentNanoTime);
				pauseGame();
				startCountdown();
				levelCheck(currentNanoTime);
			}
		};
		
		this.waitTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				String displayStr = "START";
				if((currentNanoTime - waitTime)/1000000000 < 1)
					displayStr = "3";
				else if((currentNanoTime - waitTime)/1000000000 < 2)
					displayStr = "2";
				else if((currentNanoTime - waitTime)/1000000000 < 3)
					displayStr = "1";
				
				UIGC.clearRect(GAME_LENGTH/2 - displayStr.length()*0.5*COUNTDOWN_FONT_SIZE*0.5625, GAME_HEIGHT/2 - COUNTDOWN_FONT_SIZE*0.5, COUNTDOWN_FONT_SIZE*4.8, GAME_HEIGHT/2 + COUNTDOWN_FONT_SIZE*0.5);
				
				if((currentNanoTime - waitTime)/1000000000 >= 4) {
					status = IN_GAME;
					UIGC.setFill(TIME_FILL_COLOR);
					UIGC.setFont(TIME_FONT);
					lastNanoTime = currentNanoTime;
					waitTime = currentNanoTime - waitTime;
					totalWaitTime += waitTime;
					gameTimer.start();
					stop();
					displayStr = "";
				}
				
				UIGC.fillText(displayStr, GAME_LENGTH/2 - displayStr.length()*0.5*COUNTDOWN_FONT_SIZE*0.5625, GAME_HEIGHT/2 - COUNTDOWN_FONT_SIZE*0.5);
			}
		};
			
		this.levelTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				String displayStr = "LEVEL " + level;
				
				UIGC.fillText(displayStr, GAME_LENGTH/2 - displayStr.length()*0.5*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2 - LEVEL_FONT_SIZE*0.5);
				if((currentNanoTime - waitTime)/1000000000 >= 2) {
					UIGC.clearRect(GAME_LENGTH/2 - displayStr.length()*0.5*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2 - LEVEL_FONT_SIZE*0.5, LEVEL_FONT_SIZE*4.8, GAME_HEIGHT/2 + LEVEL_FONT_SIZE*0.5);
					waitTime = currentNanoTime - waitTime;
					totalWaitTime += waitTime;
					setCountdown();
					startCountdown();
					stop();
					displayStr = "";
				}
			}
		};
		
		ballList = new LinkedList<Ball>();
		brickList = new LinkedList<Brick>();
		powerUp = null;
	}
	
	StackPane getCanvasPane() {
		return canvasPane;
	}
	
	void setDamage(int damage) {
		this.damage = damage;
	}
	
	void setLevelTime(long levelTime) {
		this.levelTime = levelTime;
	}
	
	void setPUSpawnTime(long powerUpSpawnTime) {
		this.powerUpSpawnTime = powerUpSpawnTime;
	}
	
	void incrementLives() {
		lives++;
	}

	void addBall() {
		GameInfo.getBall(this);
	}
	
	boolean isPaused() {
		if(status == PAUSED)
			return true;
		return false;
	}
	
	boolean isInGame() {
		if(status == IN_GAME)
			return true;
		return false;
	}
	
	long getGameTime(long curNanoTime) {
		return curNanoTime - startTime - totalPauseTime - totalWaitTime;
	}
	
	String getTimeString(long time) {
		String timeStr = new String();
		
		time /= 1000000000;
		long unit = time%60;
		if(unit < 10)
			timeStr = ":0" + Long.toString(unit);
		else
			timeStr = ":" + Long.toString(unit);
		
		time /= 60;
		if(time == 60)
			return "00:00" + timeStr;
		unit = time%60;
		if(unit < 10)
			timeStr = ":0" + Long.toString(unit) + timeStr;
		else
			timeStr = ":" + Long.toString(unit) + timeStr;
		
		time /= 60;
		if(time == 0)
			return "00" + timeStr;
		else if(time < 10)
			return "0" + Long.toString(time) + timeStr;
		
		return Long.toString(time) + timeStr;
	}
	
	double brickBallCollide(Brick brick, Ball ball) {
		double ballCProj, brickAProj, brickBProj, brickCProj, brickDProj;
		double ballMinProj, ballMaxProj, brickMinProj, brickMaxProj;
		double ballX, ballY;
		Vector2D axis1, axis2, axis3, mtv; // mtv -> Minimum Translation Vector
		double overlap, backtrack = 0; // overlap -> Magnitude of mtv
		
		ballX = ball.getXCoord();
		ballY = ball.getYCoord();
		
		//Checking along first axis
		axis1 = brick.getAxis1();
		ballCProj = axis1.dot(ballX, ballY);
		ballMaxProj = ballCProj + Ball.BALL_SIZE/2.0;
		ballMinProj = ballCProj - Ball.BALL_SIZE/2.0;
		brickMaxProj = brickAProj = brick.getAOnN1();
		brickMinProj = brickDProj = brick.getDOnN1();
		if(ballMaxProj < brickMinProj || ballMinProj > brickMaxProj)
			return 0;
		else {
			overlap = brickMaxProj - ballMinProj;
			mtv = axis1;
			if(ballMaxProj - brickMinProj < overlap) {
				overlap = ballMaxProj - brickMinProj;
				mtv = axis1.getReversed();
			}
			backtrack = overlap / Math.abs(mtv.dot(ball.getVelocityUnitVector()));
		}
		
		//Checking along second axis
		axis2 = brick.getAxis2();
		ballCProj = axis2.dot(ballX, ballY);
		ballMaxProj = ballCProj + Ball.BALL_SIZE/2.0;
		ballMinProj = ballCProj - Ball.BALL_SIZE/2.0;
		brickMaxProj = brickBProj = brick.getBOnN2();
		brickMinProj = brickAProj = brick.getAOnN2();
		if(ballMaxProj < brickMinProj || ballMinProj > brickMaxProj)
			return 0;
		else {
			if(brickMaxProj - ballMinProj < overlap) {
				overlap = brickMaxProj - ballMinProj;
				mtv = axis2;
			}
			if(ballMaxProj - brickMinProj < overlap) {
				overlap = ballMaxProj - brickMinProj;
				mtv = axis2.getReversed();
			}
			backtrack = overlap / Math.abs(mtv.dot(ball.getVelocityUnitVector()));
		}
		
		//Checking along third axis
		axis3 = brick.normalToClosestCorner(ballX, ballY);
		ballCProj = axis3.dot(ballX, ballY);
		ballMaxProj = ballCProj + Ball.BALL_SIZE/2.0;
		ballMinProj = ballCProj - Ball.BALL_SIZE/2.0;
		brickMinProj = brickAProj = axis3.dot(brick.getAX(), brick.getAY());
		brickBProj = axis3.dot(brick.getBX(), brick.getBY());
		brickCProj = axis3.dot(brick.getCX(), brick.getCY());
		brickDProj = axis3.dot(brick.getDX(), brick.getDY());
		if(brickBProj < brickMinProj)
			brickMinProj = brickBProj;
		if(brickCProj < brickMinProj)
			brickMinProj = brickCProj;
		if(brickDProj < brickMinProj)
			brickMinProj = brickDProj;
		
		if(brickMinProj == brickAProj)
			brickMaxProj = brickCProj;
		else if(brickMinProj == brickBProj)
			brickMaxProj = brickDProj;
		else if(brickMinProj == brickCProj)
			brickMaxProj = brickAProj;
		else
			brickMaxProj = brickBProj;
		if(ballMaxProj < brickMinProj || ballMinProj > brickMaxProj)
			return 0;
		else {
			if(ballMaxProj - brickMinProj < overlap) {
				overlap = ballMaxProj - brickMinProj;
				mtv = axis3;
				backtrack = overlap / Math.abs(mtv.dot(ball.getVelocityUnitVector()));
			}
		}
		
		ball.move(-backtrack);
		double newAngle = ((2 * Math.atan2(mtv.x,mtv.y)) - ball.getAngle())%(2 * Math.PI);
		if(newAngle < 0)
			newAngle += 2 * Math.PI;
		ball.setAngle(newAngle);
		ball.move(backtrack);
		
		return backtrack;
	}
	
	int paddleBallCollide(Ball ball) {
		double ballCProj, paddleAProj, paddleBProj, paddleCProj, paddleDProj;
		double ballMinProj, ballMaxProj, paddleMinProj, paddleMaxProj;
		double ballX, ballY;
		Vector2D axis1, axis2, axis3, mtv; // mtv -> Minimum Translation Vector
		double overlap, backtrack = 0; // overlap -> Magnitude of mtv
		boolean reflect = false;
		
		ballX = ball.getXCoord();
		ballY = ball.getYCoord();
		
		//Checking along first axis
		axis1 = new Vector2D(0, -1);
		ballMaxProj = -ballY + Ball.BALL_SIZE/2.0;
		ballMinProj = -ballY - Ball.BALL_SIZE/2.0;
		paddleMaxProj = paddleAProj = -paddle.getAY();
		paddleMinProj = paddleDProj = -paddle.getDY();
		if(ballMaxProj < paddleMinProj || ballMinProj > paddleMaxProj)
			return 0;
		else {
			reflect = true;
			overlap = paddleMaxProj - ballMinProj;
			mtv = axis1;
			backtrack = overlap / Math.abs(mtv.dot(ball.getVelocityUnitVector()));
			if(ballMaxProj - paddleMinProj < overlap) {
				reflect = false;
				/*overlap = ballMaxProj - paddleMinProj;
				mtv = axis1.getReversed();
				backtrack = overlap;*/
			}
		}
		
		//Checking along second axis
		axis2 = new Vector2D(1, 0);
		ballMaxProj = ballX + Ball.BALL_SIZE/2.0;
		ballMinProj = ballX - Ball.BALL_SIZE/2.0;
		paddleMaxProj = paddleBProj = paddle.getBX();
		paddleMinProj = paddleAProj = paddle.getAX();
		if(ballMaxProj < paddleMinProj || ballMinProj > paddleMaxProj)
			return 0;
		else {
			if(paddleMaxProj - ballMinProj < overlap) {
				reflect = false;
				/*overlap = paddleMaxProj - ballMinProj;
				mtv = axis2;
				backtrack = overlap;*/
			}
			if(ballMaxProj - paddleMinProj < overlap) {
				reflect = false;
				/*overlap = ballMaxProj - paddleMinProj;
				mtv = axis2.getReversed();
				backtrack = overlap;*/
			}
		}
		
		//Checking along third axis
		axis3 = paddle.normalToClosestCorner(ballX, ballY);
		ballCProj = axis3.dot(ballX, ballY);
		ballMaxProj = ballCProj + Ball.BALL_SIZE/2.0;
		ballMinProj = ballCProj - Ball.BALL_SIZE/2.0;
		paddleMinProj = paddleAProj = axis3.dot(paddle.getAX(), paddle.getAY());
		paddleBProj = axis3.dot(paddle.getBX(), paddle.getBY());
		paddleCProj = axis3.dot(paddle.getCX(), paddle.getCY());
		paddleDProj = axis3.dot(paddle.getDX(), paddle.getDY());
		if(paddleBProj < paddleMinProj)
			paddleMinProj = paddleBProj;
		if(paddleCProj < paddleMinProj)
			paddleMinProj = paddleCProj;
		if(paddleDProj < paddleMinProj)
			paddleMinProj = paddleDProj;
		
		if(paddleMinProj == paddleAProj)
			paddleMaxProj = paddleCProj;
		else if(paddleMinProj == paddleBProj)
			paddleMaxProj = paddleDProj;
		else if(paddleMinProj == paddleCProj)
			paddleMaxProj = paddleAProj;
		else
			paddleMaxProj = paddleBProj;
		if(ballMaxProj < paddleMinProj || ballMinProj > paddleMaxProj)
			return 0;
		else {
			if(ballMaxProj - paddleMinProj < overlap) {
				reflect = false;
				/*overlap = ballMaxProj - paddleMinProj;
				mtv = axis3;
				backtrack = -overlap;*/
			}
		}
		
		if(reflect)
		{
			ball.move(-backtrack);
			double newAngle = ((2 * Math.atan2(mtv.x,mtv.y)) - ball.getAngle())%(2 * Math.PI);
			if(newAngle < 0)
				newAngle += 2 * Math.PI;
			ball.setAngle(newAngle);
			ball.move(backtrack);
			System.out.println("MTV: " + mtv);
			
			return 1;
		}
		else {
			//ball.move(backtrack, mtv);
			ball.setY(paddle.getY() + (int)(Ball.BALL_SIZE_2 + Paddle.PADDLE_HEIGHT_2));
		}
		System.out.println("MTV: " + mtv);

		return -1; //0: No Collision, 1: Reflect, -1: No Reflect
	}
	
	boolean paddlePowerUpCollide() {
		// Perform Axis-Aligned Bounding-Box collision test
		if (powerUp.getX() < paddle.getBX() && powerUp.getX() + PowerUp.POWERUP_LENGTH > paddle.getAX() &&  powerUp.getY() < paddle.getCY() && powerUp.getY() + PowerUp.POWERUP_HEIGHT > paddle.getAY())
		        return true;
		
		return false;
	}
	
	void pause() {
		if(status == IN_GAME)
			pauseRequest = true;
	}
	
	void unpause() {
		pauseTime = System.nanoTime() - pauseTime;
		gameTimer.start();
		totalPauseTime += pauseTime;
		lastNanoTime += pauseTime;
		status = IN_GAME;
	}
	
	long getPauseTime() {
		if(status == PAUSED)
			return totalPauseTime + System.nanoTime() - pauseTime;
		return totalPauseTime;
	}
	
	void pauseGame() {
		if(status != IN_GAME)
			pauseRequest = false;
		
		if(pauseRequest) {
			gameTimer.stop();
			pauseTime = System.nanoTime();
			status = PAUSED;
			pauseRequest = false;
			System.out.println("Animation paused");
		}
	}
	
	void setCountdown() {
		waitRequest = true;
	}
	
	void startCountdown() {
		if(waitRequest && status != WAITING) {
			gameTimer.stop();
			UIGC.setFill(COUNTDOWN_FILL_COLOR);
			UIGC.setFont(COUNTDOWN_FONT);
			waitTime = System.nanoTime();
			status = WAITING;
			waitRequest = false;
			waitTimer.start();
			System.out.println("Countdown started");
		}
	}
	
	void startLevel() {
		GameEngine.setTheme(level);
		Ball.setTheme(level);
		Paddle.setTheme(level);
		Brick.setTheme(level);
		
		if(bgImg != null)
			bgGC.drawImage(bgImg, 0, 0);
		else {
			bgGC.setFill(GameEngine.bgColor);
			bgGC.fillRect(0, 0, GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		}
		
		UIGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		
		displayedLives = lives;
		for(int i = 0; i < lives; i++)
			UIGC.drawImage(lifeImg, GAME_LENGTH - (i+1)*(lifeImg.getWidth() + 3), 0);
		
		powerUpIDGenerator = new Random();
		powerUpGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		powerUp = null;
		
		GameInfo.getDetails(this, mode, level);
		damage = 1;
		livesLost = 0;
		
		ballGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		brickPaddleGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		
		ballIter = ballList.listIterator();
		brickIter = brickList.listIterator();
		
		paddle.drawPaddle(brickPaddleGC);
		while(ballIter.hasNext())
			ballIter.next().drawBall(ballGC);
		while(brickIter.hasNext())
			brickIter.next().drawBrick(brickPaddleGC);
		
		totalWaitTime = totalPauseTime = elapsedNanoTime = lastPUSpawnTime = frameCount = 0;
		startTime = lastNanoTime = waitTime = System.nanoTime();
		
		UIGC.setTextBaseline(VPos.TOP);
		UIGC.setFill(TIME_FILL_COLOR);
		UIGC.setFont(TIME_FONT);
		
		String timeStr;
		if(mode == GameInfo.TIME_TRIAL_MODE)
			timeStr = getTimeString(levelTime*1000000000 - getGameTime(startTime));
		else
			timeStr = getTimeString(getGameTime(startTime));
		UIGC.clearRect(0, 0, TIME_FONT_SIZE*4.8, TIME_FONT_SIZE);
		UIGC.fillText(timeStr, 0, 0);
		
		UIGC.setFill(LEVEL_FILL_COLOR);
		UIGC.setFont(LEVEL_FONT);
		
		System.out.println("Level started");
		levelTimer.start();
	}
	
	void startGame(int mode, int level) {
		this.mode = mode;
		lives = 3;
		if(mode == GameInfo.LEVEL_SELECT_MODE)
			this.level = level;
		else
			this.level = 1;
		
		startLevel();
	}
	
	void updateGame(long curNanoTime) {
		long loopTime = curNanoTime - lastNanoTime;
		double backtrack, newAngle;
		lastNanoTime = curNanoTime;
		elapsedNanoTime += loopTime;
		
		//Power_up Spawn
		if(powerUp == null)
			lastPUSpawnTime += loopTime;
		if(lastPUSpawnTime/1000000000.0 >= powerUpSpawnTime) {
			lastPUSpawnTime -= powerUpSpawnTime*1000000000;
			
			int powerUpID = powerUpIDGenerator.nextInt(4);
			powerUp = new PowerUp(powerUpID, this);
		}
		
		if(powerUp != null) {
			powerUp.updatePowerUp(loopTime);
			if(powerUp.isSpawned() && paddlePowerUpCollide())
				powerUp.activatePowerUp();
		}
		
		paddle.updateCoords(loopTime);
		
		ballIter = ballList.listIterator();
		
		//Ball Collisions
		while(ballIter.hasNext()) {
			Ball ball = ballIter.next();
			ball.updateCoords(loopTime);
			
			//Upper Wall Collision test
			backtrack = 0;
			if(ball.getY() - Ball.BALL_SIZE/2.0 < UPPER_WALL) {
				backtrack = (UPPER_WALL - ball.getY() + Ball.BALL_SIZE/2.0) / Math.abs(new Vector2D(0,1).dot(ball.getVelocityUnitVector()));
				ball.move(-backtrack);
				newAngle = (-ball.getAngle())%(2 * Math.PI);
				if(newAngle < 0)
					newAngle += 2 * Math.PI;
				ball.setAngle(newAngle);
				ball.move(backtrack);
			}
			
			//Left Wall Collision test
			backtrack = 0;
			if(ball.getX() - Ball.BALL_SIZE/2.0 < LEFT_WALL) {
				backtrack = (LEFT_WALL - ball.getX() + Ball.BALL_SIZE/2.0) / Math.abs(new Vector2D(0,1).dot(ball.getVelocityUnitVector()));
				ball.move(-backtrack);
				newAngle = (Math.PI - ball.getAngle())%(2 * Math.PI);
				if(newAngle < 0)
					newAngle += 2 * Math.PI;
				ball.setAngle(newAngle);
				ball.move(backtrack);
			}
			
			//Right Wall Collision test
			backtrack = 0;
			if(ball.getX() + Ball.BALL_SIZE/2.0 > RIGHT_WALL) {
				backtrack = (ball.getX() + Ball.BALL_SIZE/2.0 - RIGHT_WALL) / Math.abs(new Vector2D(0,1).dot(ball.getVelocityUnitVector()));
				ball.move(-backtrack);
				newAngle = (Math.PI - ball.getAngle())%(2 * Math.PI);
				if(newAngle < 0)
					newAngle += 2 * Math.PI;
				ball.setAngle(newAngle);
				ball.move(backtrack);
			}
			
			//Brick Collision test
			brickIter = brickList.listIterator();
			while(brickIter.hasNext()) {
				Brick brick = brickIter.next();
				if((backtrack = brickBallCollide(brick, ball)) != 0)
				{
					System.out.println("Ball collided with Brick having backtrack: " + backtrack);
					brick.reduceHealth(damage);
					brickIter = brickList.listIterator();
				}
			}
			
			//Paddle Collision test
			if((backtrack = paddleBallCollide(ball)) != 0)
			{
				System.out.println("Ball collided with Paddle having backtrack: " + backtrack);
				if(backtrack == 1) {
					newAngle = paddle.impartVelToBall(ball);
					if(newAngle >= 0)
						System.out.println("Imparted velocity to Ball with new Ball angle: " + newAngle);
				}
			}
			
			//Ball falls below the limit
			if(ball.getY() > BOTTOM_LIMIT) {
				ball.markAsDestroyed();
				System.out.println("Ball fell off the bottom");
			}
		}

		System.out.println("Loop Time: " + loopTime);
	}
	
	public void drawFrame(long curNanoTime) {
		if(elapsedNanoTime > NANO_FRAME_TIME) {
			String timeStr;
			if(mode == GameInfo.TIME_TRIAL_MODE)
				timeStr = getTimeString(levelTime*1000000000 - getGameTime(curNanoTime));
			else
				timeStr = getTimeString(getGameTime(curNanoTime));
			UIGC.clearRect(0, 0, TIME_FONT_SIZE*4.8, TIME_FONT_SIZE);
			UIGC.fillText(timeStr, 0, 0);
			
			if(powerUp != null) {
				powerUp.erasePowerUp(powerUpGC);
				if(powerUp.isSpawned()) {
					powerUp.drawPowerUp(powerUpGC);
				}
				if(powerUp.isDestroyed()) {
					powerUp.erasePowerUp(powerUpGC);
					powerUp = null;
				}
			}
			
			brickIter = brickList.listIterator();
			while(brickIter.hasNext()) {
				Brick brick = brickIter.next();
				brick.drawBrick(brickPaddleGC);
				if(brick.isDestroyed()) {
					brickIter.remove();
					System.out.println("Deleted brick");
					
				}
			}
			if(brickList.isEmpty())
				status = WON;
			
			ballIter = ballList.listIterator();
			ballGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
			while(ballIter.hasNext()) {
				Ball ball = ballIter.next();
				ball.drawBall(ballGC);
				if(ball.isDestroyed()) {
					ballIter.remove();
					System.out.println("Deleted ball");
				}
			}
			if(ballList.isEmpty()) {
				lives--;
				livesLost++;
				if(lives > 0) {
					GameInfo.resetPaddle(this);
					addBall();
					ballList.get(0).drawBall(ballGC);
					setCountdown();
				}
				else
					status = LOST;
			}
	
			paddle.erasePaddle(brickPaddleGC);
			paddle.drawPaddle(brickPaddleGC);
			
			if(mode == GameInfo.TIME_TRIAL_MODE && levelTime - getGameTime(curNanoTime)/1000000000.0 <= 0.5)
				status = LOST;
			
			if(status == WON) {
				UIGC.setFill(WIN_FILL_COLOR);
				UIGC.setFont(WIN_FONT);
				UIGC.fillText("YOU WIN", GAME_LENGTH/2 - 3.5*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2.0 - WIN_FONT_SIZE/2.0);
			}
			else if(status == LOST) {
				UIGC.setFill(LOSE_FILL_COLOR);
				UIGC.setFont(LOSE_FONT);
				UIGC.fillText("YOU LOSE", GAME_LENGTH/2 - 4*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2.0 - LOSE_FONT_SIZE/2.0);
				
			}
			
			for(; displayedLives < lives; displayedLives++) {
				UIGC.drawImage(lifeImg, GAME_LENGTH - (displayedLives+1)*(lifeImg.getWidth() + 3), 0);
			}
			
			for(; displayedLives > lives; displayedLives--) {
				UIGC.clearRect(GAME_LENGTH - (displayedLives)*(lifeImg.getWidth() + 3), 0, lifeImg.getWidth(), lifeImg.getHeight());
			}
			
			elapsedNanoTime -= NANO_FRAME_TIME;
			frameCount ++;
		}
		if(frameCount%200 == 0) {
			frameCount -= 200;
			System.out.println("200 frames printed");
		}
	}
	
	void levelCheck(long curNanoTime) {
		if(status == WON) {
			if(mode != GameInfo.LEVEL_SELECT_MODE && level <= GameInfo.LEVEL_COUNT)
				levelInfo[level - 1] = new LevelInfo(livesLost, getGameTime(curNanoTime)/1000000);
				
			gameTimer.stop();
			if(mode != GameInfo.LEVEL_SELECT_MODE && level < GameInfo.LEVEL_COUNT) {
				level++;
				new AnimationTimer() {
					public void handle(long currentNanoTime) {
						if((currentNanoTime - curNanoTime)/1000000000 >= 3) {
							stop();
							startLevel();
						}
					}
				}.start();
			}
			else {
				status = INITIAL;
				new AnimationTimer() {
					public void handle(long currentNanoTime) {
						if((currentNanoTime - curNanoTime)/1000000000 >= 3) {
							stop();
							
							if(mode != GameInfo.LEVEL_SELECT_MODE && mode != GameInfo.LEVEL_SELECT_HARD_MODE) {
								System.out.println(HighScore.calcScore(levelInfo, mode));
								////////////////////////////////////// Record game details to LeaderBoard
							}
							SceneController2 sc = new SceneController2();
							try {
								sc.switchToGameOver(canvasPane);
							}
							catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}.start();
			}
		}
		else if(status == LOST) {
			gameTimer.stop();
			status = INITIAL;
			
			new AnimationTimer() {
				public void handle(long currentNanoTime) {
					if((currentNanoTime - curNanoTime)/1000000000 >= 3) {
						stop();
						SceneController2 sc = new SceneController2();
						try {
							sc.switchToGameOver(canvasPane);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
	}
};