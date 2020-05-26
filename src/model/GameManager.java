package model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import customExceptions.AccessDeniedException;
import customExceptions.SaveNotFoundException;
import customExceptions.UserAlreadyExistException;
import customExceptions.UserNotFoundException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.AnimatedImage;

public class GameManager implements Serializable {

	private static final long serialVersionUID = -7251619495947830479L;

	/**
	 * Determines if Debug Mode is turned on
	 */
	public static final boolean DEBUG_MODE = false;

	/**
	 * width of the mage
	 */
	private final double MAGE_WIDTH = 150;

	/**
	 * height of the mage
	 */
	private final double MAGE_HEIGHT = 150;

	/**
	 * Current active match
	 */
	private Player match;

	/**
	 * List of scores
	 */
	private transient Score scores;

	/**
	 * List of users
	 */
	private User users;

	/**
	 * Current logged active user
	 */
	private User currentUser;

	/**
	 * List of saves of matches
	 */
	private ArrayList<Player> saves;

	/**
	 * List of logs
	 */
	private Log logs;

	/**
	 * List of admin users
	 */
	private ArrayList<String> admins;

	/**
	 * Time in which the user logged in
	 */
	private long sessionStart;

	/**
	 * Creates and instance of GameManager
	 */
	public GameManager() {
		this.match = null;
		this.scores = null;
		this.users = null;
		this.logs = null;
		this.saves = new ArrayList<Player>();
		this.sessionStart = -1;

		this.admins = new ArrayList<String>();
		admins.add("Admin");
	}

	/**
	 * Determines if a user is Admin
	 * 
	 * @param username username of the user to be checked
	 * @throws AccessDeniedException
	 */
	public void isAdmin(String username) throws AccessDeniedException {

		if (admins.contains(username) == false) {
			throw new AccessDeniedException(username);
		}

	}

	/**
	 * Returns the logs that have less or equals than the duration
	 * 
	 * @param duration duration
	 * @return ArrayList<Log> list of logs
	 */
	public ArrayList<Log> logsBySessionTime(String duration) {

		// Add Throw Exception at bad parsing

		ArrayList<Log> logs = (ArrayList<Log>) inOrderLogs();
		ArrayList<Log> newLogs = new ArrayList<Log>();

		String[] d = duration.split(":");

		long minutes = Long.parseLong(d[0]) * 60000;
		long seconds = Long.parseLong(d[1]) * 1000;

		Long dur = minutes + seconds;

		for (int i = 0; i < logs.size(); i++) {

			Log log = logs.get(i);

			if (log.getSessionTime() <= dur) {
				newLogs.add(log);
			}

		}

		return newLogs;

	}

	/**
	 * Returns the logs in a current date
	 * 
	 * @param date date
	 * @return ArrayList<Log> list of logs
	 */
	public ArrayList<Log> logsByDate(String date) {

		// TODO Throw Exception if not parse able

		ArrayList<Log> logs = (ArrayList<Log>) inOrderLogs();
		ArrayList<Log> newLogs = new ArrayList<Log>();

		LocalDate LDdate = LocalDate.parse(date);

		for (int i = 0; i < logs.size(); i++) {

			Log log = logs.get(i);

			if (LDdate.equals(log.getDate())) {
				newLogs.add(log);
			}

		}

		return newLogs;

	}

	/**
	 * Returns the logs by a given username
	 * 
	 * @param username username
	 * @return ArrayList<Log> list of logs
	 */
	public ArrayList<Log> logsByUsername(String username) {
		ArrayList<Log> logs = (ArrayList<Log>) inOrderLogs();
		ArrayList<Log> newLogs = new ArrayList<Log>();

		for (int i = 0; i < logs.size(); i++) {

			Log log = logs.get(i);

			if (username.equals(log.getUser().getUsername())) {
				newLogs.add(log);
			}

		}

		return newLogs;

	}

	/**
	 * Returns the list of logs in InOrder
	 * 
	 * @return List<Log> list of logs
	 */
	public List<Log> inOrderLogs() {
		return inOrderLogs(logs);
	}

