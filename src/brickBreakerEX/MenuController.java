package brickBreakerEX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class MenuController {
	private Stage stage;
	private Scene scene;
	private StackPane root;
	
	@FXML
	private AnchorPane Escene1;
	
	public void switchToGameModes( ActionEvent event) throws IOException {
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
	
	public void switchToLeaderboard( ActionEvent event) throws IOException {
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		AnchorPane menu = FXMLLoader.load(getClass().getResource("leaderBoard.fxml"));
		root = new StackPane(imageV, menu);
		
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void logout(ActionEvent e) {
		stage = (Stage)Escene1.getScene().getWindow();
		stage.close();
	}
	
	public void hoverOn( ActionEvent e ) {
		Button but = ((Button)e.getSource());
		but.setStyle("-fx-font-size: 5px;");
	}
	
	public void hoverOff( ActionEvent e ) {
		Button but = ((Button)e.getSource());
		but.setStyle("-fx-stroke-width: 0px; ");
	}
	
}
