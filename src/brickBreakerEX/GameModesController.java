package brickBreakerEX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameModesController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	static String mode;
	
	public void switchToGameUIFromGameMode( ActionEvent e ) {
		mode = ((Button)e.getSource()).getText(); 
		game.GameUI.begin(e, mode, 1);
	}
	
	public void switchToDifficultySelect( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("difficultySelect.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToMenu( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
}

