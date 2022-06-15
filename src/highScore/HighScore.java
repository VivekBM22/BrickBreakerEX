package highScore;

import game.LevelInfo;
import game.GameInfo;

public class HighScore {
	static final int HARD_BONUS = 1000;
	static final int SCORE_CONSTANT = 10000;
	
	public static int calcScore(LevelInfo[] levelInfo, int mode) {
		int score = 0;
		int baseLevelScore[] = new int[GameInfo.LEVEL_COUNT];
		baseLevelScore[0] = 5000;
		baseLevelScore[1] = 6000;
		baseLevelScore[2] = 7000;
		baseLevelScore[3] = 8000;
		baseLevelScore[4] = 9000;
		for(int i = 0; i < GameInfo.LEVEL_COUNT; i++) {
			if(mode == GameInfo.TOURNAMENT_HARD_MODE)
				baseLevelScore[i] += HARD_BONUS;
			score += baseLevelScore[i] + (SCORE_CONSTANT - (levelInfo[i].livesLost*100)  - (levelInfo[i].milliTime/100));
		}
		
		return score;
	}
}
