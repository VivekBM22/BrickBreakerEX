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
				levelTime = 600; //10 mins
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
			} else if(level == 2) {
				levelTime = 600; //10 mins
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + 10, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH + 20, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*3 - 12 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*2 + 30, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*5 - 12*2 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*3 + 40, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*7 - 12*3 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*4 + 50, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*9 - 12*4 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*5 + 60, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*11 - 12*5 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*6 + 70, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*13 - 12*6 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*7 + 80, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*15 - 12*7 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*8 + 90, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*17 - 12*8 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + 10, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*17 - 12*8 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH +20, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*15 - 12*7 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*2 + 30, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*13 - 12*6 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*3 + 40, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*11 - 12*5 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*5 + 60, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*7 - 12*3 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*6 + 70, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*5 - 12*2 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*7 + 80, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*3 - 12 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*8 + 90, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
			} else if(level == 3) {
				levelTime = 600; //10 mins
				//Column 1
				bi = new BrickInfo( 120, 60, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120 + Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				
				//mid1
				bi = new BrickInfo( 246, 20, 0, 3);
				brickInfoList.add(bi);				
				
				
				//Column 2
				bi = new BrickInfo( 370, 60, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370 - Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				
				//Middle
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7/2 - 27, (int)(Brick.BRICK_LENGTH_2 - Brick.BRICK_HEIGHT_2 ) + 400, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + 380 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*4 - 250, Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2*3 + 350 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*6 - 220, Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2*5 + 350 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*6 - 220, -Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2*7 + 320 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*4 - 250, -Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9/2 - 27, (int)(Brick.BRICK_LENGTH_2 - Brick.BRICK_HEIGHT_2 ) + 400, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 11/2 - 27, (int)(Brick.BRICK_LENGTH_2 - Brick.BRICK_HEIGHT_2 ) + 400, 0, 3);
				brickInfoList.add(bi);
				
				//Column 1
				bi = new BrickInfo( 850, 60, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850 + Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				
				//mid1
				bi = new BrickInfo( 976, 20, 0, 3);
				brickInfoList.add(bi);				
				
				
				//Column 2
				bi = new BrickInfo( 1100, 60, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100 - Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
			} else if(level == 4) {
				levelTime = 20;
				bi = new BrickInfo( 860, 460, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 315, 210, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1000, 210, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 460, 460, Math.PI/6, 3);
				brickInfoList.add(bi);				
				bi = new BrickInfo( 775, 360, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 775, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 745, 360, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 745, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 565, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 535, 95, 10*Math.PI/18, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 785, 95, 8*Math.PI/18, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 536, 365, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 536, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 278, 96, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 445, 235, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 565, 365, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 330, 490, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1023, 96, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 990, 490, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 865, 235, 0, 3);
				brickInfoList.add(bi);
				
			} else if(level == 5) {
				levelTime = 600; //10 mins
				bi = new BrickInfo( 238, 97, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 760, 97, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 495, 157, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1020, 157, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 109, 127, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 623, 127, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1150, 127, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 127, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 890, 127, -Math.PI/6, 3);
				brickInfoList.add(bi);
				
				bi = new BrickInfo( 238 , 97 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 760 , 97 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 495 , 157 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1020, 157 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 109, 127 + 137, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 623, 127 + 137, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1150, 127 + 137, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 127 + 137, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 890, 127 + 137, -Math.PI/6, 3);
				brickInfoList.add(bi);

				
				bi = new BrickInfo( 238 , 97 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 760 , 97 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 495 , 157 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1020, 157 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 109, 127 + 137*2, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 623, 127 + 137*2, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1150, 127 + 137*2, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 127 + 137*2, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 890, 127 + 137*2, -Math.PI/6, 5);
				brickInfoList.add(bi);				
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
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + 10, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH + 20, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*3 - 12 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*2 + 30, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*5 - 12*2 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*3 + 40, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*7 - 12*3 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*4 + 50, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*9 - 12*4 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*5 + 60, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*11 - 12*5 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*6 + 70, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*13 - 12*6 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*7 + 80, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*15 - 12*7 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*8 + 90, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*17 - 12*8 - 300, Math.PI/12, 3);
				brickInfoList.add(bi);
				
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + 10, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*17 - 12*8 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH +20, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*15 - 12*7 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*2 + 30, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*13 - 12*6 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*3 + 40, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*11 - 12*5 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*5 + 60, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*7 - 12*3 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*6 + 70, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*5 - 12*2 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*7 + 80, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2*3 - 12 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH*8 + 90, GameEngine.GAME_HEIGHT - (int)Brick.BRICK_HEIGHT_2 - 300, -Math.PI/12, 3);
				brickInfoList.add(bi);
			}
			else if( level == 3) {
				//Column 1
				bi = new BrickInfo( 120, 60, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120 + Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 120 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				
				//mid1
				bi = new BrickInfo( 246, 20, 0, 3);
				brickInfoList.add(bi);				
				
				
				//Column 2
				bi = new BrickInfo( 370, 60, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370 - Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				
				//Middle
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 7/2 - 27, (int)(Brick.BRICK_LENGTH_2 - Brick.BRICK_HEIGHT_2 ) + 400, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + 380 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*4 - 250, Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2*3 + 350 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*6 - 220, Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2*5 + 350 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*6 - 220, -Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2*7 + 320 , GameEngine.GAME_HEIGHT  - (int)Brick.BRICK_LENGTH_2*4 - 250, -Math.PI/4, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 9/2 - 27, (int)(Brick.BRICK_LENGTH_2 - Brick.BRICK_HEIGHT_2 ) + 400, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo((int)Brick.BRICK_LENGTH_2 + Brick.BRICK_LENGTH * 11/2 - 27, (int)(Brick.BRICK_LENGTH_2 - Brick.BRICK_HEIGHT_2 ) + 400, 0, 3);
				brickInfoList.add(bi);
				
				//Column 1
				bi = new BrickInfo( 850, 60, Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850 + Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 850 + Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				
				//mid1
				bi = new BrickInfo( 976, 20, 0, 3);
				brickInfoList.add(bi);				
				
				
				//Column 2
				bi = new BrickInfo( 1100, 60, -Math.PI/12, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100 - Brick.BRICK_HEIGHT, 400, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1100 - Brick.BRICK_HEIGHT, 400 - Brick.BRICK_LENGTH*2, Math.PI/2, 3);
				brickInfoList.add(bi);
			} 
			else if( level == 4 ) {
				bi = new BrickInfo( 860, 460, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 315, 210, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1000, 210, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 460, 460, Math.PI/6, 3);
				brickInfoList.add(bi);				
				bi = new BrickInfo( 775, 360, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 775, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 745, 360, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 745, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 565, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 535, 95, 10*Math.PI/18, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 785, 95, 8*Math.PI/18, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 536, 365, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 536, 235, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 278, 96, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 445, 235, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 565, 365, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 330, 490, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1023, 96, Math.PI/2, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 990, 490, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 865, 235, 0, 3);
				brickInfoList.add(bi);
			} else if( level == 5 ) {
				bi = new BrickInfo( 238, 97, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 760, 97, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 495, 157, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1020, 157, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 109, 127, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 623, 127, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1150, 127, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 127, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 890, 127, -Math.PI/6, 3);
				brickInfoList.add(bi);
				
				bi = new BrickInfo( 238 , 97 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 760 , 97 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 495 , 157 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1020, 157 + 137, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 109, 127 + 137, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 623, 127 + 137, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1150, 127 + 137, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 127 + 137, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 890, 127 + 137, -Math.PI/6, 3);
				brickInfoList.add(bi);

				
				bi = new BrickInfo( 238 , 97 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 760 , 97 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 495 , 157 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1020, 157 + 137*2, 0, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 109, 127 + 137*2, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 623, 127 + 137*2, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 1150, 127 + 137*2, Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 370, 127 + 137*2, -Math.PI/6, 3);
				brickInfoList.add(bi);
				bi = new BrickInfo( 890, 127 + 137*2, -Math.PI/6, 5);
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