package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import threads.AttackPlayerThread;
import threads.AttackSpellThread;
import threads.UpdateThread;

public class Player extends Entity implements Cloneable {

	private static final long serialVersionUID = -6549749374819736742L;

	/**
	 * Max health of the player
	 */
	public static final double MAX_HEALTH = 100;

	/**
	 * Max armor of the player
	 */
	public static final double MAX_ARMOR = 100;

	/**
	 * GameManager of the match
	 */
	private GameManager gameManager;

	/**
	 * User playing the match
	 */
	private User user;

	/**
	 * Creation date of the match
	 */
	private LocalDate date;

	/**
	 * Currently active pressed keys
	 */
	private HashSet<String> currentlyActiveKeys;

	/**
	 * List of spells in the match
	 */
	private ArrayList<Spell> spells;

	/**
	 * List of entities in the match
	 */
	private ArrayList<Entity> entities;

	/**
	 * List of entities to remove in the match
	 */
	private ArrayList<Entity> toRemove;

	/**
	 * Current health of the player
	 */
	private double health;

	/**
	 * Current armor of the player
	 */
	private double armor;

	/**
	 * x coordinate of the click event
	 */
	private double clickX;

	/**
	 * y coordinate of the click event
	 */
	private double clickY;

	/**
	 * Current score of the match
	 */
	private double score;

	/**
	 * Determines if the match is lost
	 */
	private boolean lose;

	/**
	 * Determines if the game is pauses
	 */
	private boolean paused;

	/**
	 * Duration of the match in ms
	 */
	private long chronometer;

	/**
	 * Duration of a pause in ms
	 */
	private long pauseDuration;

	/**
	 * Start time of the match in ms
	 */
	private long t1;

	/**
	 * Start time of the pause in ms
	 */
	private long pauseTime1;

	/**
	 * End time of the pause in ms
	 */
	private long pauseTime2;

	/**
	 * QuadTree of the match
	 */
	private QuadTree qt;

	/**
	 * Current match max number of mobs
	 */
	private int maxMobs = 3;

	/**
	 * savename of the match
	 */
	private String saveName;

	/**
	 * Cycles interval between attack loops
	 */
	private int attackCounter;

	/**
	 * Creates and instance of Player or match
	 * 
	 * @param gameManager GameManager of the match
	 * @param user        User playing the match
	 * @param sprite      Animated image of the entity
	 * @param posX        the X coordinate of the entity
	 * @param posY        the y coordinate of the entity
	 * @param movement    The type of movement performed by the entity
	 * @param attack      The type of attack performed by the entity
	 * @throws FileNotFoundException File not found
	 */
	public Player(GameManager gameManager, User user, AnimatedImage sprite, double posX, double posY, Movement movement,
			Attack attack) throws FileNotFoundException {
		super(sprite, posX, posY, 45, 54, 50, 45, movement, attack);

		this.gameManager = gameManager;
		this.user = user;
		date = LocalDate.now();

		health = MAX_HEALTH;

		currentlyActiveKeys = new HashSet<String>();

		entities = new ArrayList<Entity>();
		entities.add(this);

		maxMobs = 3;

		spells = new ArrayList<Spell>();
		toRemove = new ArrayList<Entity>();
		qt = null;
		attackCounter = 0;

		score = 0;
		armor = 0;
		paused = false;
		lose = false;
		pauseDuration = 0;
		unPause();

	}

	/**
	 * Returns the list of all QuadTrees in Preorder
	 * 
	 * @return ArrayList QuadTrees list
	 */
	public ArrayList<QuadTree> preOrderQuadTree() {
		return preOrderQuadTree(qt);
	}

	/**
	 * Returns the list of all QuadTrees in Preorder recursively
	 * 
	 * @param e current QuadTree
	 * @return ArrayList<QuadTree> QuadTrees list
	 */
	private ArrayList<QuadTree> preOrderQuadTree(QuadTree e) {

		ArrayList<QuadTree> list = new ArrayList<QuadTree>();

		// Base step doesnt do anything
		// Recursive step
		if (e != null) {

			list.add(e);

			QuadTree northEast = e.getNorthEast();
			QuadTree northWest = e.getNorthWest();
			QuadTree southEast = e.getSouthEast();
			QuadTree southWest = e.getSouthWest();

			if (northEast != null) {
				list.addAll(preOrderQuadTree(northEast));
			}

			if (northWest != null) {
				list.addAll(preOrderQuadTree(northWest));
			}

			if (southEast != null) {
				list.addAll(preOrderQuadTree(southEast));
			}

			if (southWest != null) {
				list.addAll(preOrderQuadTree(southWest));
			}

		}
		return list;
	}

