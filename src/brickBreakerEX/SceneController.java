package brickBreakerEX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void SwitchtoScene1( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
		scene = new Scene(root, 1300, 800);
		String css = this.getClass().getResource("/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void SwitchtoScene2( ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
		scene = new Scene(root, 1300, 800);
		String css = this.getClass().getResource("/application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void SwitchtoGameUI( ActionEvent e) {
		GameUI.GameUI.begin(e);
	}
	
}
