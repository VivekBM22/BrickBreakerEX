package brickBreakerEX;

import java.io.IOException;

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

public class DifficultySelectContoller {
	private Stage stage;
	private Scene scene;
	private StackPane root;
	
	public void switchToLevelSelect( ActionEvent event ) throws IOException {
		String mode = ((Button)event.getSource()).getText();
		LevelSelectController.mode = mode;
		
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		AnchorPane menu = FXMLLoader.load(getClass().getResource("levelSelect.fxml"));
		root = new StackPane(imageV, menu);
		
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchToGameModes( ActionEvent event ) throws IOException {
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		AnchorPane menu = FXMLLoader.load(getClass().getResource("GameModes.fxml"));
		root = new StackPane(imageV, menu);
		
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}
