package game;

import java.io.IOException;

import brickBreakerEX.GameOverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneController2 {
		private Stage stage;
		private Parent root; 
		private Scene scene;
		
	public void switchToGameOver(StackPane canvasPane, int status, Integer gameScore) throws IOException {		
		stage = (Stage)canvasPane.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/brickBreakerEX/GameOver.fxml")); 
		root = loader.load();
		
		GameOverController gsc = loader.getController();
		
		if(status == GameEngine.LOST) {
			gsc.lostAll();
		}
		else if(status == GameEngine.WON && gameScore == -1) {
			gsc.wonLevelSelect();
		}
		else {
			gsc.wonOtherModes(gameScore);
		}
		
		scene = new Scene(root, 1300, 800);
		//String css = this.getClass().getResource().toExternalForm();
		//scene.getStylesheets().add("/application.css");
		stage.setScene(scene);
		stage.show();
	}
	
}
