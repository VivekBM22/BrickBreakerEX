package brickBreakerEX;

import java.io.IOException;
import game.GameUI;
import highScore.LeaderBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameOverController {
	private int gScore;
	@FXML
	Button tryAgain;
	
	@FXML
	Label message;
	
	@FXML
	Label score;
	
	@FXML
	TextField nameTF;
	
	@FXML
	Label enterName;
	
	@FXML
	Button enter;
	
	@FXML
	Button rToMenu;
	
	public void lostAll() {
		message.setText("You Lost");
		score.setVisible(false);
		nameTF.setVisible(false);
		enterName.setVisible(false);
		enter.setVisible(false);
	}
	
	public void wonLevelSelect() {
		message.setText("You Won");
		score.setVisible(false);
		nameTF.setVisible(false);
		enterName.setVisible(false);
		enter.setVisible(false);
	}
	
	public void wonOtherModes(Integer gameScore) {
		tryAgain.setVisible(false);
		rToMenu.setVisible(false);
		
		if( GameUI.mode.equals("Tournament") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.TM_LB, "TM_LB.txt");
			if( gameScore > LeaderBoard.TM_LB.score[9]) {
				message.setText("New Highscore");
				nameTF.setVisible(true);
				enterName.setVisible(true);
				score.setText(gameScore.toString());
				gScore = gameScore;
			}else {
				score.setText(gameScore.toString());
				nameTF.setVisible(false);
				enterName.setVisible(false);
				enterName.setVisible(false);
			}
		} else if( GameUI.mode.equals("Time Trial") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.TTM_LB, "TTM_LB.txt");
			if( gameScore > LeaderBoard.TTM_LB.score[9] ) {
				message.setText("New Highscore");
				nameTF.setVisible(true);
				enterName.setVisible(true);
				score.setText(gameScore.toString());
				gScore = gameScore;
			}else {
				score.setText(gameScore.toString());
				nameTF.setVisible(false);
				enterName.setVisible(false);
				enterName.setVisible(false);
			}
		} else if( GameUI.mode.equals("Tournament EX") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.THM_LB, "THM_LB.txt");
			if( gameScore > LeaderBoard.THM_LB.score[9] ) {
				message.setText("New Highscore");
				nameTF.setVisible(true);
				enterName.setVisible(true);
				score.setText(gameScore.toString());
				gScore = gameScore;
			}else {
				score.setText(gameScore.toString());
				nameTF.setVisible(false);
				enterName.setVisible(false);
			}
		} 
		
	}
	
	public void restartLevel(ActionEvent event) {
		GameUI GUI = new GameUI();
		GUI.begin(event, GameUI.mode, GameUI.level);
	}
	
	public void switchToMenu( ActionEvent event) throws IOException {
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		AnchorPane menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		StackPane root = new StackPane(imageV, menu);
	
		Scene scene = new Scene(root, 1300, 800);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	public void enteredName(ActionEvent event) {
		if( GameUI.mode.equals("Tournament") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.TM_LB, "TM_LB.txt");
			if( gScore > LeaderBoard.TM_LB.score[9]) {
				String name = nameTF.getText();
				System.out.println(name);
				LeaderBoard.insertScore( name,  gScore, LeaderBoard.TM_LB);
				LeaderBoard.setFile(LeaderBoard.TM_LB, "TM_LB.txt");
			}
		} else if( GameUI.mode.equals("Time Trial") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.TTM_LB, "TTM_LB.txt");
			if( gScore > LeaderBoard.TTM_LB.score[9]) {
				String name = nameTF.getText();
				System.out.println(name);
				LeaderBoard.insertScore( name,  gScore, LeaderBoard.TTM_LB);
				LeaderBoard.setFile(LeaderBoard.TTM_LB, "TTM_LB.txt");
			}
		} else if( GameUI.mode.equals("Tournament EX") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.THM_LB, "THM_LB.txt");
			if( gScore > LeaderBoard.TM_LB.score[9]) {
				String name = nameTF.getText();
				System.out.println(name);
				LeaderBoard.insertScore( name,  gScore, LeaderBoard.THM_LB);
				LeaderBoard.setFile(LeaderBoard.THM_LB, "THM_LB.txt");
			}
		} 
		
		nameTF.setVisible(false);
		enterName.setVisible(false);
		enter.setVisible(false);
		tryAgain.setVisible(true);
		rToMenu.setVisible(true);
	}
	
	
}