	/**
	 * Game loop that updates the state of all entities
	 * 
	 * @throws FileNotFoundException File not found
	 */
	public void updateEntities() throws FileNotFoundException {

		if (paused == false && lose == false) {

			attackCounter++;

			updateChronometer();
			createEntitiesLoop();

			if (attackCounter == 0) {
				updateQuadTreeLoop();
				attackLoop();
			} else if (attackCounter == 10) {
				attackCounter = -1;
			}

			removeEntitiesLoop();
			updateThreadLoop();
		}

	}

	/**
	 * Creates a new QuadTree of the current match
	 */
	private void updateQuadTreeLoop() {

		qt = new QuadTree(0, 0, 0, 1280, 720);

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.resetQuadTree();
			qt.insert(entity);
		}

	}

	/**
	 * Creates the threads of the for the attacks of the player
	 */
	private void attackPlayerThreadLoop() {

		int numberOfThreads = this.getQuadTrees().size() / 2;

		AttackPlayerThread[] threads = new AttackPlayerThread[(int) numberOfThreads];

		int intervals = (int) Math.ceil(entities.size() / (double) numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new AttackPlayerThread(this, this.getQuadTrees(), i * intervals, (i + 1) * intervals);
		}

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}

		for (int i = 0; i < numberOfThreads; i++) {
			try {

				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates the threads for the attacks of spells
	 */
	private void attackSpellThreadLoop() {

		int numberOfThreads = spells.size();

		AttackSpellThread[] threads = new AttackSpellThread[(int) numberOfThreads];

		int intervals = (int) Math.ceil(entities.size() / (double) numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new AttackSpellThread(this, spells, i * intervals, (i + 1) * intervals);
		}

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}

		for (int i = 0; i < numberOfThreads; i++) {
			try {

				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Makes a cycle in the attackloop
	 */
	private void attackLoop() {

		attackSpellThreadLoop();
		attackPlayerThreadLoop();

	}

	/**
	 * Updates the position of each entity in the match
	 */
	private void updateThreadLoop() {

		int numberOfThreads = entities.size() / 2;

		UpdateThread[] threads = new UpdateThread[(int) numberOfThreads];

		int intervals = (int) Math.ceil(entities.size() / (double) numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new UpdateThread(this, entities, i * intervals, (i + 1) * intervals);
		}

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i].start();
		}

		for (int i = 0; i < numberOfThreads; i++) {
			try {

				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Updates the chronometer or duration of the match
	 */
	private void updateChronometer() {
		chronometer = System.currentTimeMillis() - t1 - pauseDuration;
	}

	/**
	 * Makes a cycle in the creation of entitites loop
	 * 
	 * @throws FileNotFoundException
	 */
	private void createEntitiesLoop() throws FileNotFoundException {

		SlimeFactory.getInstance().setMaxMobs(maxMobs);

		Mob mob = SlimeFactory.getInstance().createMob(this);
		if (mob != null) {
			entities.add(mob);
		}

		Item item = PerksFactory.getInstance().createItem(this);
		if (item != null) {
			entities.add(item);
		}

	}

	/**
	 * Removes all entities that doesnt have health or spells that have performed
	 * their attack
	 */
	private void removeEntitiesLoop() {

		// Remove entities
		for (Entity entity : toRemove) {

			entities.remove(entity);

			if (entity instanceof Spell) {
				spells.remove(entity);
			}
		}

		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);

			if (entity instanceof Player && ((Player) entity).getHealth() <= 0) {

				lose();
				lose = true;
				entities.remove(i);
				i = i - 1;

			} else if (entity instanceof Mob && ((Mob) entity).getHealth() <= 0) {
				entities.remove(i);
				maxMobs++;
				i = i - 1;
			} else if (entity instanceof Spell && ((Spell) entity).intersectsWall()) {
				entities.remove(i);
				spells.remove(entity);
				i = i - 1;
			}

		}
	}

	/**
	 * Adds a new entity to the list of entities to remove
	 * 
	 * @param entity Entity to remove
	 */
	public void addToRemove(Entity entity) {
		toRemove.add(entity);
	}

	/**
	 * Makes the match to lose
	 */
	private void lose() {

		gameManager.addScore(gameManager.getCurrentUser(), score, chronometer, date);

	}

	/**
	 * Lose score of the match given an attack
	 * 
	 * @param attack attack
	 */
	public void loseScore(Attack attack) {

		score -= attack.getDamage();

		if (score < 0) {
			score = 0;
		}

	}

	/**
	 * Gain score of the match
	 * 
	 * @param entity entity-
	 */
	public void gainScore(Entity entity) {
		Attack attack = entity.getAttack();
		score += attack.getDamage();

	}

	/**
	 * Render all entities in the match
	 * 
	 * @param gc GraphicsContext
	 * @param t  Time of the match
	 */
	@SuppressWarnings("unused")
	public void renderEntities(GraphicsContext gc, double t) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(gc, t);
		}

		if (GameManager.DEBUG_MODE) {
			ArrayList<QuadTree> qts = preOrderQuadTree();

			for (int i = 0; i < qts.size(); i++) {
				QuadTree qt = qts.get(i);
				qt.render(gc);

			}
		}

	}

	/**
	 * Handles the event of a click and creates a new Spell
	 * 
	 * @param event Click Event
	 * @throws FileNotFoundException File not found
	 */
	public void mouseClickEvent(MouseEvent event) throws FileNotFoundException {

		if (paused == false && lose == false) {

			clickX = event.getSceneX();
			clickY = event.getSceneY();

			// System.out.println("click X: " + clickX + " " + clickY);

			Fireball fb = (Fireball) FireballFactory.getInstance().createSpell(this, clickX, clickY);
			entities.add(fb);
			spells.add(fb);
		}

	}

	/**
	 * Handles the event of a key pressed
	 * 
	 * @param event Key pressed
	 */
	public void keyPressedEvent(KeyEvent event) {
		currentlyActiveKeys.add(event.getCode().toString());

		if (event.getCode().toString().equals("ESCAPE") && paused == false) {
			pause();
		} else if (event.getCode().toString().equals("ESCAPE") && paused == true) {
			unPause();
		}

	}

	/**
	 * Handles de envent of a key released
	 * 
	 * @param event key release
	 */
	public void keyReleasedEvent(KeyEvent event) {
		currentlyActiveKeys.remove(event.getCode().toString());
	}

	/**
	 * Returns the set of current active keys
	 * 
	 * @return HashSet Set of current active keys
	 */
	public HashSet<String> getCurrentlyActiveKeys() {
		return currentlyActiveKeys;
	}

	/**
	 * Lose the player health
	 * 
	 * @param damage damaged dealt to the player
	 */
	public void loseHealth(double damage) {

		if (armor > 0 && damage > armor) {
			armor = 0;
			health -= damage - armor;

		} else if (armor > 0) {
			armor -= damage;
		} else {

			health -= damage;

		}

	}

	/**
	 * Gain health to the player
	 * 
	 * @param healthGained health gained ammount
	 */
	public void gainHealth(double healthGained) {
		health += healthGained;

		if (health > MAX_HEALTH) {
			health = MAX_HEALTH;
		}

	}

	/**
	 * Gain Armor to the player
	 * 
	 * @param armorGained armor gained
	 */
	public void gainArmor(double armorGained) {
		this.armor += armorGained;

		if (armor > MAX_ARMOR) {
			armor = MAX_ARMOR;
		}
	}

	/**
	 * Returns the health of the player
	 * 
	 * @return health of the player
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * Returns the list of all entities in the match
	 * 
	 * @return ArrayList entities in the match
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	/**
	 * Returns the score of the match
	 * 
	 * @return double score match
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Returns the armor of the player
	 * 
	 * @return double armor of the player
	 */
	public double getArmor() {
		return armor;
	}

	/**
	 * Gets the match durations
	 * 
	 * @return long match duration in ms
	 */
	public long getChronometer() {
		return chronometer;
	}

	/**
	 * Returns if the match is paused
	 * 
	 * @return True if the match is paused
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Returns is the match is lost
	 * 
	 * @return True if the match is lost
	 */
	public boolean isLose() {
		return lose;
	}

	/**
	 * Returns the current user of the match
	 * 
	 * @return User current user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Returns the creation date of the match
	 * 
	 * @return LocalDate match creation date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Returns the formatted duration of the match
	 * 
	 * @return String formated duration of the match
	 */
	public String getFormattedDuration() {

		long seconds = (chronometer / 1000) % 60;
		long minutes = (chronometer / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		return sMin + ":" + sSec;
	}

	/**
	 * Returns the savename of the match
	 * 
	 * @return String savename of the match
	 */
	public String getSaveName() {
		return saveName;
	}

	/**
	 * Sets the savename of the match
	 * 
	 * @param saveName savename
	 */
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	/**
	 * Unpauses the match
	 */
	public void unPause() {
		pauseTime2 = System.currentTimeMillis();
		pauseDuration += pauseTime2 - pauseTime1;
		paused = false;
	}

	/**
	 * Pauses the match
	 */
	public void pause() {
		pauseTime1 = System.currentTimeMillis();
		paused = true;
	}

	/**
	 * Clones the match
	 * 
	 * @return Object cloned match
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {

		Player clone = (Player) super.clone();

		ArrayList<Entity> entitiesClone = new ArrayList<Entity>();

		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);

			if (entity instanceof Player) {
				entitiesClone.add(clone);
			} else {
				entitiesClone.add((Entity) entity.clone());
			}

		}

		clone.entities = entitiesClone;

		return clone;

	}

}
