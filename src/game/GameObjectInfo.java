package game;

class BrickInfo {
	int x;
	int y;
	int health;
	double angle;
	BrickInfo(int x, int y, double angle, int health) {
		this.x = x;
		this.y = y;
		this.health = health;
		this.angle = angle;
	}
}

class BallInfo {
	int x;
	int y;
	double angle;
	double velocity;
}

class PaddleInfo {
	int x;
	int y;
	double velocity;
}