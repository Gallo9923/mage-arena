package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameManager;
import model.Player;

public class GUIController {

	private GameManager gameManager;

	@FXML
	private BorderPane mainPane;

	@FXML
	private Canvas canvas;

	@FXML
	private StackPane pauseMenu;

	@FXML
	private StackPane arenaMainStackPane;

	@FXML
	private ProgressBar playerHealth;
	
	@FXML
	private ProgressBar playerArmor;
	
	@FXML 
	private Label score;

	@FXML 
	private Label chronometer;
	
	private Timeline gameLoop;
	
	public GUIController(GameManager gm) {
		this.gameManager = gm;
	}
	
	
	@FXML
	private void setSceneNewGame(ActionEvent event) throws IOException {

		Image arena = new Image(new FileInputStream("sprites/Arena.png"));

		// Initialize FXML
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Arena.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();
		
		pauseMenu.setVisible(false);
		mainPane.setCenter(stackPane);

		// arenaMainStackPane.requestFocus();
		initializeActionHandlers();

		// Initialize Game

		gameManager.newMatch();

		// Initialize Graphics
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);

		final long timeStart = System.currentTimeMillis();

		// Game Loop
		KeyFrame kf = new KeyFrame(Duration.seconds(0.017), // 60 FPS
				new EventHandler<ActionEvent>() {

					public void handle(ActionEvent ae) {

						// Initialize Canvas
						double t = (System.currentTimeMillis() - timeStart) / 1000.0;
						gc.clearRect(0, 0, 1280, 720);

						gc.drawImage(arena, 0, 0);

						// Game Logic
						
						try {
							updateEntities();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						renderEntities(gc, t);

					}
				});

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();

	}
	
	public void updateChronometer() {
		
		long duration = gameManager.getMatch().getChronometer();
		
		long milliseconds = duration % 1000;
		long seconds = (duration / 1000) % 60;
		long minutes = (duration / 60000) % 60;
		String sMil = milliseconds < 10 ? ("00" + milliseconds)
				: milliseconds < 100 ? ("0" + milliseconds) : ("" + milliseconds);
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);
		
		chronometer.setText(sMin + ":" + sSec);
	}

	public void updatePlayerHealthBar() {
	
		double health = gameManager.getMatch().getHealth();
		playerHealth.setProgress(health/Player.MAX_HEALTH);
		
	}
	
	public void updateScore() {
		double scoreValue = gameManager.getScore();
		score.setText(scoreValue + "");
	}
	
	public void updateEntities() throws FileNotFoundException {
		gameManager.updateEntities();
	}

	public void renderEntities(GraphicsContext gc, double t) {
		updateChronometer();
		updatePlayerHealthBar();
		updatePlayerArmorBar();
		updateScore();
		gameManager.renderEntities(gc, t);
	}

	public void updatePlayerArmorBar() {
		double armor = gameManager.getMatch().getArmor();
		playerArmor.setProgress(armor/Player.MAX_ARMOR);
	}
	
	private void initializeActionHandlers() {

		arenaMainStackPane.requestFocus();
		arenaMainStackPane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				gameManager.keyPressedEvent(event);
			}

		});

		arenaMainStackPane.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				gameManager.keyReleasedEvent(event);
				keyPressed(event);
				
			}

		});

		arenaMainStackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {
					gameManager.mouseClickEvent(event);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

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
		gameManager.unPauseGame();
		
		arenaMainStackPane.requestFocus();
	}

	void keyPressed(KeyEvent event) {

		if (event.getCode().toString().equals("ESCAPE") && pauseMenu.isVisible()) {
			pauseMenu.setVisible(false);
		} else if (event.getCode().toString().equals("ESCAPE") && !pauseMenu.isVisible()){
			pauseMenu.setVisible(true);
		}

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
		
		if(gameLoop != null) {
			gameLoop.stop();
		}
		
	}

}
