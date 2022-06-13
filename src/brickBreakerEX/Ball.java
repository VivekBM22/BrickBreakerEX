package brickBreakerEX;

import Vector2D.Vector2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

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
}
