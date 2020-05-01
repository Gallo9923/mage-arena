package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AnimatedImage;
import model.GameManager;
import model.Player;

public class GUIController {
	
	private GameManager gameManager;
	
	@FXML
	private BorderPane mainPane;

	@FXML
	private Canvas canvas;

	@FXML
	StackPane pauseMenu;

	@FXML
	private StackPane arenaMainStackPane;
	
	public GUIController(GameManager gm) {
		this.gameManager = gm;
	}
	
	@FXML
	private void setSceneNewGame(ActionEvent event) throws IOException {
		
		//Initialize FXML
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Arena.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		mainPane.setCenter(stackPane);

		
		//arenaMainStackPane.requestFocus();
		initializeActionHandlers();
		
		//Initialize Game
		
		gameManager.newMatch();
	
		
		//Initialize Graphics
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Timeline gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);

		final long timeStart = System.currentTimeMillis();
		
		//Game Loop
		KeyFrame kf = new KeyFrame(Duration.seconds(0.017), // 60 FPS
				new EventHandler<ActionEvent>() {
			
					public void handle(ActionEvent ae) {

						//Initialize Canvas
						double t = (System.currentTimeMillis() - timeStart) / 1000.0;
						gc.clearRect(0, 0, 1280, 720);
						Player match = gameManager.getMatch();
						//gc.fillRect(0, 0, 500, 500);
						
						//Update
						gameManager.update();
						gc.drawImage(match.getSprite().getFrame(t), match.getPosX(), match.getPosY());
						
					}
				});

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();

	
	}
	
	private void initializeActionHandlers() {
	
		arenaMainStackPane.requestFocus();
		arenaMainStackPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				gameManager.keyPressedEvent(event);
			}

		});
		
		arenaMainStackPane.setOnKeyReleased(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				gameManager.keyReleasedEvent(event);
			}
			
		});
		
		
	}
	
	@FXML
	private void login(ActionEvent event) throws IOException {
		setSceneMenu(event);

	}

	@FXML
	void resumeGame(ActionEvent event) {
		pauseMenu.setVisible(false);

		arenaMainStackPane.requestFocus();
	}

	@FXML
	void keyPressed(KeyEvent event) {

		if (event.getCode().toString().equals("ESCAPE") && pauseMenu.isVisible()) {
			pauseMenu.setVisible(false);
		} else {
			pauseMenu.setVisible(true);
		}

		arenaMainStackPane.requestFocus();
	}

	@FXML
	private void setSceneLogs(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Logs.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		mainPane.setCenter(stackPane);
	}

	@FXML
	private void closeApplication(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void setSceneLoadGame(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("LoadGames.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		mainPane.setCenter(stackPane);
	}

	

	@FXML
	private void setSceneScoreboard(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Scoreboard.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		mainPane.setCenter(stackPane);
	}

	@FXML
	private void setSceneMenu(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Menu.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		mainPane.setCenter(stackPane);
	}

}
