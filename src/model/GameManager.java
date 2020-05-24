package model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import customExceptions.SaveNotFoundException;
import customExceptions.UserAlreadyExistException;
import customExceptions.UserNotFoundException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.AnimatedImage;

public class GameManager implements Serializable {

	private static final long serialVersionUID = -7251619495947830479L;
	private final double MAGE_WIDTH = 150;
	private final double MAGE_HEIGHT = 150;

	private Player match;
	private transient Score scores;
	private User users;
	private User currentUser;
	private ArrayList<Player> saves;

	public GameManager() {
		this.match = null;
		this.scores = null;
		this.users = null;
		this.saves = new ArrayList<Player>();
	}

	public void loadGame(String saveName) throws SaveNotFoundException {
		//TODO agregar match = game
		Player game = querySaves(saveName);
		match = game;
	}
	
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
		
		if(curr == null) {
			throw new SaveNotFoundException(saveName);
		}
		
		return curr;
		
	}

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

	public void saveGame() {
		
		//TODO add Cloned Object
		
		match.setSaveName(matchName());
		
		saves.add(match);
	}

	public ArrayList<Score> getScores() {

		ArrayList<Score> scoresAL = new ArrayList<Score>();

		Score curr = scores;

		while (curr != null) {

			scoresAL.add(curr);
			curr = curr.getNext();

		}

		return scoresAL;
	}

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

	public List<User> preOrderUser() {
		return preOrderUser(users);
	}

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

	public User queryUser(String username) {
		return queryUser(users, username);
	}

	public User queryUser(String username, String password) throws UserNotFoundException {

		User user = queryUser(users, username, password);

		if (user == null) {
			throw new UserNotFoundException(username);
		}

		return user;
	}

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

	public void newMatch() throws FileNotFoundException {

		match = new Player(this, currentUser, mageSprite(), 500, 500, new PlayerMovement(), new FireballAttack());

	}

	public void updateEntities() throws FileNotFoundException {
		match.updateEntities();
	}

	public void renderEntities(GraphicsContext gc, double t) {
		match.renderEntities(gc, t);
	}

	public AnimatedImage mageSprite() throws FileNotFoundException {

		String[] imageArray = new String[2];
		for (int i = 0; i < 2; i++) {
			imageArray[i] = "sprites/OrangeMage" + i + ".png";
		}

		AnimatedImage mage = new AnimatedImage(imageArray, 0.200, MAGE_WIDTH, MAGE_HEIGHT);

		return mage;

	}

	public void unPauseGame() {
		match.unPause();
	}

	public void mouseClickEvent(MouseEvent event) throws FileNotFoundException {
		match.mouseClickEvent(event);
	}

	public double getScore() {
		return match.getScore();
	}

	// To Delete
	public void keyPressedEvent(KeyEvent event) {
		match.keyPressedEvent(event);

	}

	// To Delete
	public void keyReleasedEvent(KeyEvent event) {
		match.keyReleasedEvent(event);
	}

	// Is Needed?
	public Player getMatch() {
		return match;
	}

	public boolean isWon() {
		return match.isWon();
	}

	public boolean isPaused() {
		return match.isPaused();
	}

	public boolean isLose() {
		return match.isLose();
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public ArrayList<Player> getSaves() {
		return saves;
	}

}
