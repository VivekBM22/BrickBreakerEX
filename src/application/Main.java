package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

class Ball {	
	final static int BALL_SIZE = 27;
	final static int BALL_SIZE_2 = BALL_SIZE/2;
	final static Color ballColor = Color.DARKGOLDENROD;
	static Image ballImg;
	
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
	final static Color paddleColor = Color.DARKORANGE;
	static Image paddleImg;
	
	private int x;
	private int y;
	
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
	
	Paddle(int X, int Y) {
		x = X;
		y = Y;
	}
	
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

	
	void drawPaddle(GraphicsContext gc) {
		if(paddleImg != null) {
			;
		}
		else {
			gc.setFill(paddleColor);
			gc.fillRect(x - PADDLE_LENGTH_2, y - PADDLE_WIDTH_2, PADDLE_LENGTH, PADDLE_WIDTH);
		}
	}
	
	void erasePaddle(GraphicsContext gc) {
		gc.setFill(Color.TRANSPARENT);
		gc.fillRect(x - PADDLE_LENGTH_2, y - PADDLE_WIDTH_2, PADDLE_LENGTH, PADDLE_WIDTH);
	}
};

class Brick {
	final static int BRICK_LENGTH = 128;
	final static int BRICK_LENGTH_2 = BRICK_LENGTH/2;
	final static int BRICK_WIDTH = 27;
	final static int BRICK_WIDTH_2 = BRICK_WIDTH/2;
	final static Color brickColor = Color.DARKBLUE;
	static Image brickImg = new Image("Theme "+ 1 + "/Brick.png");
	
	//Upper-left corner
	private int x;
	private int y;
	
	double angle;
	
	double[] xCoords = new double[4];
	double[] yCoords = new double[4];
	
	static {
		try {
			brickImg = new Image("Theme "+ 1 + "/Brick.png");
		}
		catch(IllegalArgumentException npe) {
			brickImg = null;
		}
	}
	
	static void setTheme(int themeNo) {
		try {
			brickImg = new Image("Theme "+ themeNo + "/Brick.png");
		}
		catch(IllegalArgumentException npe) {
			brickImg = null;
		}
	}
	
