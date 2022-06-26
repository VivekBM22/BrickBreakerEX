package game;

import Vector2D.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class Ball {
	final static int BALL_SIZE = 27;
	final static double BALL_SIZE_2 = BALL_SIZE/2.0;
	
	final static int NORMAL = 1;
	final static int DESTROYED = 0;
	
	private static Image ballImg;
	private static Color ballColor;
	
	private int x;
	private int y;
	private double xCoord;
	private double yCoord;
	private double angle;
	
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
			if(themeNo == 0)
				ballColor = Color.DARKGOLDENROD;
			else if(themeNo == 1)
				ballColor = Color.DARKGOLDENROD;
			else if(themeNo == 2)
				ballColor = Color.DARKGOLDENROD;
			else if(themeNo == 3)
				ballColor = Color.DARKGOLDENROD;
			else if(themeNo == 4)
				ballColor = Color.DARKGOLDENROD;
			else
				ballColor = Color.DARKGOLDENROD;
		}
	}
	
	Ball(int x, int y, double angle) {
		this.x = x;
		this.y = y;
		xCoord = x;
		yCoord = y;
		this.angle = angle;
		xVelocity = 0;
		yVelocity = 0;
		status = NORMAL;
	}
	
	void setVelocity(double velocity) {
		this.velocity = velocity;
		xVelocity = velocity * Math.cos(angle);
		yVelocity = velocity * Math.sin(angle);
	}
	
	void setX(int x) {
		this.x = x;
		xCoord = x;
	}
	
	void setY(int y) {
		this.y = y;
		yCoord = y;
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
	
	void move(double distance) {
		xCoord += distance * Math.cos(angle);
		yCoord -= distance * Math.sin(angle);
		x = (int) Math.round(xCoord);
		y = (int) Math.round(yCoord);
	}
	
	void move(double distance, Vector2D direction) {
		xCoord += distance * direction.x;
		yCoord -= distance * direction.y;
		x = (int) Math.round(xCoord);
		y = (int) Math.round(yCoord);
	}
	
	void drawBall(GraphicsContext gc) {
		if(status == DESTROYED)
			return;
		
		if(ballImg != null) {
			gc.drawImage(ballImg, x - BALL_SIZE_2, y - BALL_SIZE_2, BALL_SIZE, BALL_SIZE);
		}
		else {
			gc.setFill(ballColor);
			gc.fillOval(x - BALL_SIZE_2, y - BALL_SIZE_2, BALL_SIZE, BALL_SIZE);
		}
	}
	
	void updateCoords(long nanoTime) {
		long time = nanoTime / 1000000;
		xCoord += xVelocity * time;
		yCoord -= yVelocity * time;
		x = (int) Math.round(xCoord);
		y = (int) Math.round(yCoord);
	}
}