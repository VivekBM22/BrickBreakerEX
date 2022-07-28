package brickBreakerEX;

import javafx.beans.property.SimpleStringProperty;

public class scoreBoardDetails {
	private int position, score;
	private SimpleStringProperty name;
	
	public scoreBoardDetails(int p, String n, int s){
		this.position = p;
		this.name = new SimpleStringProperty(n);
		this.score = s;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
