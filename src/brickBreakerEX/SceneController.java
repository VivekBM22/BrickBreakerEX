package brickBreakerEX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private AnchorPane Escene;
	
	public void SwitchtoMenu( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void SwitchtoGameModes( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("GameModes.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void SwitchtoLevelSelect( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("levelSelect.fxml"));
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void SwitchtoGameUI( ActionEvent e) {
		game.GameUI.begin(e);
	}
	
	public void logout(ActionEvent e) {
		stage = (Stage) Escene.getScene().getWindow();
		stage.close();
	}
	
}