	/**
	 * Returns the list of logs in InOrder recursively
	 * 
	 * @param e Current Log
	 * @return List<Log> List of logs
	 */
	private List<Log> inOrderLogs(Log e) {

		ArrayList<Log> logsList = new ArrayList<Log>();

		if (e != null) {

			if (e.getLeft() != null) {
				logsList.addAll(inOrderLogs(e.getLeft()));
			}

			logsList.add(e);

			if (e.getRight() != null) {

				logsList.addAll(inOrderLogs(e.getRight()));
			}

		}

		return logsList;

	}

	/**
	 * Adds the log of the current session
	 */
	public void addLog() {

		long sessionTime = System.currentTimeMillis() - sessionStart;
		LocalDate date = LocalDate.now();

		if (logs == null) {
			logs = new Log(currentUser, sessionTime, date);
		} else {
			addLog(logs, currentUser, sessionTime, date);
		}
	}

	/**
	 * Adds a log
	 * 
	 * @param curr        Current log
	 * @param user        user
	 * @param sessionTime sessionTime of the current user
	 * @param date        Date of the log
	 */
	private void addLog(Log curr, User user, long sessionTime, LocalDate date) {

		if (sessionTime <= curr.getSessionTime()) {

			Log left = curr.getLeft();

			if (left == null) {
				curr.setLeft(new Log(currentUser, sessionTime, date));
				curr.getLeft().setParent(curr);
			} else {
				addLog(left, currentUser, sessionTime, date);
			}

		} else {

			Log right = curr.getRight();

			if (right == null) {
				curr.setRight(new Log(currentUser, sessionTime, date));
				curr.getRight().setParent(curr);
			} else {
				addLog(right, currentUser, sessionTime, date);
			}
		}
	}

