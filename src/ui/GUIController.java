package ui;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUIController {

    @FXML
    private Button buttNewGame;

    @FXML
    private Button buttLoadGame;

    @FXML
    private Button buttScoreboard;

    @FXML
    private Button buttExit;

    @FXML
	private BorderPane borderPane;
    
    @FXML
    public void closeApplication(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    public void setSceneLoadGame(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("LoadGames.fxml"));
		Scene scene = new Scene(root);
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    public void setSceneNewGame(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Arena.fxml"));
		Scene scene = new Scene(root);
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }

    @FXML
    public void setSceneScoreboard(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Scoreboard.fxml"));
		Scene scene = new Scene(root);
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
    
    
    @FXML
    public void setSceneMenu(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		
		//Image image = new Image("sprites"+ File.separator + "Arena.png");
		
		//BackgroundImage backgroundImage = new BackgroundImage(new Image("sprites" + File.separator + "Arena.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
		//BackgroundImage backgroundImage = new BackgroundImage(new Image("sprites\\Arena.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
		//borderPane.setBackground(new Background(backgroundImage));
		
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
    }
    

}



