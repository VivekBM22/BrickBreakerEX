package brickBreakerEX;

import java.util.LinkedList;
import java.util.ListIterator;
import Vector2D.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

class GameEngine {
	final static Color GAME_BG_COLOR = Color.LIGHTGREEN;
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
	
	private final int UPPER_WALL = 10;
	private final int LEFT_WALL = 10;
	private final int RIGHT_WALL = GAME_LENGTH - 10;
	private final int BOTTOM_LIMIT = GAME_HEIGHT - 10;
	
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
	private long startTime;
	private long lastNanoTime;
	private long elapsedNanoTime = 0;
	private long totalPauseTime = 0;
	private long totalWaitTime = 0;
	private int frameCount = 0;
	private long pauseTime;
	private long waitTime;
	Boolean pauseRequest = false;
	Boolean waitRequest = false;
	private int status = 0;
	
	private int level;
	private int mode;
	
	LinkedList<Ball> ballList;
	Paddle paddle;
	LinkedList<Brick> brickList;
	ListIterator<Ball> ballIter;
	ListIterator<Brick> brickIter;
	
	private int lives = 3;
	private int displayedLives = 3;
	private int damage;
	
	StackPane canvasPane;
	GraphicsContext bgGC, ballGC, brickPaddleGC, UIGC;
	Canvas bgCanvas, ballCanvas, brickPaddleCanvas, UICanvas;
	AnimationTimer gameTimer, waitTimer, levelAnimTimer;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	static {
		try {
			bgImg = new Image("Theme " + 1 + "/Background.png");
		}
		catch(IllegalArgumentException iae) {
			bgImg = null;
		}
		
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
		}
	}
	
	GameEngine(Label timeLabel) {
		status = INITIAL;
		
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
		
		UICanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		UIGC = UICanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(UICanvas);
		
		gameTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				timeLabel.setText(Long.toString(getGameTime(currentNanoTime)/1000000000) + "s");
				updateGame(currentNanoTime);
				drawFrame(currentNanoTime);
				pauseGame();
				startCountdown();
				levelCheck();
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
			
		this.levelAnimTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				String displayStr = "LEVEL " + level;
				
				UIGC.fillText(displayStr, GAME_LENGTH/2 - displayStr.length()*0.5*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2 - LEVEL_FONT_SIZE*0.5);
				if((currentNanoTime - waitTime)/1000000000 >= 1) {
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
	}
	
	StackPane getCanvasPane() {
		return canvasPane;
	}
	
	void setDamage(int damage) {
		this.damage = damage;
	}
	
	Boolean isPaused() {
		if(status == PAUSED)
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
	
	int paddleBallCollide(Paddle paddle, Ball ball) {
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
				overlap = ballMaxProj - paddleMinProj;
				mtv = axis1.getReversed();
				backtrack = overlap;
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
				overlap = paddleMaxProj - ballMinProj;
				mtv = axis2;
				backtrack = overlap;
			}
			if(ballMaxProj - paddleMinProj < overlap) {
				reflect = false;
				overlap = ballMaxProj - paddleMinProj;
				mtv = axis2.getReversed();
				backtrack = overlap;
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
				overlap = ballMaxProj - paddleMinProj;
				mtv = axis3;
				backtrack = -overlap;
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
		else
			ball.move(backtrack, mtv);
		System.out.println("MTV: " + mtv);

		return -1; //0: No Collision, 1: Reflect, -1: No Reflect
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
			bgGC.setFill(GameEngine.GAME_BG_COLOR);
			bgGC.fillRect(0, 0, GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		}
		
		UIGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		
		UIGC.setTextBaseline(VPos.TOP);
		/*UIGC.setFill(TIME_FILL_COLOR);
		UIGC.setFont(TIME_FONT);*/
		
		displayedLives = lives;
		for(int i = 0; i < lives; i++)
			UIGC.drawImage(lifeImg, GAME_LENGTH - (i+1)*(lifeImg.getWidth() + 3), 0);
		
		UIGC.setFill(LEVEL_FILL_COLOR);
		UIGC.setFont(LEVEL_FONT);
		
		GameInfo.getDetails(this, mode, level);
		
		ballGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		brickPaddleGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
		
		ballIter = ballList.listIterator();
		brickIter = brickList.listIterator();
		
		paddle.drawPaddle(brickPaddleGC);
		while(ballIter.hasNext())
			ballIter.next().drawBall(ballGC);
		while(brickIter.hasNext())
			brickIter.next().drawBrick(brickPaddleGC);
		
		System.out.println("Level started");
		
		totalWaitTime = totalPauseTime = elapsedNanoTime = frameCount = 0;
		startTime = lastNanoTime = waitTime = System.nanoTime();
		levelAnimTimer.start();
	}
	
	void startGame(int mode, int level) {
		this.mode = mode;
		if(mode == GameInfo.LEVEL_SELECT_MODE)
			this.level = level;
		else
			this.level = 1;
		
		startLevel();
	}
	
	void updateGame(long curNanoTime) {
		long loopTime = curNanoTime - lastNanoTime;
		double backtrack, newAngle;
		lastNanoTime = System.nanoTime();
		elapsedNanoTime += loopTime;
		
		/*int paddleDir = */paddle.updateCoords(loopTime);
		
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
					brick.reduceHealth(damage);                  //////////////////////////////// Set Damage
					//pause();
				}
			}
			
			//Paddle Collision test
			if((backtrack = paddleBallCollide(paddle, ball)) != 0)
			{
				System.out.println("Ball collided with Paddle having backtrack: " + backtrack);
				if(backtrack == 1) {
					newAngle = paddle.impartVelToBall(ball);
					if(newAngle >= 0)
						System.out.println("Imparted velocity to Ball with new Ball angle: " + newAngle);
				}
				//pause();
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
			String timeStr = getTimeString(getGameTime(curNanoTime));
			UIGC.clearRect(0, 0, TIME_FONT_SIZE*4.8, TIME_FONT_SIZE);
			UIGC.fillText(timeStr, 0, 0);
			
			for(; displayedLives < lives; displayedLives++) {
				UIGC.drawImage(lifeImg, GAME_LENGTH - (displayedLives+1)*(lifeImg.getWidth() + 3), 0);
			}
			
			for(; displayedLives > lives; displayedLives--) {
				UIGC.clearRect(GAME_LENGTH - (displayedLives)*(lifeImg.getWidth() + 3), 0, lifeImg.getWidth(), lifeImg.getHeight());
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
			if(brickList.isEmpty()) {
				status = WON;
				UIGC.setFill(WIN_FILL_COLOR);
				UIGC.setFont(WIN_FONT);
				UIGC.fillText("YOU WIN", GAME_LENGTH/2 - 3.5*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2.0 - WIN_FONT_SIZE/2.0);
			}
			
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
				if(lives > 0) {
					GameInfo.getBall(this);
					setCountdown();
				}
				else {
					status = LOST;
					UIGC.setFill(LOSE_FILL_COLOR);
					UIGC.setFont(LOSE_FONT);
					UIGC.fillText("YOU LOSE", GAME_LENGTH/2 - 4*LEVEL_FONT_SIZE*0.5625, GAME_HEIGHT/2.0 - LOSE_FONT_SIZE/2.0);
				}
			}
	
			paddle.erasePaddle(brickPaddleGC);
			paddle.drawPaddle(brickPaddleGC);
			
			elapsedNanoTime -= NANO_FRAME_TIME;
			frameCount ++;
		}
		if(frameCount%200 == 0) {
			System.out.println("200 frames printed");
		}
	}
	
	void levelCheck() {
		if(status == WON) {
			//////////////////////////////////////////// Record level details
			gameTimer.stop();
			if(mode != GameInfo.LEVEL_SELECT_MODE && level < GameInfo.LEVEL_COUNT) {
				level++;
				long curTime = System.nanoTime();
				new AnimationTimer() {
					public void handle(long currentNanoTime) {
						if((currentNanoTime - curTime)/1000000000 >= 3) {
							stop();
							startLevel();
						}
					}
				}.start();
			}
			else {
				//////////////////////////////////////////////// Record game details to LeaderBoard
			}
		}
		else if(status == LOST) {
			//////////////////////////////////////////// Record level details
			gameTimer.stop();
			//////////////////////////////////////////// Record game details to LeaderBoard
		}
	}
};