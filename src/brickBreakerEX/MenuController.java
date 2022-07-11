package brickBreakerEX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private AnchorPane Escene;
	
	
	public void switchToGameModes( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("GameModes.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToLeaderboard( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("leaderBoard.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void logout(ActionEvent e) {
		stage = (Stage) Escene.getScene().getWindow();
		stage.close();
	}
	
}
