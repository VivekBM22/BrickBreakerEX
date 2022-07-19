package game;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameInfo {
	public static final int LEVEL_COUNT = 5;
	public static final int THEME_COUNT = 5;
	
	public static final int TOURNAMENT_MODE = 0;
	public static final int TOURNAMENT_HARD_MODE = 1;
	public static final int LEVEL_SELECT_MODE = 2;
	public static final int LEVEL_SELECT_HARD_MODE = 3;
	public static final int TIME_TRIAL_MODE = 4;
	
	private static ArrayList<BrickInfo> brickInfoList;
	private static ListIterator<BrickInfo> brickInfoIter;
	private static BallInfo ballInfo;
	private static PaddleInfo paddleInfo;
	
	private static int ballDamage;
	private static long powerUpSpawnTime;
	private static long levelTime; // In seconds
	
	static {
		readyGameInfo(TOURNAMENT_MODE, 1);
	}
	
	private static void readyGameInfo(final int mode, final int level) {
		BrickInfo bi;
		ballInfo = new BallInfo();
		paddleInfo = new PaddleInfo();
		brickInfoList = new ArrayList<BrickInfo>();
		
		ballDamage = 1;
		levelTime = -1;
		
		paddleInfo.x = GameEngine.GAME_LENGTH/2;
		paddleInfo.y =  GameEngine.GAME_HEIGHT - 100;
		paddleInfo.velocity = 0.6;
		
		ballInfo.x = paddleInfo.x + 100;
		ballInfo.y = GameEngine.GAME_HEIGHT - 200;
		ballInfo.angle = -Math.PI*0.75;
		
		if(mode == TOURNAMENT_MODE || mode == LEVEL_SELECT_MODE || mode == TIME_TRIAL_MODE) {
			powerUpSpawnTime = 15;
			ballInfo.velocity = 0.35;
		}
		else {
			powerUpSpawnTime = 25;
			ballInfo.velocity = 0.45;
		}
		
		if(mode == TIME_TRIAL_MODE) {
			if(level == 1) {
				levelTime = 20;
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250, 0, 3);
				brickInfoList.add(bi);
			} else if(level == 2) {
				levelTime = 20;
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250, 0, 3);
				brickInfoList.add(bi);
			} else if(level == 3) {
				levelTime = 20;
//				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250, 0, 3);
//				brickInfoList.add(bi);
			} else if(level == 4) {
				levelTime = 20;
//				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250, 0, 3);
//				brickInfoList.add(bi);
			} else if(level == 5) {
				levelTime = 20;
//				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250, 0, 3);
//				brickInfoList.add(bi);
			}
			
		}
		else {
			if(level == 1) {
				
				//Row 5
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);

				//Row 4
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5)- 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);

				//Row 3
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);

				//Row 2
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 6*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);

				//Row 3
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 8*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
			}
			else if(level == 2) {
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, 0, 3);
				brickInfoList.add(bi);
				
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2 - 100, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 325,  Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2 + 100, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 2*(Brick.BRICK_HEIGHT + 5) - 325,  -Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2 - 100, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 375,  -Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2 + 100, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 4*(Brick.BRICK_HEIGHT + 5) - 375,  Math.PI/4, 3);
				brickInfoList.add(bi);
				
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 2, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 3, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 4, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 5, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 6, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 8, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 9*(Brick.BRICK_HEIGHT + 5) - 300, 0, 3);
				brickInfoList.add(bi);
			}
		}
	}
	
	static void getBall(GameEngine gameEngine) {
		Ball ball = new Ball(ballInfo.x - paddleInfo.x + gameEngine.getPaddle().getX(), ballInfo.y, ballInfo.angle);
		ball.setVelocity(ballInfo.velocity);
		gameEngine.getBallList().add(ball);
	}
	
	static void resetPaddle(GameEngine gameEngine) {
		gameEngine.getPaddle().resetX(paddleInfo.x);
	}
	
	static void getDetails(GameEngine gameEngine, final int mode, final int level) {
		gameEngine.getBallList().clear(); //Power-Ups do not carry over(Multi-balls)
		gameEngine.getBrickList().clear();
		readyGameInfo(mode, level);
		
		gameEngine.setDamage(ballDamage);
		gameEngine.setLevelTime(levelTime);
		gameEngine.setPUSpawnTime(powerUpSpawnTime);
		
		Ball ball = new Ball(ballInfo.x, ballInfo.y, ballInfo.angle);
		ball.setVelocity(ballInfo.velocity);
		gameEngine.getBallList().add(ball);
		
		gameEngine.setPaddle(new Paddle(paddleInfo.x, paddleInfo.y));
		gameEngine.getPaddle().setVelocity(paddleInfo.velocity);
				
		ListIterator<Brick> brickIter = gameEngine.getBrickList().listIterator();
		brickInfoIter = brickInfoList.listIterator();
		BrickInfo brickInfo;
		Brick brick;
		
		while(brickInfoIter.hasNext()) {
			brickInfo = brickInfoIter.next();
			brick = new Brick(brickInfo.x, brickInfo.y, brickInfo.angle, brickInfo.health);
			brickIter.add(brick);
		}
	}
}