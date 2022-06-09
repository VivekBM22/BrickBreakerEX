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
	final static double BALL_SIZE_2 = BALL_SIZE/2.0;
	final static Color ballColor = Color.DARKGOLDENROD;
	
	final static int NORMAL = 1;
	final static int DESTROYED = 0;
	
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
	
	private int status;
	
	static {
		setTheme(1);
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
		status = NORMAL;
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
	
	double getX() {
		return xCoord;
	}
	
	double getY() {
		return yCoord;
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
	
	Vector2D getVelocityUnitVector() {
		return new Vector2D(Math.cos(angle), Math.sin(angle));
	}

	double getXCoord() {
		return xCoord;
	}
	
	double getYCoord() {
		return yCoord;
	}
	
	boolean isDestroyed() {
		if(status == DESTROYED)
			return true;
		return false;
	}
	
	void markAsDestroyed() {
		status = DESTROYED;
	}
	
	void move(double backtrack) {
		xCoord += backtrack * Math.cos(angle);
		yCoord -= backtrack * Math.sin(angle);
		x = (int) Math.round(xCoord);
		y = (int) Math.round(yCoord);
	}
	
	void move(double backtrack, Vector2D direction) {
		xCoord += backtrack * direction.x;
		yCoord -= backtrack * direction.y;
		x = (int) Math.round(xCoord);
		y = (int) Math.round(yCoord);
	}
	
	WritableImage getBallImg(Canvas canvas) {
		SnapshotParameters snapParams = new SnapshotParameters();
		snapParams.setViewport(new Rectangle2D(x - BALL_SIZE_2 - 1, y + BALL_SIZE_2 - 2, BALL_SIZE + 2, BALL_SIZE + 2));
		snapParams.setFill(Color.TRANSPARENT);
		return canvas.snapshot(snapParams, null);
	}
	
	void drawBall(GraphicsContext gc) {
		if(status == DESTROYED)
			return;
		
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
	final static double PADDLE_LENGTH_2 = PADDLE_LENGTH/2.0;
	final static int PADDLE_WIDTH = (Ball.BALL_SIZE + 1)/2;
	final static double PADDLE_WIDTH_2 = PADDLE_WIDTH/2.0;
	final static Color paddleColor = Color.PURPLE;
	private static Image paddleImg;
	
	private int x;
	private int y;
	private double velocity = 1;
	
	private int oldX;
	private double xCoord;
	private double AY, CY;
	
	private boolean moveLeft = false;
	private boolean moveRight = false;
	
	static {
		setTheme(1);
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
		
		AY = y - PADDLE_WIDTH/2.0;
		CY = y + PADDLE_WIDTH/2.0;
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
	
	double getAX() {
		return xCoord - PADDLE_LENGTH/2.0;
	}
	
	double getBX() {
		return xCoord + PADDLE_LENGTH/2.0;
	}
	
	double getCX() {
		return xCoord + PADDLE_LENGTH/2.0;
	}
	
	double getDX() {
		return xCoord - PADDLE_LENGTH/2.0;
	}
	
	double getAY() {
		return AY;
	}
	
	double getBY() {
		return AY;
	}
	
	double getCY() {
		return CY;
	}
	
	double getDY() {
		return CY;
	}
	
	double getVelocity() {
		return velocity;
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
	
	Vector2D normalToClosestCorner(double x, double y) {
		Vector2D normal = null;
		double AX = getAX();
		double BX = getBX();
		
		double d1 = (x - AX)*(x - AX) + (y - AY)*(y - AY);
		double d2 = (x - BX)*(x - BX) + (y - AY)*(y - AY);
		double d3 = (x - BX)*(x - BX) + (y - CY)*(y - CY);
		double d4 = (x - AX)*(x - AX) + (y - CY)*(y - CY);
		
		double smallest = d1;
		if(d2 < smallest)
			smallest = d2;
		if(d3 < smallest)
			smallest = d3;
		if(d4 < smallest)
			smallest = d4;
		
		if(smallest == d1)
			normal = new Vector2D((AX - x)/Math.sqrt(d1), -(AY - y)/Math.sqrt(d1));
		else if(smallest == d2)
			normal = new Vector2D((BX - x)/Math.sqrt(d2), -(AY - y)/Math.sqrt(d2));
		else if(smallest == d3)
			normal = new Vector2D((BX - x)/Math.sqrt(d3), -(CY - y)/Math.sqrt(d3));
		else
			normal = new Vector2D((AX - x)/Math.sqrt(d4), -(CY - y)/Math.sqrt(d4));
		
		return normal;
	}
	
	double impartVelToBall(Ball ball) {
		double impartFactor = 0.5;
		double newAngle = -1;
		
		if(moveRight && !moveLeft) {
			newAngle = Math.atan(ball.getYVelocity() / (ball.getXVelocity() + velocity*impartFactor));
			if(newAngle < 0)
				newAngle += Math.PI;
			ball.setAngle(newAngle);
		}
		else if(moveLeft && !moveRight) {
			newAngle = Math.atan(ball.getYVelocity() / (ball.getXVelocity() - velocity*impartFactor));
			if(newAngle < 0)
				newAngle += Math.PI;
			ball.setAngle(newAngle);
		}
		
		return newAngle;
	}

	void drawPaddle(GraphicsContext gc) {
		if(paddleImg != null) {
			gc.drawImage(paddleImg, x - PADDLE_LENGTH_2, y - PADDLE_WIDTH_2);
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
	
	int updateCoords(long nanoTime) {
		long time = nanoTime / 1000000;
		int direction = 0; //0: Not moving, 1: Moving right, -1: Moving left
		double displace = velocity * time;
		
		if(moveLeft && !moveRight)
			if(xCoord - displace > PADDLE_LENGTH/2.0) {
				xCoord -= displace;
				direction = -1;
			}
			else {
				xCoord = PADDLE_LENGTH/2.0;
				direction = -1;
			}
		else if(!moveLeft && moveRight)
			if(xCoord + displace < GameEngine.GAME_LENGTH - PADDLE_LENGTH/2.0) {
				xCoord += displace;
				direction = 1;
			}
			else {
				xCoord = GameEngine.GAME_LENGTH - PADDLE_LENGTH/2.0;
				direction = 1;
			}
		
		x = (int) Math.round(xCoord);
		return direction;
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
	
	private Vector2D n1, n2;
	private double aOnN1, dOnN1;
	private double aOnN2, bOnN2; 
	
	static {
		setTheme(1);
	}
	
	static void setTheme(int themeNo) {
		noDmgColor = Color.BLUE;
		dmgLowColor = Color.YELLOW;
		dmgHighColor = Color.RED;
		
		try {
			noDmgImg = new Image("Theme " + themeNo + "/Brick.png");
		}
		catch(IllegalArgumentException npe) {
			noDmgImg = null;
		}
		try {
			dmgLowImg = new Image("Theme " + themeNo + "/BrickLowDmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmgLowImg = null;
		}
		try {
			dmgHighImg = new Image("Theme " + themeNo + "/BrickHighDmg.png");
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
		
		n1 = new Vector2D(-sin, -cos);
		n2 = new Vector2D(cos, -sin);
		
		aOnN1 = n1.dot(xCoords[0], yCoords[0]);
		dOnN1 = n1.dot(xCoords[3], yCoords[3]);
		aOnN2 = n2.dot(xCoords[0], yCoords[0]);
		bOnN2 = n2.dot(xCoords[1], yCoords[1]);
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
	
	double getAX() {
		return xCoords[0];
	}
	
	double getBX() {
		return xCoords[1];
	}
	
	double getCX() {
		return xCoords[2];
	}
	
	double getDX() {
		return xCoords[3];
	}
	
	double getAY() {
		return yCoords[0];
	}
	
	double getBY() {
		return yCoords[1];
	}
	
	double getCY() {
		return yCoords[2];
	}
	
	double getDY() {
		return yCoords[3];
	}
	
	boolean isDestroyed() {
		if(status == DESTROYED)
			return true;
		return false;
	}
	
	Vector2D getAxis1() {
		return n1;
	}
	
	Vector2D getAxis2() {
		return n2;
	}
	
	double getAOnN1() {
		return aOnN1;
	}
	
	double getDOnN1() {
		return dOnN1;
	}
	
	double getAOnN2() {
		return aOnN2;
	}
	
	double getBOnN2() {
		return bOnN2;
	}
	
	Vector2D normalToClosestCorner(double x, double y) {
		Vector2D normal = null;
		
		double d1 = (x - xCoords[0])*(x - xCoords[0]) + (y - yCoords[0])*(y - yCoords[0]);
		double d2 = (x - xCoords[1])*(x - xCoords[1]) + (y - yCoords[1])*(y - yCoords[1]);
		double d3 = (x - xCoords[2])*(x - xCoords[2]) + (y - yCoords[2])*(y - yCoords[2]);
		double d4 = (x - xCoords[3])*(x - xCoords[3]) + (y - yCoords[3])*(y - yCoords[3]);
		
		double smallest = d1;
		if(d2 < smallest)
			smallest = d2;
		if(d3 < smallest)
			smallest = d3;
		if(d4 < smallest)
			smallest = d4;
		
		if(smallest == d1)
			normal = new Vector2D((xCoords[0] - x)/Math.sqrt(d1), -(yCoords[0] - y)/Math.sqrt(d1));
		else if(smallest == d2)
			normal = new Vector2D((xCoords[1] - x)/Math.sqrt(d2), -(yCoords[1] - y)/Math.sqrt(d2));
		else if(smallest == d3)
			normal = new Vector2D((xCoords[2] - x)/Math.sqrt(d3), -(yCoords[2] - y)/Math.sqrt(d3));
		else
			normal = new Vector2D((xCoords[3] - x)/Math.sqrt(d4), -(yCoords[3] - y)/Math.sqrt(d4));
		
		return normal;
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
				gc.drawImage(brickImg, x - BRICK_LENGTH/2, y - BRICK_WIDTH/2);
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
			gc.clearRect(x - BRICK_LENGTH/2, y - BRICK_WIDTH/2, BRICK_LENGTH, BRICK_WIDTH);
			gc.restore();
		}
	}
};

class BrickInfo {
	int x;
	int y;
	double angle;
	BrickInfo(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
	}
}

class BallInfo {
	int x;
	int y;
	double angle;
	double velocity;
}

class PaddleInfo {
	int x;
	int y;
	double velocity;
}

class GameInfo {
	static final int THEME_COUNT = 5;
	static final int STANDARD_MODE = 0;
	static final int HARD_MODE = 1;
	static final int TIME_TRIAL_MODE = 2;
	//static final int RANDOM_MODE = 3;
	//static final int ENDLESS_MODE = 4;
	static final int LEVEL_NULL = -1;
	
	private static ArrayList<BrickInfo> brickInfoList;
	private static ListIterator<BrickInfo> brickInfoIter;
	private static BallInfo ballInfo;
	private static PaddleInfo paddleInfo;
	private static int health;
	
	static {
		readyGameInfo(STANDARD_MODE, 1);
	}
	
	private static void readyGameInfo(final int mode, final int level) {
		BrickInfo bi;
		ballInfo = new BallInfo();
		paddleInfo = new PaddleInfo();
		brickInfoList = new ArrayList<BrickInfo>();
		
		ballInfo.x = GameEngine.GAME_LENGTH/2 + 400;
		ballInfo.y = GameEngine.GAME_HEIGHT - 500;
		ballInfo.angle = -Math.PI/1.1;
		ballInfo.velocity = 0.5;
		
		paddleInfo.x = GameEngine.GAME_LENGTH/2;
		paddleInfo.y =  GameEngine.GAME_HEIGHT - 100 + (int)(Paddle.PADDLE_WIDTH_2) + (int)(Ball.BALL_SIZE_2) + 2;
		paddleInfo.velocity = 0.8;
		
		if(mode == STANDARD_MODE) {
			health = 3;
			if(level == 1) {
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250,  Math.PI/6);
				brickInfoList.add(bi);
			}
		}
		else if(mode == HARD_MODE) {
			health = 5;
		}
	}
	
	static void getBall(GameEngine gameEngine) {
		Ball ball = new Ball(ballInfo.x, ballInfo.y, ballInfo.angle);
		ball.setVelocity(ballInfo.velocity);
		gameEngine.ballList.add(ball);
	}
	
	static void getDetails(GameEngine gameEngine, final int mode, final int level) {
		gameEngine.ballList.clear(); //Power-ups do not carry over(Multi-ball)
		gameEngine.brickList.clear();
		readyGameInfo(mode, level);
		
		Ball ball = new Ball(ballInfo.x, ballInfo.y, ballInfo.angle);
		ball.setVelocity(ballInfo.velocity);
		gameEngine.ballList.add(ball);
		
		gameEngine.paddle = new Paddle(paddleInfo.x, paddleInfo.y);
		gameEngine.paddle.setVelocity(paddleInfo.velocity);
				
		ListIterator<Brick> brickIter = gameEngine.brickList.listIterator();
		brickInfoIter = brickInfoList.listIterator();
		BrickInfo brickInfo;
		Brick brick;
		
		while(brickInfoIter.hasNext()) {
			brickInfo = brickInfoIter.next();
			brick = new Brick(brickInfo.x, brickInfo.y, brickInfo.angle, health);
			brickIter.add(brick);
		}
	}
}

class GameEngine {
	final static Color GAME_BG_COLOR = Color.LIGHTGREEN;
	final static int GAME_LENGTH = 1280;
	final static int GAME_HEIGHT = 720;
	final static int FRAME_RATE = 60;
	final static long NANO_FRAME_TIME = 1000000000/FRAME_RATE;
	
	final static int IN_GAME = 0;
	final static int WON = 1;
	final static int LOST = 2;
	final static int PAUSED = 3;
	final static int WAITING = 4;
	
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
	
	ArrayList<Ball> ballList;
	Paddle paddle;
	ArrayList<Brick> brickList;
	ListIterator<Ball> ballIter;
	ListIterator<Brick> brickIter;
	
	private int lives = 3;
	private int displayedLives = 3;
	
	GraphicsContext bgGC, ballGC, brickPaddleGC, UIGC;
	Canvas ballCanvas, brickPaddleCanvas;
	AnimationTimer gameTimer, waitTimer;
	
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
	
	GameEngine(GraphicsContext bgGC, Canvas ballCanvas, GraphicsContext ballGC, Canvas brickPaddleCanvas, GraphicsContext brickPaddleGC, GraphicsContext UIGC, AnimationTimer gameTimer) {
		this.bgGC = bgGC;
		this.ballCanvas = ballCanvas;
		this.ballGC = ballGC;
		this.brickPaddleCanvas = brickPaddleCanvas;
		this.brickPaddleGC = brickPaddleGC;
		this.UIGC = UIGC;
		this.gameTimer = gameTimer;
		
		this.waitTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				String displayStr = "START";
				if((currentNanoTime - waitTime)/1000000000 < 1)
					displayStr = "3";
				else if((currentNanoTime - waitTime)/1000000000 < 2)
					displayStr = "2";
				else if((currentNanoTime - waitTime)/1000000000 < 3)
					displayStr = "1";
				
				UIGC.clearRect(GAME_LENGTH/2 - displayStr.length()*0.5*COUNTDOWN_FONT_SIZE*0.5625, GAME_HEIGHT/2 - COUNTDOWN_FONT_SIZE*0.2, COUNTDOWN_FONT_SIZE*4.8, GAME_HEIGHT/2 + COUNTDOWN_FONT_SIZE*0.2);
				
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
				
				UIGC.fillText(displayStr, GAME_LENGTH/2 - displayStr.length()*0.5*COUNTDOWN_FONT_SIZE*0.5625, GAME_HEIGHT/2 - COUNTDOWN_FONT_SIZE*0.2);
			}
		};
		
		ballList = new ArrayList<Ball>();
		brickList = new ArrayList<Brick>();
	}
	
	Boolean isPaused() {
		if(status == PAUSED)
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
	
	int paddleBallCollide(Paddle paddle, Ball ball, int paddleDir) {
		double ballCProj, paddleAProj, paddleBProj, paddleCProj, paddleDProj;
		double ballMinProj, ballMaxProj, paddleMinProj, paddleMaxProj;
		double ballX, ballY;
		Vector2D axis1, axis2, axis3, mtv; // mtv -> Minimum Translation Vector
		double overlap, backtrack = 0; // overlap -> Magnitude of mtv
		boolean reflect = false;
		
		/*double paddleVel = 0;
		if(paddleDir == 1)
			paddleVel = paddle.getVelocity();
		else if(paddleDir == -1)
			paddleVel = -paddle.getVelocity();
		Vector2D relativeVelocity = new Vector2D(ball.getXVelocity() - paddleVel, ball.getYVelocity());
		Vector2D relativeDirection = relativeVelocity.getNormalized();*/
		
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
		
		GameInfo.getDetails(this, GameInfo.STANDARD_MODE, 1);
		
		ballIter = ballList.listIterator();
		brickIter = brickList.listIterator();
		
		paddle.drawPaddle(brickPaddleGC);
		while(ballIter.hasNext())
			ballIter.next().drawBall(ballGC);
		while(brickIter.hasNext())
			brickIter.next().drawBrick(brickPaddleGC);
		
		startTime = lastNanoTime = System.nanoTime();
		setCountdown();
		startCountdown();
	}
	
	void updateGame(long curNanoTime) {
		long loopTime = curNanoTime - lastNanoTime;
		double backtrack, newAngle;
		lastNanoTime = System.nanoTime();
		elapsedNanoTime += loopTime;
		
		int paddleDir = paddle.updateCoords(loopTime);
		
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
					brick.reduceHealth(1);                  //////////////////////////////// Set Damage
					//pause();
				}
			}
			
			//Paddle Collision test
			if((backtrack = paddleBallCollide(paddle, ball, paddleDir)) != 0)
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
			ballIter = ballList.listIterator();
			
			ballGC.clearRect(0, 0, GAME_LENGTH, GAME_HEIGHT);
			while(ballIter.hasNext()) {
				Ball ball = ballIter.next();
				ball.drawBall(ballGC);
				if(ball.isDestroyed()) {
					ballIter.remove();
					System.out.println("Deleted ball");
					
					//All balls fell off the screen
					if(ballList.isEmpty()) {
						lives--;
						if(lives > 0) {
							GameInfo.getBall(this);
							setCountdown();
						}
						break;
					}
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
};

public class Main extends Application {
	Canvas ballCanvas, brickPaddleCanvas, bgCanvas, UICanvas;
	GraphicsContext ballGC, brickPaddleGC, bgGC, UIGC;
	GameEngine gameEngine;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	AnimationTimer gameTimer;
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
		
		StackPane canvasPane = new StackPane(); /////////////Move starting here
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
		
		gameTimer = new AnimationTimer() {                               //////////////////// Move AnimationTimer to GameEngine
			public void handle(long currentNanoTime) {
				timeLabel.setText(Long.toString(gameEngine.getGameTime(currentNanoTime)/1000000000) + "s");
				gameEngine.updateGame(currentNanoTime);
				gameEngine.drawFrame(currentNanoTime);
				gameEngine.pauseGame();
				gameEngine.startCountdown();
			}
		};
		
		gameEngine = new GameEngine(bgGC, ballCanvas, ballGC, brickPaddleCanvas, brickPaddleGC, UIGC, gameTimer);
		
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