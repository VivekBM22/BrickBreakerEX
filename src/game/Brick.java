package game;

import Vector2D.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

class Brick {
	final static int BRICK_LENGTH = 128;
	final static double BRICK_LENGTH_2 = BRICK_LENGTH/2.0;
	final static int BRICK_HEIGHT = 27;
	final static double BRICK_HEIGHT_2 = BRICK_HEIGHT/2.0;
	static final int MAX_HEALTH = 5;
	
	private final static int DESTROYED = 0;
	private final static int DAMAGE_4 = 1;
	private final static int DAMAGE_3 = 2;
	private final static int DAMAGE_2 = 3;
	private final static int DAMAGE_1 = 4;
	private final static int DAMAGE_0 = 5;
	
	private static Color dmg4Color;
	private static Color dmg3Color;
	private static Color dmg2Color;
	private static Color dmg1Color;
	private static Color dmg0Color;
	
	private static Image dmg4Img;
	private static Image dmg3Img;
	private static Image dmg2Img;
	private static Image dmg1Img;
	private static Image dmg0Img;
	
	private static Image brickImg;
	static Color brickColor;
	
	//Upper-left corner
	private int x;
	private int y;
	private double angle;
	
	double[] xCoords = new double[4];
	double[] yCoords = new double[4];
	
	private int health;
	private int status;
	
	private boolean reDraw;
	
	private Vector2D n1, n2;
	private double aOnN1, dOnN1;
	private double aOnN2, bOnN2; 
	
	static {
		setTheme(1);
	}
	
