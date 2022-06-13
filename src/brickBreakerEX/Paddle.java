package brickBreakerEX;

import Vector2D.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
}
