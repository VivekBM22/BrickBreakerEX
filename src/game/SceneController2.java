package game;

import java.io.IOException;

import brickBreakerEX.GameOverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SceneController2 {
		private Stage stage;
		private StackPane root; 
		private Scene scene;
		
	public void switchToGameOver(StackPane canvasPane, int status, Integer gameScore) throws IOException {		
		stage = (Stage)canvasPane.getScene().getWindow();
		
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/brickBreakerEX/GameOver.fxml")); 
		AnchorPane menu = loader.load();
		root = new StackPane(imageV, menu);
	
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
