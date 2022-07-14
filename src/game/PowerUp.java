package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class PowerUp {
	static final int POWERUP_LENGTH = 35;
	static final int POWERUP_HEIGHT = 35;
	
	private static Image[] POWERUP_IMG = new Image[4];
	private static Color[] POWERUP_COLOR = new Color[4];
	
	private static final int LIFE_UP = 0;
	private static final int PADDLE_SPEED_UP = 1;
	private static final int EXTRA_BALL = 2;
	private static final int DAMAGE_UP = 3;
	
	private static final int SPAWNED = 0;
	private static final int ACTIVE = 1;
	private static final int DESTROYED = 2;

	private static final int LU_DURATION = 5000;
	private static final int PSU_DURATION = 5000;
	private static final int EB_DURATION = 5000;
	private static final int DU_DURATION = 5000;
	private static final int BOOSTED_DAMAGE = 2;
	private static final double PADDLE_SPEED_INC = 0.2;
	private static final double VELOCITY = 0.25;
	
	private Image powerUpImg;
	private Color powerUpColor;
	
	private GameEngine gameEngine;
	private int ID;
	private int status;
	private double yCoord;
	private int x;
	private int y, oldY;
	private long duration; //In milliseconds
	private boolean erase; 
	
	static {
		try {
			POWERUP_IMG[0] = new Image("LifeUp.png");
		}
		catch(IllegalArgumentException npe) {
			POWERUP_IMG[0] = null;
			POWERUP_COLOR[0] = Color.RED;
		}
		
		try {
			POWERUP_IMG[1] = new Image("PaddleSpeedUp.png");
		}
		catch(IllegalArgumentException npe) {
			POWERUP_IMG[1] = null;
			POWERUP_COLOR[1] = Color.BLUEVIOLET;
		}
		
		try {
			POWERUP_IMG[2] = new Image("ExtraBall.png");
		}
		catch(IllegalArgumentException npe) {
			POWERUP_IMG[2] = null;
			POWERUP_COLOR[2] = Color.GOLDENROD;
		}
		
		try {
			POWERUP_IMG[3] = new Image("DamageUp.png");
		}
		catch(IllegalArgumentException npe) {
			POWERUP_IMG[3] = null;
			POWERUP_COLOR[3] = Color.BLACK;
		}
	}
	
	PowerUp(int ID, GameEngine gameEngine) {
		this.ID = ID;
		status = SPAWNED;
		erase = true;
		yCoord = y = oldY = 0;
		x = (int)(Math.random()*(GameEngine.GAME_LENGTH - POWERUP_LENGTH));
		this.gameEngine = gameEngine;
		
		powerUpImg = POWERUP_IMG[ID];
		if(powerUpImg == null)
			powerUpColor = POWERUP_COLOR[ID];
		
		if(ID == LIFE_UP)
			duration = LU_DURATION;
		else if(ID == PADDLE_SPEED_UP)
			duration = PSU_DURATION;
		else if(ID == EXTRA_BALL)
			duration = EB_DURATION;
		else if(ID == DAMAGE_UP)
			duration = DU_DURATION;
		
		System.out.println("PowerUp Spawned with ID: " + ID);
		System.out.println(powerUpColor);
		System.out.println("X: " + x + ", Y: " + y);
	}
	
	boolean isSpawned() {
		if(status == SPAWNED)
			return true;
		return false;
	}
	
	boolean isDestroyed() {
		if(status == DESTROYED)
			return true;
		return false;
	}
	
	double getX() {
		return x;
	}
	
	double getY() {
		return yCoord;
	}
	
	void activatePowerUp() {
		status = ACTIVE;
		
		if(ID == LIFE_UP)
			gameEngine.incrementLives();
		else if(ID == PADDLE_SPEED_UP)
			gameEngine.getPaddle().setVelocity(gameEngine.getPaddle().getVelocity() + PADDLE_SPEED_INC);
		else if(ID == EXTRA_BALL)
			gameEngine.addBall();
		else if(ID == DAMAGE_UP)
			gameEngine.setDamage(BOOSTED_DAMAGE);
	}
	
	void deactivatePowerUp() {
		if(ID == PADDLE_SPEED_UP)
			gameEngine.getPaddle().setVelocity(gameEngine.getPaddle().getVelocity() - PADDLE_SPEED_INC);
		else if(ID == DAMAGE_UP)
			gameEngine.setDamage(1);
	}	
	
	void updatePowerUp(long nanoTime) {
		long time = nanoTime/1000000;
		if(status == SPAWNED) {
			yCoord += VELOCITY*time;
			y = (int)(yCoord);
			
			if(yCoord > GameEngine.BOTTOM_LIMIT - POWERUP_HEIGHT/2)
				status = DESTROYED;
		}
		else if(status == ACTIVE) {
			duration -= time;
			if(duration <= 0) {
				deactivatePowerUp();
				status = DESTROYED;
			}
		}
	}
	
	void drawPowerUp(GraphicsContext gc) {
		if(powerUpImg != null) {
			gc.drawImage(powerUpImg, x, y);
		}
		else {
			gc.setFill(powerUpColor);
			gc.fillRect(x, y, POWERUP_LENGTH, POWERUP_HEIGHT);
		}
		
		oldY = y;
	}
	
	void erasePowerUp(GraphicsContext gc) {
		if(!erase)
			return;
		
		gc.clearRect(x, oldY, POWERUP_LENGTH, POWERUP_HEIGHT);
		if(status != SPAWNED)
			erase = false;
	}	
}