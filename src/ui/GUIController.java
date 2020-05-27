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

	/**
	 * Gamemanager of the application
	 */
	private GameManager gameManager;

	/**
	 * Main of the application
	 */
	private Main main;

	@FXML
	private BorderPane mainPane;

	@FXML
	private Canvas canvas;

	@FXML
	private StackPane arenaMainStackPane;

	@FXML
	private StackPane pauseMenu;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private StackPane lostMenu;

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

	/**
	 * Creates an instance of GUIController
	 * 
	 * @param gm   gamemanager
	 * @param main main
	 */
	public GUIController(GameManager gm, Main main) {
		this.gameManager = gm;
		this.main = main;
	}

	/**
	 * Loads a saved game
	 * 
	 * @param event event
	 * @throws IOException Loading file
	 */
	@FXML
	public void loadGame(ActionEvent event) throws IOException {

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

	/**
	 * Displays the scores sorted by date
	 * 
	 * @param event event
	 */
	@FXML
	public void displaySortedByDate(ActionEvent event) {
		ArrayList<Score> scores = gameManager.getScores();
		scores.sort(new ScoreDateComparator());

		initializeScoreTable(scores);

	}

	/**
	 * Displays sscores sorted by score
	 * 
	 * @param event event
	 */
	@FXML
	public void displaySortedByScore(ActionEvent event) {
		ArrayList<Score> scores = gameManager.getScores();
		scores.sort(new ScorePointsComparator());

		initializeScoreTable(scores);

	}

	/**
	 * Initialized the score table
	 * 
	 * @param scores scores
	 */
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

	/**
	 * Displays the loaded games
	 */
	public void displayLoadGames() {
		ArrayList<Player> saves = gameManager.getSaves(gameManager.getCurrentUser());
		initializeLoadGamesTable(saves);
	}

	/**
	 * Initializes the load Games Table
	 * 
	 * @param saves saves
	 */
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

	/**
	 * Save the game
	 * 
	 * @param event event
	 */
	@FXML
	void saveGame(ActionEvent event) {

		gameManager.saveGame();
		savedSucessfully();
		arenaMainStackPane.requestFocus();

	}

	/**
	 * Sets the scene to a new game
	 * 
	 * @param event event
	 * @throws IOException Save problem
	 */
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

	/**
	 * Checks if the match has ended
	 */
	public void matchEndedCheckLoop() {

		if (gameManager.isLose()) {
			setSceneLose();
		}

	}

	/**
	 * Sets the scene to lose
	 */
	public void setSceneLose() {

		lostMenu.setVisible(true);
		losePoints.setText(score.getText());
		loseTime.setText(chronometer.getText());

	}

	/**
	 * Updates the chronometer of the game
	 */
	public void updateChronometer() {

		long duration = gameManager.getMatch().getChronometer();

		long seconds = (duration / 1000) % 60;
		long minutes = (duration / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		chronometer.setText(sMin + ":" + sSec);
	}

	/**
	 * Updates the player health bar
	 */
	public void updatePlayerHealthBar() {

		double health = gameManager.getMatch().getHealth();
		playerHealth.setProgress(health / Player.MAX_HEALTH);

	}

	/**
	 * Updates de score of the match
	 */
	public void updateScore() {
		double scoreValue = gameManager.getScore();
		score.setText(scoreValue + "");
	}

	/**
	 * Update entities of the match
	 * 
	 * @throws FileNotFoundException file not found
	 */
	public void updateEntities() throws FileNotFoundException {
		gameManager.updateEntities();
	}

	/**
	 * Renders the entitites of the match
	 * 
	 * @param gc GraphicsContext
	 * @param t  time
	 */
	public void renderEntities(GraphicsContext gc, double t) {
		updateChronometer();
		updatePlayerHealthBar();
		updatePlayerArmorBar();
		updateScore();
		gameManager.renderEntities(gc, t);
	}

	/**
	 * Updated the player armor bar
	 */
	public void updatePlayerArmorBar() {
		double armor = gameManager.getMatch().getArmor();
		playerArmor.setProgress(armor / Player.MAX_ARMOR);
	}

	/**
	 * Initializes the action handlers of the game
	 */
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

	/**
	 * Registers a new user
	 * 
	 * @param event
	 */
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

	/**
	 * Logins an user
	 * 
	 * @param event event
	 * @throws IOException IOException
	 */
	@FXML
	private void login(ActionEvent event) throws IOException {

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

	/**
	 * Saved Sucessfuly window
	 */
	public void savedSucessfully() {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "The match has been saved successfuly", ok);
		alert.setTitle("Game");
		alert.setHeaderText("Information");
		alert.showAndWait();
	}

	/**
	 * User already exist window
	 * 
	 * @param msg msg
	 */
	public void userAlreadyExist(String msg) {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "User " + msg + " Already exist. Choose another one.", ok);
		alert.setTitle("Login");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}

	/**
	 * Incorrect user window
	 * 
	 * @param msg msg
	 */
	public void incorrectUser(String msg) {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "User " + msg + " not found", ok);
		alert.setTitle("Login");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}

	/**
	 * Invalid form window
	 */
	public void invalidForm() {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION,
				"Looks like some of the fields are missing or have invalid values, please try again.", ok);
		alert.setTitle("Warning filling the survey");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}

	/**
	 * Resumes the current game
	 * 
	 * @param event
	 */
	@FXML
	void resumeGame(ActionEvent event) {
		pauseMenu.setVisible(false);
		gameManager.unPauseGame();

		arenaMainStackPane.requestFocus();
	}

	/**
	 * Handles a key pressed
	 * 
	 * @param event
	 */
	void keyPressed(KeyEvent event) {

		if (event.getCode().toString().equals("ESCAPE") && pauseMenu.isVisible()) {
			pauseMenu.setVisible(false);
		} else if (event.getCode().toString().equals("ESCAPE") && !pauseMenu.isVisible()) {
			pauseMenu.setVisible(true);
		}

	}

	/**
	 * Sets the scene of logs
	 * 
	 * @param event event
	 * @throws IOException IOException
	 */
	@FXML
	private void setSceneLogs(ActionEvent event) throws IOException {

		try {
			gameManager.isAdmin(gameManager.getCurrentUser().getUsername());

			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Logs.fxml"));
			fxmlLoader2.setController(this);
			StackPane stackPane = fxmlLoader2.load();

			displayLogs();

			mainPane.setCenter(stackPane);

		} catch (AccessDeniedException e) {
			accessDenied(e.getUsername());
		}

	}

	/**
	 * Access denied window
	 * 
	 * @param username username
	 */
	private void accessDenied(String username) {
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "The user" + username + " does not have access", ok);
		alert.setTitle("Login");
		alert.setHeaderText("Warning");
		alert.showAndWait();
	}

	/**
	 * Displays the logs in the table
	 */
	private void displayLogs() {

		ArrayList<Log> logs = (ArrayList<Log>) gameManager.inOrderLogs();
		initializeLogsTable(logs);

	}

	/**
	 * Displays the logs by date
	 * 
	 * @param event event
	 */
	@FXML
	public void displayLogsByDate(ActionEvent event) {

		try {
			String date = logsLabel.getText();
			ArrayList<Log> logs = gameManager.logsByDate(date);
			initializeLogsTable(logs);
		} catch (DateTimeParseException e) {
			invalidForm();
		}

	}

	/**
	 * displays the logs by session time
	 * 
	 * @param event event
	 * 
	 */
	@FXML
	public void displayLogsBySessionTime(ActionEvent event) {
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

	/**
	 * display the logs by Username
	 * 
	 * @param event
	 */
	@FXML
	void displayLogsByUsername(ActionEvent event) {

		String username = logsLabel.getText();
		ArrayList<Log> logs = gameManager.logsByUsername(username);
		initializeLogsTable(logs);

	}

	/**
	 * initializes the logs Table
	 * 
	 * @param logs logs list
	 */
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

	/**
	 * Closes the application
	 * 
	 * @param event event
	 * @throws FileNotFoundException file not found
	 */
	@FXML
	private void closeApplication(ActionEvent event) throws FileNotFoundException {

		// Add log
		gameManager.addLog();

		// reset session start
		gameManager.resetSessionStart();

		// Serialize information
		main.serializeScore();
		main.serializeModel();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	/**
	 * Sets the scene to load game
	 * 
	 * @param event event
	 * @throws IOException IOException
	 */
	@FXML
	private void setSceneLoadGame(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("LoadGames.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		displayLoadGames();

		mainPane.setCenter(stackPane);

	}

	/**
	 * Sets Scene to scoreboard
	 * 
	 * @param event event
	 * @throws IOException IOException
	 */
	@FXML
	private void setSceneScoreboard(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Scoreboard.fxml"));
		fxmlLoader2.setController(this);
		StackPane stackPane = fxmlLoader2.load();

		displaySortedByScore(event);

		mainPane.setCenter(stackPane);
	}

	/**
	 * Sets the Scene Menu
	 * 
	 * @param event event
	 * @throws IOException IOException
	 */
	@FXML
	private void setSceneMenu(ActionEvent event) throws IOException {

		// Sesion Time

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

	/**
	 * Queries a user by username
	 * 
	 * @param username username
	 * @return User user
	 */
	public User queryUser(String username) {
		return gameManager.queryUser(username);
	}

}
