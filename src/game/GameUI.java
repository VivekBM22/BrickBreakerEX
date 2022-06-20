package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class GameUI {
	static GameEngine gameEngine;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	private static Stage primaryStage;
	
	public static void begin(ActionEvent e) {
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		StackPane root = new StackPane();
		FlowPane gamePane = new FlowPane();
		
		Scene scene  = new Scene(root, 1300, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		root.getChildren().add(gamePane);
		
		gameEngine = new GameEngine();
		gamePane.getChildren().add(gameEngine.getCanvasPane());
		
		gameEngine.startGame(GameInfo.TOURNAMENT_MODE, 1);
		
		gamePane.requestFocus();
		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				keyPressedForGame(ke);
				System.out.println("Key Pressed: " + ke.getCode());
			}
		});
		
		gamePane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				keyReleasedForGame(ke);
			}
		});
	}
	
	public void pauseButtonAction(ActionEvent ae) {
		if(gameEngine.isInGame())
			gameEngine.pause();
		else if(gameEngine.isPaused())
			gameEngine.unpause();
	}
	
	public static void keyPressedForGame(KeyEvent ke) {
		if(gameEngine.isInGame()) {
			if(ke.getCode().equals(KeyCode.LEFT)) {
				gameEngine.paddle.setLeft(true);
			}
			else if(ke.getCode().equals(KeyCode.RIGHT)) {
				gameEngine.paddle.setRight(true);
			}
		}
	}
	
	public static void keyReleasedForGame(KeyEvent ke) {
		if(ke.getCode().equals(KeyCode.ESCAPE)) {
			if(gameEngine.isInGame()) {
				gameEngine.pause();
			}
			else if(gameEngine.isPaused())
				gameEngine.unpause();
		}
		else if(ke.getCode().equals(KeyCode.LEFT))
			gameEngine.paddle.setLeft(false);
		else if(ke.getCode().equals( KeyCode.RIGHT))
			gameEngine.paddle.setRight(false);
	}
}