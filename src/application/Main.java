package application;

import Vector2D.Vector2D;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;

class Ball {	
	final static int BALL_SIZE = 27;
	final static int BALL_SIZE_2 = BALL_SIZE/2;
	final static Color ballColor = Color.DARKGOLDENROD;
	private static Image ballImg;
	
	private int x;
	private int y;
	private double xCoord;
	private double yCoord;
	private double angle;
	
	private int oldX;
	private int oldY;
	
	private double velocity;
	private double xVelocity;
	private double yVelocity;
	
	static {
		try {
			ballImg = new Image("Theme 1/Ball.png");
		}
		catch(IllegalArgumentException npe) {
			ballImg = null;
		}
	}
	
	static void setTheme(int themeNo) {
		try {
			ballImg = new Image("Theme "+ themeNo + "/Ball.png");
		}
		catch(IllegalArgumentException npe) {
			ballImg = null;
		}
	}
	
	Ball(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		xCoord = x;
		yCoord = y;
		this.angle = angle;
		oldX = x;
		oldY = y;
		xVelocity = 0;
		yVelocity = 0;
	}
	
	void setPos(int X, int Y) {
		x = X;
		y = Y;
	}
	
	void setVelocity(double velocity) {
		this.velocity = velocity;
		xVelocity = velocity * Math.cos(angle);
		yVelocity = velocity * Math.sin(angle);
	}
	
	void setAngle(double angle) {
		this.angle = angle;
		setVelocity(velocity);
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	double getAngle() {
		return angle;
	}
	
	double getVelocity() {
		return velocity;
	}
	
	double getXVelocity() {
		return xVelocity;
	}
	
	double getYVelocity() {
		return yVelocity;
	}

	
	WritableImage getBallImg(Canvas canvas) {
		SnapshotParameters snapParams = new SnapshotParameters();
		snapParams.setViewport(new Rectangle2D(x - BALL_SIZE_2 - 1, y + BALL_SIZE_2 - 2, BALL_SIZE + 2, BALL_SIZE + 2));
		snapParams.setFill(Color.TRANSPARENT);
		return canvas.snapshot(snapParams, null);
	}
	
	void drawBall(GraphicsContext gc) {
		if(ballImg != null) {
			gc.drawImage(ballImg, x - BALL_SIZE_2, y - BALL_SIZE_2, BALL_SIZE, BALL_SIZE);
		}
		else {
			gc.setFill(ballColor);
			gc.setStroke(ballColor);
			gc.stroke();
			gc.fillOval(x - BALL_SIZE_2, y - BALL_SIZE_2, BALL_SIZE, BALL_SIZE);
		}
		oldX = x;
		oldY = y;
	}
	
	void eraseBall(GraphicsContext gc) {
		gc.clearRect(oldX - BALL_SIZE_2, oldY - BALL_SIZE_2, BALL_SIZE, BALL_SIZE);
	}
	
	void updateCoords(long nanoTime) {
		long time = nanoTime / 1000000;
		xCoord += xVelocity * time;
		yCoord -= yVelocity * time;
		x = (int) Math.round(xCoord);
		y = (int) Math.round(yCoord);
	}
};

class Paddle {
	final static int PADDLE_LENGTH = 130;
	final static int PADDLE_LENGTH_2 = PADDLE_LENGTH/2;
	final static int PADDLE_WIDTH = (Ball.BALL_SIZE + 1)/2;
	final static int PADDLE_WIDTH_2 = PADDLE_WIDTH/2;
	final static Color paddleColor = Color.PURPLE;
	private static Image paddleImg;
	
	private int x;
	private int y;
	private double velocity = 1;
	
	private int oldX;
	private double xCoord;
	
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	static {
		try {
			paddleImg = new Image("Theme "+ 1 + "/Paddle.png");
		}
		catch(IllegalArgumentException npe) {
			paddleImg = null;
		}
	}
	
	static void setTheme(int themeNo) {
		try {
			paddleImg = new Image("Theme "+ themeNo + "/Paddle.png");
		}
		catch(IllegalArgumentException npe) {
			paddleImg = null;
		}
	}
	
	Paddle(int x, int y) {
		this.x = x;
		this.y = y;
		xCoord = x;
	}
	
