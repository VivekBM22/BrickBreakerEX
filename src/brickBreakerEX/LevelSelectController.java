package brickBreakerEX;

import java.io.IOException;

import game.GameUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LevelSelectController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	static String mode;
	static int level;
	
	public void switchToGameUIFromLevelSelect( ActionEvent e ) {
		String l = ((Button)e.getSource()).getText();
		level = Integer.parseInt(l);
		
		GameUI GUI = new GameUI();
		GUI.begin(e, mode, level);
	}
	
	public void switchToDifficultySelect( ActionEvent event ) throws IOException {
		root = FXMLLoader.load(getClass().getResource("difficultySelect.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}
