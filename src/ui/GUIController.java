package ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import customExceptions.AccessDeniedException;
import customExceptions.SaveNotFoundException;
import customExceptions.UserAlreadyExistException;
import customExceptions.UserNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.GameManager;
import model.Log;
import model.Player;
import model.Score;
import model.ScoreDateComparator;
import model.ScorePointsComparator;
import model.User;

public class GUIController {

	private GameManager gameManager;
	private Main main;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private BorderPane mainPane;

	@FXML
	private Canvas canvas;

	@FXML
	private StackPane pauseMenu;

	@FXML
	private StackPane lostMenu;

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

	@FXML
	private Label losePoints;

	@FXML
	private Label loseTime;

	@FXML
	private TableView<Score> scoreboardTable;

	@FXML
	private TableColumn<Score, String> usernameColumn;

	@FXML
	private TableColumn<Score, Double> pointsColumn;

	@FXML
	private TableColumn<Score, String> timeColumn;

	@FXML
	private TableColumn<Score, LocalDate> dateColumn;

	@FXML
	private TableView<Player> loadGamesTable;

	@FXML
	private TableColumn<Player, String> saveNameColumn;

	@FXML
	private TableColumn<Player, LocalDate> loadGameDateColumn;

	@FXML
	private TableColumn<Player, String> gameTimeColumn;

	@FXML
	private TextField logsLabel;

	@FXML
	private TextField saveNameLabel;

	@FXML
	private TableView<Log> logsTableView;

	@FXML
	private TableColumn<Log, String> usernameLogsColumn;

	@FXML
	private TableColumn<Log, LocalDate> dateLogsColumn;

	@FXML
	private TableColumn<Log, String> timeLogsColumn;

	public GUIController(GameManager gm, Main main) {
		this.gameManager = gm;
		this.main = main;
	}

