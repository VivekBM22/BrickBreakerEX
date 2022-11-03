module Test {
	requires transitive javafx.base;
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.fxml;
	requires javafx.web;
	
	opens brickBreakerEX to javafx.graphics, javafx.fxml;
	opens game to javafx.graphics, javafx.fxml;
	
	exports brickBreakerEX;
}