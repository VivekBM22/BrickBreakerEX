package GameUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class SceneController2 {

	
	public void switchToGameOver(Canvas ballCanvas) throws IOException {

			Stage stage = (Stage)ballCanvas.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
			Scene scene = new Scene(root, 1300, 800);
			//String css = this.getClass().getResource().toExternalForm();
			scene.getStylesheets().add("/application.css");
			stage.setScene(scene);
			stage.show();
		
	}
	
}