	static void setTheme(int themeNo) {
		dmg0Color = Color.BLUE;
		dmg1Color = Color.MAGENTA;
		dmg2Color = Color.PURPLE;
		dmg3Color = Color.ORANGE;
		dmg4Color = Color.RED;
		
		try {
			dmg0Img = new Image("Theme " + themeNo + "/Brick.png");
		}
		catch(IllegalArgumentException npe) {
			dmg0Img = null;
			if(themeNo == 0)
				dmg0Color = Color.BLUE;
			else if(themeNo == 1)
				dmg0Color = Color.BLUE;
			else if(themeNo == 2)
				dmg0Color = Color.BLUE;
			else if(themeNo == 3)
				dmg0Color = Color.BLUE;
			else if(themeNo == 4)
				dmg0Color = Color.BLUE;
		}
		
		try {
			dmg1Img = new Image("Theme " + themeNo + "/Brick1Dmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmg1Img = null;
			if(themeNo == 0)
				dmg1Color = Color.MAGENTA;
			else if(themeNo == 1)
				dmg1Color = Color.MAGENTA;
			else if(themeNo == 2)
				dmg1Color = Color.MAGENTA;
			else if(themeNo == 3)
				dmg1Color = Color.MAGENTA;
			else if(themeNo == 4)
				dmg1Color = Color.MAGENTA;
		}
		
		try {
			dmg2Img = new Image("Theme " + themeNo + "/Brick2Dmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmg2Img = null;
			if(themeNo == 0)
				dmg2Color = Color.PURPLE;
			else if(themeNo == 1)
				dmg2Color = Color.PURPLE;
			else if(themeNo == 2)
				dmg2Color = Color.PURPLE;
			else if(themeNo == 3)
				dmg2Color = Color.PURPLE;
			else if(themeNo == 4)
				dmg2Color = Color.PURPLE;
		}
		
		try {
			dmg3Img = new Image("Theme " + themeNo + "/Brick3Dmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmg3Img = null;
			if(themeNo == 0)
				dmg3Color = Color.ORANGE;
			else if(themeNo == 1)
				dmg3Color = Color.ORANGE;
			else if(themeNo == 2)
				dmg3Color = Color.ORANGE;
			else if(themeNo == 3)
				dmg3Color = Color.ORANGE;
			else if(themeNo == 4)
				dmg3Color = Color.ORANGE;
		}
		
		try {
			dmg4Img = new Image("Theme " + themeNo + "/Brick4Dmg.png");
		}
		catch(IllegalArgumentException npe) {
			dmg4Img = null;
			if(themeNo == 0)
				dmg4Color = Color.RED;
			else if(themeNo == 1)
				dmg4Color = Color.RED;
			else if(themeNo == 2)
				dmg4Color = Color.RED;
			else if(themeNo == 3)
				dmg4Color = Color.RED;
			else if(themeNo == 4)
				dmg4Color = Color.RED;
		}
	}
	
	Brick(int x, int y, double angle, int health) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.health = health;
		reduceHealth(0);
		
		double cos = Math.cos(this.angle);
		double sin = Math.sin(this.angle);
		
		xCoords[0] = -BRICK_LENGTH_2 * cos - BRICK_HEIGHT_2 * sin;
		xCoords[1] = BRICK_LENGTH_2 * cos - BRICK_HEIGHT_2 * sin;
		xCoords[3] = -BRICK_LENGTH_2 * cos + BRICK_HEIGHT_2 * sin;
		xCoords[2] = BRICK_LENGTH_2 * cos + BRICK_HEIGHT_2 * sin;
		
		yCoords[0] = BRICK_LENGTH_2 * sin - BRICK_HEIGHT_2 * cos;
		yCoords[1] = -BRICK_LENGTH_2 * sin - BRICK_HEIGHT_2 * cos;
		yCoords[3] = BRICK_LENGTH_2 * sin + BRICK_HEIGHT_2 * cos;
		yCoords[2] = -BRICK_LENGTH_2 * sin + BRICK_HEIGHT_2 * cos;
		
		for(int i = 0; i < 4; i++) {
			xCoords[i] += this.x;
			yCoords[i] += this.y;
		}
		
		n1 = new Vector2D(-sin, -cos);
		n2 = new Vector2D(cos, -sin);
		
		aOnN1 = n1.dot(xCoords[0], yCoords[0]);
		dOnN1 = n1.dot(xCoords[3], yCoords[3]);
		aOnN2 = n2.dot(xCoords[0], yCoords[0]);
		bOnN2 = n2.dot(xCoords[1], yCoords[1]);
	};
	
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
		if(this.status == DAMAGE_0) {
			brickColor = dmg0Color;
			brickImg = dmg0Img;
		}
		else if(this.status == DAMAGE_1) {
			brickColor = dmg1Color;
			brickImg = dmg1Img;
		}
		else if(this.status == DAMAGE_2) {
			brickColor = dmg2Color;
			brickImg = dmg2Img;
		}
		else if(this.status == DAMAGE_3) {
			brickColor = dmg3Color;
			brickImg = dmg3Img;
		}
		else if(this.status == DAMAGE_4) {
			brickColor = dmg4Color;
			brickImg = dmg4Img;
		}
		this.reDraw = true;
	}
	
	void reduceHealth(int damage) {
		health -= damage;
		if(health < 0)
			health = 0;
		else if(health > MAX_HEALTH)
			health = MAX_HEALTH;

		dmg0Color = Color.BLUE;
		dmg1Color = Color.MAGENTA;
		dmg2Color = Color.PURPLE;
		dmg3Color = Color.ORANGE;
		dmg4Color = Color.RED;
		if(health == 5)
			setStatus(DAMAGE_0);
		else if(health == 4)
			setStatus(DAMAGE_1);
		else if(health == 3)
			setStatus(DAMAGE_2);
		else if(health == 2)
			setStatus(DAMAGE_3);
		else if(health == 1)
			setStatus(DAMAGE_4);
		else
			setStatus(DESTROYED);
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
				gc.drawImage(brickImg, x - BRICK_LENGTH_2, y - BRICK_HEIGHT_2);
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
			gc.clearRect(x - BRICK_LENGTH_2, y - BRICK_HEIGHT_2, BRICK_LENGTH, BRICK_HEIGHT);
			gc.restore();
		}
	}
}