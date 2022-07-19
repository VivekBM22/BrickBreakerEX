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
import javafx.stage.Stage;

public class GameOverController {
	
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
	
	public void lostAll() {
		message.setText("You Lost");
		score.setVisible(false);
		nameTF.setVisible(false);
		enterName.setVisible(false);
	}
	
	public void wonLevelSelect() {
		message.setText("You Won");
		score.setVisible(false);
		nameTF.setVisible(false);
		enterName.setVisible(false);
	}
	
	public void wonOtherModes(Integer gameScore) {
		tryAgain.setVisible(false);
		
		if( GameUI.mode.equals("Tournament") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.TM_LB, "TM_LB.txt");
			if( gameScore > LeaderBoard.TM_LB.score[9]) {
				message.setText("New Highscore");
				nameTF.setVisible(true);
				enterName.setVisible(true);
				score.setText(gameScore.toString());
				String name = nameTF.getText();
				LeaderBoard.insertScore( name,  gameScore, LeaderBoard.TM_LB);
			}
		} else if( GameUI.mode.equals("Time Trial") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.TTM_LB, "TTM_LB.txt");
			if( gameScore > LeaderBoard.TTM_LB.score[9]) {
				message.setText("New Highscore");
				nameTF.setVisible(true);
				enterName.setVisible(true);
				score.setText(gameScore.toString());
				String name = nameTF.getText();
				LeaderBoard.insertScore( name,  gameScore, LeaderBoard.TTM_LB);
			}
		} else if( GameUI.mode.equals("Tournament EX") ) {
			LeaderBoard.setLeaderBoard(LeaderBoard.THM_LB, "THM_LB.txt");
			if( gameScore > LeaderBoard.TM_LB.score[9]) {
				message.setText("New Highscore");
				nameTF.setVisible(true);
				enterName.setVisible(true);
				score.setText(gameScore.toString());
				String name = nameTF.getText();
				LeaderBoard.insertScore( name,  gameScore, LeaderBoard.THM_LB);
			}
		} else {
			score.setText(gameScore.toString());
			nameTF.setVisible(false);
			enterName.setVisible(false);
		}
		
	}
	
	public void restartLevel(ActionEvent event) {
		game.GameUI.begin(event, GameUI.mode, GameUI.level);
	}
	
	public void switchToMenu( ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root, 1300, 800);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
}