	@FXML
	void loadGame(ActionEvent event) throws IOException {

		String saveGameSTR = saveNameLabel.getText();

		try {
			gameManager.loadGame(saveGameSTR);

			Image arena = new Image(new FileInputStream("sprites/Arena.png"));

			// Initialize FXML
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Arena.fxml"));
			fxmlLoader2.setController(this);
			StackPane stackPane = fxmlLoader2.load();

			lostMenu.setVisible(false);
			pauseMenu.setVisible(true);
			mainPane.setCenter(stackPane);
			playerHealth.setStyle("-fx-accent: red");

			// arenaMainStackPane.requestFocus();
			initializeActionHandlers();

			// Initialize Game

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

							matchEndedCheckLoop();

						}
					});

			gameLoop.getKeyFrames().add(kf);
			gameLoop.play();

		} catch (SaveNotFoundException e) {

			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.INFORMATION, "The save " + e.getSaveName() + " was not found", ok);
			alert.setTitle("Load Game");
			alert.setHeaderText("Warning");
			alert.showAndWait();
		}

	}

	@FXML
	public void displaySortedByDate(ActionEvent event) {
		ArrayList<Score> scores = gameManager.getScores();
		scores.sort(new ScoreDateComparator());

		initializeScoreTable(scores);

	}

	@FXML
	public void displaySortedByScore(ActionEvent event) {
		ArrayList<Score> scores = gameManager.getScores();
		scores.sort(new ScorePointsComparator());

		initializeScoreTable(scores);

	}

	public void initializeScoreTable(ArrayList<Score> scores) {
		ObservableList<Score> scoresOL = FXCollections.observableArrayList(scores);
		scoreboardTable.setItems(scoresOL);

		usernameColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("username"));
		usernameColumn.setStyle("-fx-alignment: CENTER");

		pointsColumn.setCellValueFactory(new PropertyValueFactory<Score, Double>("score"));
		pointsColumn.setStyle("-fx-alignment: CENTER");

		timeColumn.setCellValueFactory(new PropertyValueFactory<Score, String>("formattedDuration"));
		timeColumn.setStyle("-fx-alignment: CENTER");

		dateColumn.setCellValueFactory(new PropertyValueFactory<Score, LocalDate>("date"));
		dateColumn.setStyle("-fx-alignment: CENTER");
	}

	public void displayLoadGames() {
		ArrayList<Player> saves = gameManager.getSaves(gameManager.getCurrentUser());
		initializeLoadGamesTable(saves);
	}

	public void initializeLoadGamesTable(ArrayList<Player> saves) {
		ObservableList<Player> savesOL = FXCollections.observableArrayList(saves);
		loadGamesTable.setItems(savesOL);

		saveNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("saveName"));
		saveNameColumn.setStyle("-fx-alignment: CENTER");

		loadGameDateColumn.setCellValueFactory(new PropertyValueFactory<Player, LocalDate>("date"));
		loadGameDateColumn.setStyle("-fx-alignment: CENTER");

		gameTimeColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("formattedDuration"));
		gameTimeColumn.setStyle("-fx-alignment: CENTER");
	}

	@FXML
	void saveGame(ActionEvent event) {

		gameManager.saveGame();
		savedSucessfully();
		arenaMainStackPane.requestFocus();

	}

	@FXML
	private void setSceneNewGame(ActionEvent event) throws IOException {

		Image arena = new Image(new FileInputStream("sprites/Arena.png"));

		// Initialize FXML
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Arena.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		lostMenu.setVisible(false);
		pauseMenu.setVisible(false);
		mainPane.setCenter(stackPane);
		playerHealth.setStyle("-fx-accent: red");

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

						matchEndedCheckLoop();

					}
				});

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();

	}

	public void matchEndedCheckLoop() {

		if (gameManager.isLose()) {
			setSceneLose();
		}

	}

	public void setSceneLose() {

		lostMenu.setVisible(true);
		losePoints.setText(score.getText());
		loseTime.setText(chronometer.getText());

	}

	public void updateChronometer() {

		long duration = gameManager.getMatch().getChronometer();

		long seconds = (duration / 1000) % 60;
		long minutes = (duration / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		chronometer.setText(sMin + ":" + sSec);
	}

	public void updatePlayerHealthBar() {

		double health = gameManager.getMatch().getHealth();
		playerHealth.setProgress(health / Player.MAX_HEALTH);

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
		playerArmor.setProgress(armor / Player.MAX_ARMOR);
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
	void register(ActionEvent event) {
		String un = username.getText();
		String pass = password.getText();

		if (un.length() != 0 && pass.length() != 0) {

			try {
				gameManager.addUser(un, pass);
			} catch (UserAlreadyExistException e) {
				userAlreadyExist(e.getUsername());
			}

			username.setText("");
			password.setText("");

		} else {
			invalidForm();
		}

	}

	@FXML
	private void login(ActionEvent event) throws IOException {

		// TODO REMOVE WHEN FINISH
		setSceneMenu(event);

		String un = username.getText();
		String pass = password.getText();

		username.setText("");
		password.setText("");

		if (un.length() != 0 && pass.length() != 0) {

			try {
				User user = gameManager.queryUser(un, pass);

				if (user != null) {
					gameManager.setCurrentUser(user);
					setSceneMenu(event);
				}

			} catch (UserNotFoundException e) {
				incorrectUser(e.getUsername());
			}

		} else {
			invalidForm();
		}

	}

	public void savedSucessfully() {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "The match has been saved successfuly", ok);
		alert.setTitle("Game");
		alert.setHeaderText("Information");
		alert.showAndWait();
	}

	public void userAlreadyExist(String msg) {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "User " + msg + " Already exist. Choose another one.", ok);
		alert.setTitle("Login");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}

	public void incorrectUser(String msg) {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "User " + msg + " not found", ok);
		alert.setTitle("Login");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}

	public void invalidForm() {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION,
				"Looks like some of the fields are missing or have invalid values, please try again.", ok);
		alert.setTitle("Warning filling the survey");
		alert.setHeaderText("Warning");
		alert.showAndWait();
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
		} else if (event.getCode().toString().equals("ESCAPE") && !pauseMenu.isVisible()) {
			pauseMenu.setVisible(true);
		}

	}

	@FXML
	private void setSceneLogs(ActionEvent event) throws IOException {

		try {
			gameManager.isAdmin(gameManager.getCurrentUser().getUsername());

			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Logs.fxml"));
			fxmlLoader2.setController(this);
			StackPane stackPane = fxmlLoader2.load();

			// TODO initializeTableView
			displayLogs();

			mainPane.setCenter(stackPane);

		} catch (AccessDeniedException e) {
			accessDenied(e.getUsername());
		}

	}

	private void accessDenied(String username) {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "The user" + username + " does not have access", ok);
		alert.setTitle("Login");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}
	
	private void displayLogs() {
		// TODO initialize TableView

		ArrayList<Log> logs = (ArrayList<Log>) gameManager.inOrderLogs();
		initializeLogsTable(logs);

	}

	@FXML
	void displayLogsByDate(ActionEvent event) {

		try {
			String date = logsLabel.getText();
			ArrayList<Log> logs = gameManager.logsByDate(date);
			initializeLogsTable(logs);
		} catch (DateTimeParseException e) {
			invalidForm();
		}

	}

	@FXML
	void displayLogsBySessionTime(ActionEvent event) {
		try {
			String duration = logsLabel.getText();

			if (duration.split(":").length == 2) {
				ArrayList<Log> logs = gameManager.logsBySessionTime(duration);
				initializeLogsTable(logs);
			} else {
				invalidForm();
			}

		} catch (NumberFormatException e) {
			invalidForm();
		}
	}

	@FXML
	void displayLogsByUsername(ActionEvent event) {

		String username = logsLabel.getText();
		ArrayList<Log> logs = gameManager.logsByUsername(username);
		initializeLogsTable(logs);

	}

	private void initializeLogsTable(ArrayList<Log> logs) {

		ObservableList<Log> logsOL = FXCollections.observableArrayList(logs);
		logsTableView.setItems(logsOL);

		usernameLogsColumn.setCellValueFactory(new PropertyValueFactory<Log, String>("username"));
		usernameLogsColumn.setStyle("-fx-alignment: CENTER");

		dateLogsColumn.setCellValueFactory(new PropertyValueFactory<Log, LocalDate>("date"));
		;
		dateLogsColumn.setStyle("-fx-alignment: CENTER");

		timeLogsColumn.setCellValueFactory(new PropertyValueFactory<Log, String>("formattedSessionTime"));
		timeLogsColumn.setStyle("-fx-alignment: CENTER");

	}

	@FXML
	private void closeApplication(ActionEvent event) throws FileNotFoundException {

		// TODO Add log
		gameManager.addLog();

		// TODO reset session start
		gameManager.resetSessionStart();

		main.serializeScore();
		main.serializeModel();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void setSceneLoadGame(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("LoadGames.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		displayLoadGames();

		mainPane.setCenter(stackPane);

	}

	@FXML
	private void setSceneScoreboard(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Scoreboard.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		displaySortedByScore(event);

		mainPane.setCenter(stackPane);
	}

	@FXML
	private void setSceneMenu(ActionEvent event) throws IOException {
		// TODO Sesion Time

		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Menu.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		mainPane.setCenter(stackPane);

		if (gameLoop != null) {
			gameLoop.stop();
		}

		if (gameManager.getSessionStart() == -1) {
			gameManager.setSessionStart();
		}

		gameManager.setMatch(null);

	}

	public User queryUser(String username) {
		return gameManager.queryUser(username);
	}

}
