package ui;

import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.GameManager;

public class Main extends Application{
	
	private GameManager gameManager;
	private GUIController controller;
	
	public Main() {
		gameManager  = new GameManager();
		controller = new GUIController(gameManager);
	}
	
	public static void main(String[] args) {
		
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainPane.fxml"));
		fxmlLoader.setController(controller);
		BorderPane pane = fxmlLoader.load();
		
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Login.fxml"));
		fxmlLoader2.setController(controller);
		StackPane stackPane = fxmlLoader2.load();
		
		pane.setCenter(stackPane);
		
		Scene scene = new Scene(pane);

		primaryStage.setTitle("Mage Arena");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	
	
}
