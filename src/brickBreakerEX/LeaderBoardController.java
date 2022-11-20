package brickBreakerEX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import highScore.LeaderBoard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LeaderBoardController implements Initializable{
	@FXML private TableView<scoreBoardDetails> TM;
	@FXML private TableView<scoreBoardDetails> TTM;
	@FXML private TableView<scoreBoardDetails> THM;
	
	@FXML private TableColumn<scoreBoardDetails, Integer> TM1;
	@FXML private TableColumn<scoreBoardDetails, String> TM2;
	@FXML private TableColumn<scoreBoardDetails, Integer> TM3;
	
	@FXML private TableColumn<scoreBoardDetails, Integer> TTM1;
	@FXML private TableColumn<scoreBoardDetails, String> TTM2;
	@FXML private TableColumn<scoreBoardDetails, Integer> TTM3;
	
	@FXML private TableColumn<scoreBoardDetails, Integer> THM1;
	@FXML private TableColumn<scoreBoardDetails, String> THM2;
	@FXML private TableColumn<scoreBoardDetails, Integer> THM3;
	
	private Stage stage;
	private Scene scene;
	private StackPane root;
	
	public void switchToMenu(ActionEvent event) throws IOException {
		Image image = new Image("Background_menu.png");
		ImageView imageV = new ImageView();
		imageV.setImage(image);
		AnchorPane menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		root = new StackPane(imageV, menu);
		scene = new Scene(root, 1300, 800);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
    public static ObservableList<scoreBoardDetails>  getTMData()
    {
        ObservableList<scoreBoardDetails> sbd = FXCollections.observableArrayList();
        LeaderBoard.setLeaderBoard(LeaderBoard.TM_LB, "TM_LB.txt");
        
        for ( int i=0 ; i<10  ; i++ ) {
        	sbd.add(new scoreBoardDetails( i+1, LeaderBoard.TM_LB.name[i], LeaderBoard.TM_LB.score[i]));
        }
        
        return sbd;
    }
    
    public static ObservableList<scoreBoardDetails>  getTTMData()
    {
        ObservableList<scoreBoardDetails> sbd = FXCollections.observableArrayList();
        LeaderBoard.setLeaderBoard(LeaderBoard.TTM_LB, "TM_LB.txt");
        
        for ( int i=0 ; i<10  ; i++ ) {
        	sbd.add(new scoreBoardDetails( i+1, LeaderBoard.TTM_LB.name[i], LeaderBoard.TTM_LB.score[i]));
        }
        return sbd;
    }
    
    public ObservableList<scoreBoardDetails>  getTHMData()
    {
        ObservableList<scoreBoardDetails> sbd = FXCollections.observableArrayList();
        LeaderBoard.setLeaderBoard(LeaderBoard.THM_LB, "TM_LB.txt");
        
        for ( int i=0 ; i<10  ; i++ ) {
        	sbd.add(new scoreBoardDetails( i+1, LeaderBoard.THM_LB.name[i], LeaderBoard.THM_LB.score[i]));
        }
        return sbd;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        //Cell value factory creation

        TM1.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,Integer>("position"));
        TM2.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,String>("name"));
        TM3.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,Integer>("score"));
        
        TTM1.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,Integer>("position"));
        TTM2.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,String>("name"));
        TTM3.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,Integer>("score"));
        
        THM1.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,Integer>("position"));
        THM2.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,String>("name"));
        THM3.setCellValueFactory(new PropertyValueFactory<scoreBoardDetails,Integer>("score"));
        
		TM.setItems(getTMData());
		TTM.setItems(getTTMData());
		THM.setItems(getTHMData());
		
	}
	
}


