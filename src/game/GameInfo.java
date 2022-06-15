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
		
		ballInfo.x = GameEngine.GAME_LENGTH/2 + 400;
		ballInfo.y = GameEngine.GAME_HEIGHT - 500;
		ballInfo.angle = -Math.PI/1.1;
		
		ballDamage = 1;
		levelTime = -1;
		
		paddleInfo.x = GameEngine.GAME_LENGTH/2;
		paddleInfo.y =  GameEngine.GAME_HEIGHT - 100 + (int)(Paddle.PADDLE_HEIGHT_2) + (int)(Ball.BALL_SIZE_2) + 2;
		paddleInfo.velocity = 0.8;
		
		if(mode == TOURNAMENT_MODE || mode == LEVEL_SELECT_MODE || mode == TIME_TRIAL_MODE) {
			powerUpSpawnTime = 15;
			ballInfo.velocity = 0.5;
		}
		else {
			powerUpSpawnTime = 25;
			ballInfo.velocity = 0.5;
		}
		
		if(mode == TIME_TRIAL_MODE) {
			if(level == 1) {
				levelTime = 15;
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250,  Math.PI/6, 3);
				brickInfoList.add(bi);
			}
			
		}
		else {
			if(level == 1) {
				bi = new BrickInfo(GameEngine.GAME_LENGTH/2, GameEngine.GAME_HEIGHT - 100 - 250,  Math.PI/6, 3);
				brickInfoList.add(bi);
			}
		}
	}
	
	static void getBall(GameEngine gameEngine) {
		Ball ball = new Ball(ballInfo.x, ballInfo.y, ballInfo.angle);
		ball.setVelocity(ballInfo.velocity);
		gameEngine.ballList.add(ball);
	}
	
	static void getDetails(GameEngine gameEngine, final int mode, final int level) {
		gameEngine.ballList.clear(); //Power-Ups do not carry over(Multi-balls)
		gameEngine.brickList.clear();
		readyGameInfo(mode, level);
		
		gameEngine.setDamage(ballDamage);
		gameEngine.setLevelTime(levelTime);
		gameEngine.setPUSpawnTime(powerUpSpawnTime);
		
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
			brick = new Brick(brickInfo.x, brickInfo.y, brickInfo.angle, brickInfo.health);
			brickIter.add(brick);
		}
	}
}