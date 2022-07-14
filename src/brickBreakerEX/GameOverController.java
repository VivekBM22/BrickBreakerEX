package brickBreakerEX;

import java.io.IOException;

import game.GameUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverController {
	
	@FXML
	Button tryAgain;
	
	@FXML
	Label message;
	
	@FXML
	Label score;
	
	public void lostAll() {
		message.setText("You Lost");
		score.setVisible(false);
	}
	
	public void wonLevelSelect() {
		message.setText("You Won");
		score.setVisible(false);
	}
	
	public void wonOtherModes(Integer gameScore) {
		score.setText(gameScore.toString());
		tryAgain.setVisible(false);
	}
	
	public void restartLevel(ActionEvent event) {
		game.GameUI.begin(event, GameUI.mode, GameUI.level);
	}
	
	public void switchToMenu( ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root, 1300, 800);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}