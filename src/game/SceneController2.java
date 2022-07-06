package game;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneController2 {

	
	public void switchToGameOver(StackPane canvasPane) throws IOException {

			Stage stage = (Stage)canvasPane.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/brickBreakerEX/GameOver.fxml"));
			Scene scene = new Scene(root, 1300, 800);
			//String css = this.getClass().getResource().toExternalForm();
			//scene.getStylesheets().add("/application.css");
			stage.setScene(scene);
			stage.show();
		
	}
	
}
