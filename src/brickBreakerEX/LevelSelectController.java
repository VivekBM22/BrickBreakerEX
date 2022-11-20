package brickBreakerEX;

import java.io.IOException;

import game.GameUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LevelSelectController {
	private Stage stage;
	private Scene scene;
	private StackPane root;
	
	static String mode;
	static int level;
	
	public void switchToGameUIFromLevelSelect( ActionEvent e ) {
		String l = ((Button)e.getSource()).getText();
		level = Integer.parseInt(l);
		
		GameUI GUI = new GameUI();
		GUI.begin(e, mode, level);
	}
	
	public void switchToDifficultySelect( ActionEvent event ) throws IOException {
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		AnchorPane menu = FXMLLoader.load(getClass().getResource("difficultySelect.fxml"));
		root = new StackPane(imageV, menu);
		
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}
