package brickBreakerEX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DifficultySelectContoller {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchToLevelSelect( ActionEvent event ) throws IOException {
		String mode = ((Button)event.getSource()).getText();
		LevelSelectController.mode = mode;
		
		root = FXMLLoader.load(getClass().getResource("levelSelect.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToGameModes( ActionEvent event ) throws IOException {
		root = FXMLLoader.load(getClass().getResource("GameModes.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}
