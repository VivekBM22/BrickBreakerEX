package brickBreakerEX;

import Vector2D.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

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
}
