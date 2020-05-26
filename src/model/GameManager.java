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

	public static final boolean DEBUG_MODE = false;
	
	private static final long serialVersionUID = -7251619495947830479L;
	private final double MAGE_WIDTH = 150;
	private final double MAGE_HEIGHT = 150;

	private Player match;
	private transient Score scores;
	private User users;
	private User currentUser;
	private ArrayList<Player> saves;
	private Log logs;
	private ArrayList<String> admins;
	
	private long sessionStart;

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
	
	public void isAdmin(String username) throws AccessDeniedException {
		
		 if(admins.contains(username) == false) {
			 throw new AccessDeniedException(username);
		 }
		
	}
	
	public ArrayList<Log> logsBySessionTime(String duration){
		
		//Add Throw Exception at bad parsing
		
		ArrayList<Log> logs = (ArrayList<Log>)inOrderLogs();
		ArrayList<Log> newLogs = new ArrayList<Log>();
		
		String[] d = duration.split(":");
		
		long minutes = Long.parseLong(d[0]) * 60000;
		long seconds = Long.parseLong(d[1]) * 1000;
				
		Long dur = minutes + seconds;
		
		for(int i=0; i<logs.size(); i++) {
			
			Log log = logs.get(i);
			
			if(log.getSessionTime() <= dur) {
				newLogs.add(log);
			}
			
		}
		
		return newLogs;
		
	}
	
	public ArrayList<Log> logsByDate(String date) {
		
		//TODO Throw Exception if not parse able
		
		ArrayList<Log> logs = (ArrayList<Log>)inOrderLogs();
		ArrayList<Log> newLogs = new ArrayList<Log>();
		
		LocalDate LDdate = LocalDate.parse(date);
		
		for(int i=0; i<logs.size(); i++) {
			
			Log log = logs.get(i);
			
			if(LDdate.equals(log.getDate())) {
				newLogs.add(log);
			}
			
		}
		
		return newLogs;
		
	}
	
	public ArrayList<Log> logsByUsername(String username){
		ArrayList<Log> logs = (ArrayList<Log>)inOrderLogs();
		ArrayList<Log> newLogs = new ArrayList<Log>();
		
		
		for(int i=0; i<logs.size(); i++) {
			
			Log log = logs.get(i);
			
			if(username.equals(log.getUser().getUsername())) {
				newLogs.add(log);
			}
			
		}
		
		return newLogs;
		
	}
	
	public List<Log> inOrderLogs(){
        return inOrderLogs(logs);
    }
    
    private List<Log> inOrderLogs(Log e){
        
        ArrayList<Log> logsList = new ArrayList<Log>();
        
        if(e!= null){
            
            if(e.getLeft() != null){
                 logsList.addAll(inOrderLogs(e.getLeft()));
            }
            
            logsList.add(e);
            
            if(e.getRight() != null){
                
                logsList.addAll(inOrderLogs(e.getRight()));
            }
            
        }
        
        return logsList;
                
    }
	
	public void addLog() {
		
		long sessionTime = System.currentTimeMillis() - sessionStart;
		LocalDate date = LocalDate.now();
		
		if (logs == null) {
			logs = new Log(currentUser, sessionTime, date);
		} else {
			addLog(logs, currentUser, sessionTime, date);
		}
	}
	
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
	
	public void loadGame(String saveName) throws SaveNotFoundException {
		//TODO agregar match = game
		Player game = querySaves(saveName);
		try {
			match = (Player) game.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
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
		
		if(found == false) {
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
		
		try {
			
			Player clone = (Player)match.clone();
			clone.setSaveName(matchName());
			saves.add(clone);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
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

	public ArrayList<Player> getSaves(User user) {
		
		ArrayList<Player> saves2 = new ArrayList<Player>();
		
		for(int i=0; i<saves.size(); i++) {
			
			Player save = saves.get(i);
			if(user.equals(save.getUser())) {
				saves2.add(save);
			}
			
		}
		
		return saves2;
	}

	public void setMatch(Player match) {
		this.match = match;
	}

	public long getSessionStart() {
		return sessionStart;
	}

	public void setSessionStart() {
		this.sessionStart = System.currentTimeMillis();
	}
	
	public void resetSessionStart() {
		this.sessionStart = -1;
	}
	
	
}
