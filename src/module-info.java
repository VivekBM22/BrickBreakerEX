module Test {
	requires javafx.controls;
	requires transitive javafx.graphics;
	requires transitive javafx.base;
	requires javafx.fxml;
	requires javafx.web;
	
	opens brickBreakerEX to javafx.graphics, javafx.fxml, javafx.web;
	opens game to javafx.graphics, javafx.fxml, javafx.web;
	
	exports brickBreakerEX;
}
