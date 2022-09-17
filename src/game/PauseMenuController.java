package game;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PauseMenuController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	public AnchorPane Escene;
	
	public void resume(ActionEvent e) {
		GameUI.gameEngine.unpause();
		GameUI.pauseMenu.setVisible(false);
		GameUI.gamePane.requestFocus();
	}
	
	public void switchToMenu( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/brickBreakerEX/Menu.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void logout(ActionEvent e) {
		stage = (Stage)Escene.getScene().getWindow();
		stage.close();
	}
	
}