	Brick(int x, int y, double angle) { //Modify for center as (x,y)
		this.x = x;
		this.y = y;
		this.angle = angle;
		
		xCoords[0] = 0;
		xCoords[1] = BRICK_LENGTH * Math.cos(this.angle);
		xCoords[3] = BRICK_WIDTH * Math.sin(this.angle);
		xCoords[2] = xCoords[1] + xCoords[3];
		
		yCoords[0] = 0;
		yCoords[1] = - BRICK_LENGTH *  Math.sin(this.angle);
		yCoords[3] = BRICK_WIDTH * Math.cos(this.angle);
		yCoords[2] = yCoords[1] + yCoords[3];
		
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

	private void rotateGC(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

	
	void drawBrick(GraphicsContext gc) {
		if(brickImg != null) {
			gc.save();
			rotateGC(gc, -angle*180/Math.PI, x/* + BRICK_LENGTH_2*/, y/* + BRICK_WIDTH_2*/); // Remove comments to rotate along center
			gc.drawImage(brickImg, x, y);
			gc.restore();
		}
		else {
			gc.setFill(brickColor);
			gc.fillPolygon(xCoords, yCoords, 4);
		}
	}
	
	void eraseBrick(GraphicsContext gc) {
		if(brickImg != null) {
			gc.save();
			rotateGC(gc, -angle*180/Math.PI, x/* + BRICK_LENGTH_2*/, y/* + BRICK_WIDTH_2*/); // Remove comments to rotate along center
			gc.clearRect(x, y, BRICK_LENGTH, BRICK_WIDTH);
			gc.restore();
		}
		else {
			gc.setFill(GameEngine.GAME_BG_COLOR);
			gc.fillPolygon(xCoords, yCoords, 4);
		}
	}
};

class GameEngine {
	final static Color GAME_BG_COLOR = Color.LIGHTGREEN;
	final static int GAME_LENGTH = 1280;
	final static int GAME_HEIGHT = 720;
	final static int FRAME_RATE = 60;
	final static long NANO_FRAME_TIME = 1000000000/FRAME_RATE;
	private long lastNanoTime;
	private long elapsedNanoTime = 0;
	private int frameCount = 0;
	
	Ball ball;
	Paddle paddle;
	Brick brick;
	
	GraphicsContext ballGC, brickPaddleGC;
	Canvas ballCanvas, brickPaddleCanvas;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	GameEngine(Canvas ballCanvas, GraphicsContext ballGC, Canvas brickPaddleCanvas, GraphicsContext brickPaddleGC) {
		this.ballCanvas = ballCanvas;
		this.ballGC = ballGC;
		this.brickPaddleCanvas = brickPaddleCanvas;
		this.brickPaddleGC = brickPaddleGC;
		ball = new Ball(640, 650, Math.PI/3);
		ball.setVelocity(0.5);
		paddle = new Paddle(ball.getX(), ball.getY() + Paddle.PADDLE_WIDTH_2 + Ball.BALL_SIZE_2 + 2);
		brick = new Brick(ball.getX() - Brick.BRICK_LENGTH_2, 360 - Brick.BRICK_WIDTH_2, Math.PI/6);
		
		paddle.drawPaddle(brickPaddleGC);
		ball.drawBall(ballGC);
		brick.drawBrick(brickPaddleGC);
		//brick.eraseBrick(brickPaddleGC);
	}
	
	long getLastNanoTime() {
		return lastNanoTime;
	}
	
	void setLastNanoTime(long lNanoTime) {
		lastNanoTime = lNanoTime;
	}
	
	public void drawFrame(long curNanoTime) {
		elapsedNanoTime += curNanoTime - lastNanoTime;
		
		ball.updateCoords(curNanoTime - lastNanoTime);
		
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
		
		if(elapsedNanoTime > NANO_FRAME_TIME) {
			ball.eraseBall(ballGC);
			ball.drawBall(ballGC);
			elapsedNanoTime -= NANO_FRAME_TIME;
			frameCount ++;
		}
		if(frameCount%200 == 0) {
			System.out.println("200 frames printed");
		}

		System.out.println(curNanoTime);
	}
	
};

public class Main extends Application {
	Canvas ballCanvas, brickPaddleCanvas, bgCanvas;
	GraphicsContext ballGC, brickPaddleGC, bgGC;
	GameEngine gameEngine;
	
	private long pauseTime;
	private long totalPausedTime = 0;
	private Boolean paused = false;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	
	AnimationTimer animTimer;
	@Override
	public void start(Stage primaryStage) {
		
		FlowPane root = new FlowPane();
		
		Button btn = new Button("Test Button");
		root.getChildren().add(btn);
		
		Scene scene  = new Scene(root, 1300, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test Application 1");
		primaryStage.show();
		
		StackPane canvasPane = new StackPane();
		root.getChildren().add(canvasPane);
		
		bgCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		bgGC = bgCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(bgCanvas);
		
		ballCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		ballGC = ballCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(ballCanvas);
		
		brickPaddleCanvas = new Canvas(GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		brickPaddleGC = brickPaddleCanvas.getGraphicsContext2D();
		canvasPane.getChildren().add(brickPaddleCanvas);
		
		bgGC.setFill(GameEngine.GAME_BG_COLOR);
		bgGC.fillRect(0, 0, GameEngine.GAME_LENGTH, GameEngine.GAME_HEIGHT);
		
		gameEngine = new GameEngine(ballCanvas, ballGC, brickPaddleCanvas, brickPaddleGC);
		
		animTimer = new AnimationTimer() {
			public void handle(long currentNanoTime) {
				gameEngine.drawFrame(currentNanoTime);
				gameEngine.setLastNanoTime(currentNanoTime);
			}
		};
		
		gameEngine.setLastNanoTime(System.nanoTime());
		animTimer.start();
		
		ballImg = gameEngine.ball.getBallImg(ballCanvas);
		ballImgReader = ballImg.getPixelReader();
		
		/*gc.drawImage(ballImg, 0, 0);*/
		
		Label txt = new Label();
		root.getChildren().add(txt);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if(!paused) {
					animTimer.stop();
					pauseTime = System.nanoTime();
					paused = true;
;					txt.setText("Animation stopped");
				}
				else {
					pauseTime = System.nanoTime() - pauseTime;
					gameEngine.setLastNanoTime(gameEngine.getLastNanoTime() + pauseTime);
					totalPausedTime += pauseTime;
					animTimer.start();
					paused = false;
					txt.setText("Animation continued");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}