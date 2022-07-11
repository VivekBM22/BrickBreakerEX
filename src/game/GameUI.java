package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class GameUI  {
	static GameEngine gameEngine;
	
	WritableImage ballImg;
	PixelReader ballImgReader;
	private static Stage primaryStage;
	
	public static void begin(ActionEvent e, String mode, int level) {
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		StackPane root = new StackPane();
		FlowPane gamePane = new FlowPane();
		
		Scene scene  = new Scene(root, 1300, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		root.getChildren().add(gamePane);
		
		Button btn = new Button("Test Button");
		gamePane.getChildren().add(btn);
		
		Label timeLabel = new Label();
		gamePane.getChildren().add(timeLabel);
		
		gameEngine = new GameEngine();
		gamePane.getChildren().add(gameEngine.getCanvasPane());
		
		System.out.println(mode);
		
		if( mode.equals("Tournament") )
			gameEngine.startGame(GameInfo.TOURNAMENT_MODE, 1);
		if( mode.equals("Time Trial") )
			gameEngine.startGame(GameInfo.TIME_TRIAL_MODE, 1);
		if( mode.equals("Tournament EX") )
			gameEngine.startGame(GameInfo.TOURNAMENT_HARD_MODE, 1);
		if( mode.equals("Easy") ) {
			switch(level) {
				case 1 : gameEngine.startGame(GameInfo.LEVEL_SELECT_MODE, 1); break;
				case 2 : gameEngine.startGame(GameInfo.LEVEL_SELECT_MODE, 2); break;
				case 3 : gameEngine.startGame(GameInfo.LEVEL_SELECT_MODE, 3); break;
				case 4 : gameEngine.startGame(GameInfo.LEVEL_SELECT_MODE, 4); break;
				case 5 : gameEngine.startGame(GameInfo.LEVEL_SELECT_MODE, 5); break;
			}
		}
		
		if( mode.equals("Hard") ) {
			switch(level) {
				case 1 : gameEngine.startGame(GameInfo.LEVEL_SELECT_HARD_MODE, 1); break;
				case 2 : gameEngine.startGame(GameInfo.LEVEL_SELECT_HARD_MODE, 2); break;
				case 3 : gameEngine.startGame(GameInfo.LEVEL_SELECT_HARD_MODE, 3); break;
				case 4 : gameEngine.startGame(GameInfo.LEVEL_SELECT_HARD_MODE, 4); break;
				case 5 : gameEngine.startGame(GameInfo.LEVEL_SELECT_HARD_MODE, 5); break;
			}
		}
		
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

	
	
}