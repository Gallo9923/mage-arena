package ui;

import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	
	
	public static void main(String[] args) {
		
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		
		Image image = new Image(new FileInputStream("sprites" + File.separator + "Arena.png"));
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
		
		
		//myContainer.setBackground(new Background(myBF));
		
		primaryStage.setTitle("Mage Arena");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	
	
}
