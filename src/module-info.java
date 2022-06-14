module Test {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	
	opens brickBreakerEX to javafx.graphics, javafx.fxml;
}
