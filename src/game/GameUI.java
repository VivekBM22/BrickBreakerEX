package game;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class GameUI  {
	static GameEngine gameEngine;
	public static AnchorPane pauseMenu;
	public static String mode = null;
	public static int level = -1;
	public static StackPane gamePane;
	
	private static Stage primaryStage;
	
	public void begin(ActionEvent e, String mode, int level) {
		GameUI.mode = mode;
		GameUI.level = level;
		
		primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		StackPane root = new StackPane();
		gamePane = new StackPane();
		
		Scene scene  = new Scene(root, 1300, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
		
//		gamePane.setStyle(" -fx-stroke:5px; \n" + " -fx-stroke-fill:black;");
//		gamePane.setTranslateX(10);		// FlowPane
//		gamePane.setTranslateY(35);		// FlowPane
		root.getChildren().add(gamePane);
		
		pauseMenu = new AnchorPane();
		try {
			pauseMenu = FXMLLoader.load(getClass().getResource("PauseMenu.fxml"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		Adding image background 
//		root.setStyle("-fx-background-image : url('file:../../resources/Background.png'); \n" );
		root.setStyle("-fx-background-color : #000000" );
		Label timeLabel = new Label();
		gamePane.getChildren().add(timeLabel);
		
		gameEngine = new GameEngine();
		gamePane.getChildren().add(gameEngine.getCanvasPane());
		gamePane.getChildren().add(pauseMenu);
		pauseMenu.setVisible(false);
		
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
		
//		btn.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent ae) {
//				pauseButtonAction(ae);
//			}
//		});
//		
		gamePane.requestFocus();
		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				//gamePane.requestFocus();
				keyPressedForGame(ke);
				System.out.println("Key Pressed: " + ke.getCode());
			}
		});
		gamePane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				//gamePane.requestFocus();
				keyReleasedForGame(ke);
			}
		});
		
	}
	
//	public static void pauseButtonAction(ActionEvent ae) {
//		if(gameEngine.isInGame())
//			gameEngine.pause();
//		else if(gameEngine.isPaused())
//			gameEngine.unpause();
//	}
	
	public static void keyPressedForGame(KeyEvent ke) {
		if(gameEngine.isInGame()) {
			if(ke.getCode().equals(KeyCode.LEFT)) {
				gameEngine.getPaddle().setLeft(true);
			}
			else if(ke.getCode().equals(KeyCode.RIGHT)) {
				gameEngine.getPaddle().setRight(true);
			}
		}
	}
	
	public static void keyReleasedForGame(KeyEvent ke) {
		if(ke.getCode().equals(KeyCode.ESCAPE)) {
			if(gameEngine.isInGame()) {
				gameEngine.pause();
				pauseMenu.setVisible(true);
			}
			else if(gameEngine.isPaused()) {
				gameEngine.unpause();
				pauseMenu.setVisible(false);
			}
		}
		else if(ke.getCode().equals(KeyCode.LEFT))
			gameEngine.getPaddle().setLeft(false);
		else if(ke.getCode().equals( KeyCode.RIGHT))
			gameEngine.getPaddle().setRight(false);
	}

}