	void setPos(int x, int y) {
		this.x = x;
		this.y = y;
		xCoord = x;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	void setLeft(boolean value) {
		moveLeft = value;
	}
	
	void setRight(boolean value) {
		moveRight = value;
	}

	void drawPaddle(GraphicsContext gc) {
		if(paddleImg != null) {
			;
		}
		else {
			gc.setFill(paddleColor);
			gc.fillRect(x - PADDLE_LENGTH_2, y - PADDLE_WIDTH_2, PADDLE_LENGTH, PADDLE_WIDTH);
		}
		
		oldX = x;
	}
	
	void erasePaddle(GraphicsContext gc) {
		gc.clearRect(oldX - PADDLE_LENGTH_2, y - PADDLE_WIDTH_2, PADDLE_LENGTH, PADDLE_WIDTH);
	}
	
	void updateCoords(long nanoTime) {
		long time = nanoTime / 1000000;
		double displace = velocity * time;
		
		if(moveLeft && !moveRight)
			if(xCoord - displace > PADDLE_LENGTH/2.0)
				xCoord -= displace;
			else
				xCoord = PADDLE_LENGTH/2.0;
		else if(!moveLeft && moveRight)
			if(xCoord + displace < GameEngine.GAME_LENGTH - PADDLE_LENGTH/2.0)
				xCoord += displace;
			else
				xCoord = GameEngine.GAME_LENGTH - PADDLE_LENGTH/2.0;
		
		x = (int) Math.round(xCoord);
	}
};

class Brick {
	final static int BRICK_LENGTH = 128;
	final static int BRICK_LENGTH_2 = BRICK_LENGTH/2;
	final static int BRICK_WIDTH = 27;
	final static int BRICK_WIDTH_2 = BRICK_WIDTH/2;
	
	//Status values
	final static int DESTROYED = 0;
	final static int DAMAGE_HIGH = 1;
	final static int DAMAGE_LOW = 2;
	final static int NO_DAMAGE = 3;
	
	private static Color noDmgColor;
	private static Color dmgLowColor;
	private static Color dmgHighColor;
	
	private static Image noDmgImg;
	private static Image dmgLowImg;
	private static Image dmgHighImg;
	
	private static Image brickImg;
	static Color brickColor;
	
	//Upper-left corner
	private int x;
	private int y;
	
	private double angle;
	
	double[] xCoords = new double[4];
	double[] yCoords = new double[4];
	
	private int health;
	private int maxHealth;
	private int status;
	
	private boolean reDraw;
	
