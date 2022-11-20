package brickBreakerEX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class BBX extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Image image = new Image("Background_menu.png");
			ImageView imageV = new ImageView();
			imageV.setImage(image);
			AnchorPane menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));
			StackPane root = new StackPane(imageV, menu);
			Scene scene = new Scene(root, 1300, 800);
			
			//String css = this.getClass().getResource("/application.css").toExternalForm();
			//scene.getStylesheets().add(css);
			//root.setDisable(true);
			primaryStage.setResizable(false);
			primaryStage.setTitle("BRICK BREAKER EX");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