	/**
	 * Loads the game with a given savename
	 * 
	 * @param saveName name of the game
	 * @throws SaveNotFoundException
	 */
	public void loadGame(String saveName) throws SaveNotFoundException {
		// TODO agregar match = game
		Player game = querySaves(saveName);
		try {
			match = (Player) game.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the game or save given a savename
	 * 
	 * @param saveName name of the game
	 * @return Player Game
	 * @throws SaveNotFoundException
	 */
	public Player querySaves(String saveName) throws SaveNotFoundException {
		// TODO
		boolean found = false;
		Player curr = null;

		for (int i = 0; i < saves.size() && found == false; i++) {

			curr = saves.get(i);

			if (saveName.equalsIgnoreCase(curr.getSaveName())) {
				found = true;
			}
		}

		if (found == false) {
			throw new SaveNotFoundException(saveName);
		}

		return curr;

	}

	/**
	 * Returns a valid match name or savename
	 * 
	 * @return String valid Savename
	 */
	public String matchName() {

		String save = "save-";
		int cont = 1;
		boolean found = false;

		while (found == false) {

			boolean same = false;

			for (int i = 0; i < saves.size() && same == false; i++) {

				String saveName = save + cont;

				if (saveName.equals(saves.get(i).getSaveName())) {
					cont++;
					same = true;
				}

			}

			if (same == false) {
				found = true;
			}

		}

		return save + cont;
	}

	/**
	 * Saves the game to saves
	 */
	public void saveGame() {

		try {

			Player clone = (Player) match.clone();
			clone.setSaveName(matchName());
			saves.add(clone);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the scores
	 * 
	 * @return ArrayList<Score> List of scores
	 */
	public ArrayList<Score> getScores() {

		ArrayList<Score> scoresAL = new ArrayList<Score>();

		Score curr = scores;

		while (curr != null) {

			scoresAL.add(curr);
			curr = curr.getNext();

		}

		return scoresAL;
	}

	/**
	 * Adds a score of a match
	 * 
	 * @param user     User of the match
	 * @param score    Score obtained in the match
	 * @param duration duration of the match
	 * @param date     Date of the match
	 */
	public void addScore(User user, double score, long duration, LocalDate date) {

		Score curr = scores;
		Score newElement = new Score(user, score, duration, date);

		if (scores == null) {
			scores = newElement;

		} else {

			while (curr.getNext() != null) {
				curr = curr.getNext();
			}

			curr.setNext(newElement);
			newElement.setPrev(curr);

		}

	}

	/**
	 * Returns a score given the parametters
	 * 
	 * @param user     User of the match
	 * @param score    Score of the match
	 * @param duration Duration of the match
	 * @param date     Date of the match
	 * @return
	 */
	public Score queryScore(User user, double score, long duration, LocalDate date) {

		Score curr = scores;
		boolean found = false;
		Score scoreObj = null;

		while (curr != null && found == false) {

			if (curr.getUser().equals(user) && curr.getScore() == score && curr.getDuration() == duration
					&& curr.getDate().equals(date)) {

				found = true;
				scoreObj = curr;
			}

			curr = curr.getNext();
		}

		return scoreObj;
	}

	/**
	 * Returns the list of users in Preorder
	 * 
	 * @return List<User> list of users
	 */
	public List<User> preOrderUser() {
		return preOrderUser(users);
	}

	/**
	 * Retunrs the list of users in Preorder recursively
	 * 
	 * @param e current user
	 * @return List<User> list of users
	 */
	private List<User> preOrderUser(User e) {

		ArrayList<User> list = new ArrayList<User>();

		// Base step doesnt do anything
		// Recursive step
		if (e != null) {

			list.add(e);

			User nextLeft = e.getLeft();
			User nextRight = e.getRight();

			if (nextLeft != null) {
				list.addAll(preOrderUser(nextLeft));
			}

			if (nextRight != null) {
				list.addAll(preOrderUser(nextRight));
			}

		}
		return list;
	}

	/**
	 * Adds a new user
	 * 
	 * @param username username of the new user
	 * @param password password of the new user
	 * @throws UserAlreadyExistException
	 */
	public void addUser(String username, String password) throws UserAlreadyExistException {

		User found = queryUser(username);

		if (found == null) {
			if (users == null) {
				users = new User(username, password);
			} else {
				addUser(users, username, password);
			}
		} else {
			throw new UserAlreadyExistException(username);
		}

	}

	/**
	 * Adds a new user recursively
	 * 
	 * @param username username of the new user
	 * @param password password of the new user
	 * @throws UserAlreadyExistException
	 */
	private void addUser(User curr, String username, String password) {

		if (username.compareTo(curr.getUsername()) <= 0) {

			User left = curr.getLeft();

			if (left == null) {
				curr.setLeft(new User(username, password));
				curr.getLeft().setParent(curr);
			} else {
				addUser(left, username, password);
			}

		} else {

			User right = curr.getRight();

			if (right == null) {
				curr.setRight(new User(username, password));
				curr.getRight().setParent(curr);
			} else {
				addUser(right, username, password);
			}
		}
	}

	/**
	 * Returns a User given a username
	 * 
	 * @param username username
	 * @return User User
	 */
	public User queryUser(String username) {
		return queryUser(users, username);
	}

	/**
	 * Returns a user given a username and password
	 * 
	 * @param username username
	 * @param password password
	 * @return User user
	 * @throws UserNotFoundException
	 */
	public User queryUser(String username, String password) throws UserNotFoundException {

		User user = queryUser(users, username, password);

		if (user == null) {
			throw new UserNotFoundException(username);
		}

		return user;
	}

	/**
	 * Returns a User recursively
	 * 
	 * @param curr     Current user
	 * @param username username
	 * @param password password
	 * @return User user
	 */
	private User queryUser(User curr, String username, String password) {

		User user = null;

		if (curr == null) {

		} else {

			if (username.equals(curr.getUsername()) && password.equals(curr.getPassword())) {
				user = curr;
			} else if (username.compareTo(curr.getUsername()) <= 0) {
				user = queryUser(curr.getLeft(), username);
			} else {
				user = queryUser(curr.getRight(), username);
			}

		}

		return user;
	}

	/**
	 * Returns a user given a username recursively
	 * 
	 * @param curr     Current user
	 * @param username username
	 * @return User user
	 */
	private User queryUser(User curr, String username) {

		User user = null;

		if (curr == null) {

		} else {

			if (username.equals(curr.getUsername())) {
				user = curr;
			} else if (username.compareTo(curr.getUsername()) <= 0) {
				user = queryUser(curr.getLeft(), username);
			} else {
				user = queryUser(curr.getRight(), username);
			}

		}

		return user;
	}

	/**
	 * Creates a new match
	 * 
	 * @throws FileNotFoundException
	 */
	public void newMatch() throws FileNotFoundException {

		match = new Player(this, currentUser, mageSprite(), 500, 500, new PlayerMovement(), new FireballAttack());

	}

	/**
	 * Updates all entities of the match
	 * 
	 * @throws FileNotFoundException
	 */
	public void updateEntities() throws FileNotFoundException {
		match.updateEntities();
	}

	/**
	 * Renders all entities of the match
	 * 
	 * @param gc GraphicsContexct
	 * @param t  time of the current match
	 */
	public void renderEntities(GraphicsContext gc, double t) {
		match.renderEntities(gc, t);
	}

	/**
	 * Returns the sprite of a mage
	 * 
	 * @return AnimatedImage mage sprite
	 * @throws FileNotFoundException
	 */
	public AnimatedImage mageSprite() throws FileNotFoundException {

		String[] imageArray = new String[2];
		for (int i = 0; i < 2; i++) {
			imageArray[i] = "sprites/OrangeMage" + i + ".png";
		}

		AnimatedImage mage = new AnimatedImage(imageArray, 0.200, MAGE_WIDTH, MAGE_HEIGHT);

		return mage;

	}

	/**
	 * Unpauses the game
	 */
	public void unPauseGame() {
		match.unPause();
	}

	/**
	 * Gets score of the current match
	 * 
	 * @return
	 */
	public double getScore() {
		return match.getScore();
	}

	/**
	 * Handles a mouseClickEvent to the game
	 * 
	 * @param event
	 * @throws FileNotFoundException
	 */
	public void mouseClickEvent(MouseEvent event) throws FileNotFoundException {
		match.mouseClickEvent(event);
	}

	/**
	 * Handles a KeyPressedEvent to the match
	 * 
	 * @param event
	 */
	public void keyPressedEvent(KeyEvent event) {
		match.keyPressedEvent(event);

	}

	/**
	 * Handles a KeyReleasedEvent to the match
	 * 
	 * @param event
	 */
	public void keyReleasedEvent(KeyEvent event) {
		// Check viability of method
		match.keyReleasedEvent(event);
	}

	/**
	 * Returns the current match
	 * 
	 * @return Player match
	 */
	public Player getMatch() {
		return match;
	}

	/**
	 * Returns if the match is paused
	 * 
	 * @return boolean True if the match is paused
	 */
	public boolean isPaused() {
		return match.isPaused();
	}

	/**
	 * Returns if the match is lost
	 * 
	 * @return boolean True if the match is lost
	 */
	public boolean isLose() {
		return match.isLose();
	}

	/**
	 * Returns the current logged User
	 * 
	 * @return User user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets the current User
	 * 
	 * @param currentUser User
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Returns the list of saved matches given a User
	 * 
	 * @param user User
	 * @return ArrayList<Player> List of saved matches
	 */
	public ArrayList<Player> getSaves(User user) {

		ArrayList<Player> saves2 = new ArrayList<Player>();

		for (int i = 0; i < saves.size(); i++) {

			Player save = saves.get(i);
			if (user.equals(save.getUser())) {
				saves2.add(save);
			}

		}

		return saves2;
	}

	/**
	 * Sets the the match
	 * 
	 * @param match
	 */
	public void setMatch(Player match) {
		this.match = match;
	}

	/**
	 * Returns the time at which the session started
	 * 
	 * @return long time in ms
	 */
	public long getSessionStart() {
		return sessionStart;
	}

	/**
	 * Sets the session start time in ms
	 */
	public void setSessionStart() {
		this.sessionStart = System.currentTimeMillis();
	}

	/**
	 * Resets the session start time
	 */
	public void resetSessionStart() {
		this.sessionStart = -1;
	}

}