	static {
		noDmgColor = Color.BLUE;
		dmgLowColor = Color.YELLOW;
		dmgHighColor = Color.RED;

		try {
			noDmgImg = new Image("Theme "+ 1 + "/Brick.png");
		}
		catch(IllegalArgumentException npe) {
			noDmgImg = null;
		}
		try {
			dmgLowImg = new Image("Theme "+ 1 + "/BrickLowDmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmgLowImg = null;
		}
		try {
			dmgHighImg = new Image("Theme "+ 1 + "/BrickHighDmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmgHighImg = null;
		}
		
		brickImg = noDmgImg;
		brickColor = noDmgColor;
	}
	
	static void setTheme(int themeNo) {
		noDmgColor = Color.BLUE;
		dmgLowColor = Color.YELLOW;
		dmgHighColor = Color.RED;
		
		try {
			noDmgImg = new Image("Theme "+ themeNo + "/Brick.png");
		}
		catch(IllegalArgumentException npe) {
			noDmgImg = null;
		}
		try {
			dmgLowImg = new Image("Theme "+ themeNo + "/BrickLowDmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmgLowImg = null;
		}
		try {
			dmgHighImg = new Image("Theme "+ themeNo + "/BrickHighDmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmgHighImg = null;
		}
		
		brickImg = noDmgImg;
		brickColor = noDmgColor;
	}
	
	Brick(int x, int y, double angle, int health) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.health = this.maxHealth = health;
		this.status = NO_DAMAGE;
		this.reDraw = true;
		
		double cos = Math.cos(this.angle);
		double sin = Math.sin(this.angle);
		
		xCoords[0] = -BRICK_LENGTH/2.0 * cos - BRICK_WIDTH/2.0 * sin;
		xCoords[1] = BRICK_LENGTH/2.0 * cos - BRICK_WIDTH/2.0 * sin;
		xCoords[3] = -BRICK_LENGTH/2.0 * cos + BRICK_WIDTH/2.0 * sin;
		xCoords[2] = BRICK_LENGTH/2.0 * cos + BRICK_WIDTH/2.0 * sin;
		
		yCoords[0] = BRICK_LENGTH/2.0 * sin - BRICK_WIDTH/2.0 * cos;
		yCoords[1] = -BRICK_LENGTH/2.0 * sin - BRICK_WIDTH/2.0 * cos;
		yCoords[3] = BRICK_LENGTH/2.0 * sin + BRICK_WIDTH/2.0 * cos;
		yCoords[2] = -BRICK_LENGTH/2.0 * sin + BRICK_WIDTH/2.0 * cos;
		
		for(int i = 0; i < 4; i++) {
			xCoords[i] += this.x;
			yCoords[i] += this.y;
			System.out.println(xCoords[i] + ", " + yCoords[i]);
		}
	};
	
	void setPos(int X, int Y) {
		x = X;
		y = Y;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	boolean isDestroyed() {
		if(status == DESTROYED)
			return true;
		return false;
	}
	
	private void setStatus(int status) {
		this.status = status;
		 if(this.status == NO_DAMAGE) {
			brickColor = noDmgColor;
			brickImg = noDmgImg;
		}
		else if(this.status == DAMAGE_LOW) {
			brickColor = dmgLowColor;
			brickImg = dmgLowImg;
		}
		else if(this.status == DAMAGE_HIGH) {
			brickColor = dmgHighColor;
			brickImg = dmgHighImg;
		}
		this.reDraw = true;
	}
	
	void reduceHealth(int damage) {
		health -= damage;
		if(health < 0)
			health = 0;
		
		if(maxHealth == 5) {
			if(health == 4)
				setStatus(DAMAGE_LOW);
			else if(health == 2)
				setStatus(DAMAGE_HIGH);
			else if(health == 0)
				setStatus(DESTROYED);
		}
		else if(maxHealth == 3) {
			if(health == 2)
				setStatus(DAMAGE_LOW);
			else if(health == 1)
				setStatus(DAMAGE_HIGH);
			else if(health == 0)
				setStatus(DESTROYED);
		}
	}

	private void rotateGC(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

	void drawBrick(GraphicsContext gc) {
		if(status == DESTROYED) {
			System.out.println("Erased brick");
			eraseBrick(gc);
			return;
		}
		
		if(reDraw) {
			reDraw = false;
			
			if(brickImg != null) {
				gc.save();
				rotateGC(gc, -angle*180/Math.PI, x, y);
				gc.drawImage(brickImg, x - BRICK_LENGTH_2, y - BRICK_WIDTH_2);
				gc.restore();
			}
			else {
				gc.setFill(brickColor);
				gc.fillPolygon(xCoords, yCoords, 4);
			}
		}
	}
	
	void eraseBrick(GraphicsContext gc) {
		if(reDraw) {
			reDraw = false;
			
			gc.save();
			rotateGC(gc, -angle*180/Math.PI, x, y);
			gc.clearRect(x - BRICK_LENGTH_2, y - BRICK_WIDTH_2, BRICK_LENGTH, BRICK_WIDTH);
			gc.restore();
		}
	}
};

class GameInfo {
	static final int THEME_COUNT = 5;
	static final int STANDARD_MODE = 0;
	static final int HARD_MODE = 1;
	static final int TIME_TRIAL_MODE = 2;
	static final int RANDOM_MODE = 3;
	static final int ENDLESS_MODE = 4;
	static final int LEVEL_NULL = -1;
	
	class BrickInfo {
		int x;
		int y;
		double angle;
		int health;
	}
	
	static void setDetails(GameEngine gameEngine, final int mode, final int level) {
		gameEngine.ballList.clear(); //Power-ups do not carry over
		gameEngine.brickList.clear();
		
		Ball ball = new Ball(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100, Math.PI/3);
		ball.setVelocity(0.5);
		gameEngine.ballList.add(ball);
		
		gameEngine.paddle = new Paddle(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 + Paddle.PADDLE_WIDTH_2 + Ball.BALL_SIZE_2 + 2);
		gameEngine.paddle.setVelocity(0.8);
		
		if(mode == STANDARD_MODE) {
			final int health = 3;
			if(level == 1) {
				ListIterator<Brick> brickIter = gameEngine.brickList.listIterator();
				
				Brick brick = new Brick(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250, Math.PI/6, health);
				brickIter.add(brick);
			}
		}
	}
}


class GameEngine {
	final static Color GAME_BG_COLOR = Color.LIGHTGREEN;
	final static int GAME_LENGTH = 1280;
	final static int GAME_HEIGHT = 720;
	final static int FRAME_RATE = 60;
	final static long NANO_FRAME_TIME = 1000000000/FRAME_RATE;
	
	private static Image lifeImg;
	private final static Color TIME_FILL_COLOR = Color.BLACK;
	private final static int TIME_FONT_SIZE = 25;
	private final static Font TIME_FONT = Font.font("Courier New", FontWeight.BOLD, FontPosture.REGULAR, TIME_FONT_SIZE);
	
	private static Image bgImg;
	private long startTime;
	private long lastNanoTime;
	private long elapsedNanoTime = 0;
	private long totalPauseTime = 0;
	private int frameCount = 0;
	private long pauseTime;
	Boolean pauseRequest = false; 
	private Boolean paused = false;
	
	ArrayList<Ball> ballList;
	Paddle paddle;
	ArrayList<Brick> brickList;
	ListIterator<Ball> ballIter;
	ListIterator<Brick> brickIter;
	
	private int lives = 3;
	private int displayedLives = 3;
	
	GraphicsContext bgGC, ballGC, brickPaddleGC, UIGC;
	Canvas ballCanvas, brickPaddleCanvas;
	AnimationTimer animTimer;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	static {
		try {
			bgImg = new Image("Theme "+ 1 + "/Background.png");
		}
		catch(IllegalArgumentException iae) {
			bgImg = null;
		}
		
		try {
			lifeImg = new Image("life.png");
		}
		catch(IllegalArgumentException iae) {
			lifeImg = null;
		}
	}
	
	static void setTheme(int themeNo) {
		try {
			bgImg = new Image("Theme "+ themeNo + "/Background.png");
		}
		catch(IllegalArgumentException npe) {
			bgImg = null;
		}
	}
	
	GameEngine(GraphicsContext bgGC, Canvas ballCanvas, GraphicsContext ballGC, Canvas brickPaddleCanvas, GraphicsContext brickPaddleGC, GraphicsContext UIGC, AnimationTimer animTimer) {
		this.bgGC = bgGC;
		this.ballCanvas = ballCanvas;
		this.ballGC = ballGC;
		this.brickPaddleCanvas = brickPaddleCanvas;
		this.brickPaddleGC = brickPaddleGC;
		this.UIGC = UIGC;
		this.animTimer = animTimer;
		
		ballList = new ArrayList<Ball>();
		brickList = new ArrayList<Brick>();
	}
	
	Boolean isPaused() {
		return paused;
	}
	
	void pause() {
			pauseRequest = true;
	}
	
	void unpause() {
		pauseTime = System.nanoTime() - pauseTime;
		animTimer.start();
		totalPauseTime += pauseTime;
		lastNanoTime += pauseTime;
		paused = false;
	}
	
	long getPauseTime() {
		if(paused)
			return totalPauseTime + System.nanoTime() - pauseTime;
		return totalPauseTime;
	}
	
	long getGameTime(long curNanoTime) {
		return curNanoTime - startTime - totalPauseTime;
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
	
	void startGame() {
		if(bgImg != null)
			bgGC.drawImage(bgImg, 0, 0);
		else {
			bgGC.setFill(GameEngine.GAME_BG_COLOR);
			bgGC.fillRect(0, 0, GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		}
		
		UIGC.setTextBaseline(VPos.TOP);
		UIGC.setFill(TIME_FILL_COLOR);
		UIGC.setFont(TIME_FONT);
		
		displayedLives = lives;
		for(int i = 0; i < lives; i++) {
			UIGC.drawImage(lifeImg, GAME_LENGTH - (i+1)*(lifeImg.getWidth() + 3), 0);
		}
		
		GameInfo.setDetails(this, GameInfo.STANDARD_MODE, 1);
		
		ballIter = ballList.listIterator();
		brickIter = brickList.listIterator();
		
		paddle.drawPaddle(brickPaddleGC);
		while(ballIter.hasNext())
			ballIter.next().drawBall(ballGC);
		while(brickIter.hasNext())
			brickIter.next().drawBrick(brickPaddleGC);
		
		startTime = lastNanoTime = System.nanoTime();
		animTimer.start();
	}
	
	void updateGame(long curNanoTime) {
		long loopTime = curNanoTime - lastNanoTime;
		lastNanoTime = System.nanoTime();
		elapsedNanoTime += loopTime;
		
		paddle.updateCoords(loopTime);
		
		ballIter = ballList.listIterator();
		
		while(ballIter.hasNext()) {
			Ball ball = ballIter.next();
			ball.updateCoords(loopTime);
			
			//Upper Wall Collision test
			if(ball.getY() < Ball.BALL_SIZE/2 - 1) {
				if((-ball.getAngle() + 2 * Math.PI) > 2 * Math.PI) {
					System.out.println("1. " + -ball.getAngle());
					ball.setAngle(-ball.getAngle());
				}
				else {
					System.out.println("2. " + (-ball.getAngle() + 2 * Math.PI));
					ball.setAngle(-ball.getAngle() + 2 * Math.PI);
				}
				
				System.out.println("X Velocity: " + ball.getXVelocity() + "\nY Velocity: " + ball.getYVelocity());
			}
		}

		System.out.println("Loop Time: " + loopTime);
	}
	
	public void drawFrame(long curNanoTime) {
		if(elapsedNanoTime > NANO_FRAME_TIME) {
			ballIter = ballList.listIterator();
			
			while(ballIter.hasNext()) {
				Ball ball = ballIter.next();
				ball.eraseBall(ballGC);
				ball.drawBall(ballGC);
			}
			
			/*if(frameCount%150 == 0) { // Debugging block
				brickIter = brickList.listIterator();
				while(brickIter.hasNext()) {
					Brick brick = brickIter.next();
					brick.reduceHealth(1);
					//System.out.println("Brick health reduced\nBrick Status: " + brick.status + "\nBrick Redraw: " + brick.reDraw+ "\nBrick Health: " + brick.health);
				}
			}*/
			
			brickIter = brickList.listIterator();
			while(brickIter.hasNext()) {
				Brick brick = brickIter.next();
				brick.drawBrick(brickPaddleGC);
				if(brick.isDestroyed()) {
					brickIter.remove();
					System.out.println("Deleted brick");
				}
			}
			
			paddle.erasePaddle(brickPaddleGC);
			paddle.drawPaddle(brickPaddleGC);
			
			String timeStr = getTimeString(getGameTime(curNanoTime));
			UIGC.clearRect(0, 0, TIME_FONT_SIZE*4.8, TIME_FONT_SIZE);
			UIGC.fillText(timeStr, 0, 0);
			
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
			System.out.println("200 frames printed");
		}
	}
	
	void pauseGame() {
		if(pauseRequest && !paused) {
			animTimer.stop();
			pauseTime = System.nanoTime();
			paused = true;
			pauseRequest = false;
			System.out.println("Animation paused");
		}
	}
	
};

public class Main extends Application {
	Canvas ballCanvas, brickPaddleCanvas, bgCanvas, UICanvas;
	GraphicsContext ballGC, brickPaddleGC, bgGC, UIGC;
	GameEngine gameEngine;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	AnimationTimer animTimer;
	@Override
	public void start(Stage primaryStage) {
		
		StackPane root = new StackPane();
		FlowPane gamePane = new FlowPane();
		
		Scene scene  = new Scene(root, 1300, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test Application 1");
		primaryStage.show();
		
		root.getChildren().add(gamePane);
		
		Button btn = new Button("Test Button");
		gamePane.getChildren().add(btn);
		
		Label timeLabel = new Label();
		gamePane.getChildren().add(timeLabel);
		
		StackPane canvasPane = new StackPane();
		gamePane.getChildren().add(canvasPane);
		
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
		
		animTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				timeLabel.setText(Long.toString(gameEngine.getGameTime(currentNanoTime)/1000000000) + "s");
				gameEngine.updateGame(currentNanoTime);
				gameEngine.drawFrame(currentNanoTime);
				gameEngine.pauseGame();
			}
		};
		
		gameEngine = new GameEngine(bgGC, ballCanvas, ballGC, brickPaddleCanvas, brickPaddleGC, UIGC, animTimer);
		
		gameEngine.startGame();
		
		/*ballImg = gameEngine.ball.getBallImg(ballCanvas);
		ballImgReader = ballImg.getPixelReader();
		
		gc.drawImage(ballImg, 0, 0);*/
		
		Label txt = new Label();
		gamePane.getChildren().add(txt);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if(!gameEngine.isPaused()) {
					gameEngine.pause();
					txt.setText("Animation paused");
				}
				else {
					gameEngine.unpause();
					txt.setText("Animation continued\nTotal Pause Time: " + gameEngine.getPauseTime()/1000000000);
				}
			}
		});
		
		gamePane.requestFocus();
		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if(!gameEngine.isPaused()) {
					if(ke.getCode().equals(KeyCode.LEFT)) {
						gameEngine.paddle.setLeft(true);
						txt.setText("Moving left");
					}
					else if(ke.getCode().equals(KeyCode.RIGHT)) {
						gameEngine.paddle.setRight(true);
						txt.setText("Moving right");
					}
				}
			}
		});
		gamePane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if(ke.getCode().equals(KeyCode.ESCAPE)) {
					if(!gameEngine.isPaused()) {
						gameEngine.pause();
						txt.setText("Animation paused");
					}
					else {
						gameEngine.unpause();
						txt.setText("Animation continued\nTotal Pause Time: " + gameEngine.getPauseTime()/1000000000);
					}
				}
				else if(ke.getCode().equals(KeyCode.LEFT))
					gameEngine.paddle.setLeft(false);
				else if(ke.getCode().equals( KeyCode.RIGHT))
					gameEngine.paddle.setRight(false);
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}