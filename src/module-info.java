module Test {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens brickBreakerEX to javafx.graphics, javafx.fxml;
}
