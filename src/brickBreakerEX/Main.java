package brickBreakerEX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class Main extends Application {
	GameEngine gameEngine;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		FlowPane gamePane = new FlowPane();
		
		Scene scene  = new Scene(root, 1300, 800);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Test Application 1");
		primaryStage.show();
		
		root.getChildren().add(gamePane);
		
		Button btn = new Button("Test Button");
		gamePane.getChildren().add(btn);
		
		Label timeLabel = new Label();
		gamePane.getChildren().add(timeLabel);
		
		gameEngine = new GameEngine(timeLabel);
		gamePane.getChildren().add(gameEngine.getCanvasPane());
		
		gameEngine.startGame(GameInfo.TOURNAMENT_MODE, 1);
		
		/*ballImg = gameEngine.ball.getBallImg(ballCanvas);
		ballImgReader = ballImg.getPixelReader();
		
		gc.drawImage(ballImg, 0, 0);*/
		
		Label txt = new Label();
		gamePane.getChildren().add(txt);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if(!gameEngine.isPaused()) {
					gameEngine.pause();
					txt.setText("Animation paused");
				}
				else {
					gameEngine.unpause();
					txt.setText("Animation continued\nTotal Pause Time: " + gameEngine.getPauseTime()/1000000000);
				}
			}
		});
		
		gamePane.requestFocus();
		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if(!gameEngine.isPaused()) {
					if(ke.getCode().equals(KeyCode.LEFT)) {
						gameEngine.paddle.setLeft(true);
						txt.setText("Moving left");
					}
					else if(ke.getCode().equals(KeyCode.RIGHT)) {
						gameEngine.paddle.setRight(true);
						txt.setText("Moving right");
					}
				}
			}
		});
		gamePane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if(ke.getCode().equals(KeyCode.ESCAPE)) {
					if(!gameEngine.isPaused()) {
						gameEngine.pause();
						txt.setText("Animation paused");
					}
					else {
						gameEngine.unpause();
						txt.setText("Animation continued\nTotal Pause Time: " + gameEngine.getPauseTime()/1000000000);
					}
				}
				else if(ke.getCode().equals(KeyCode.LEFT))
					gameEngine.paddle.setLeft(false);
				else if(ke.getCode().equals( KeyCode.RIGHT))
					gameEngine.paddle.setRight(false);
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}