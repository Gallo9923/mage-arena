package model;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import threads.AttackThread;
import threads.UpdateThread;

public class Player extends Entity implements Cloneable {

	private static final long serialVersionUID = -6549749374819736742L;
	public static final double MAX_HEALTH = 100;
	public static final double MAX_ARMOR = 100;

	private GameManager gameManager;
	private User user;
	private LocalDate date;

	private HashSet<String> currentlyActiveKeys;

	private ArrayList<Entity> entities;
	private ArrayList<Entity> toRemove;
	private double health;
	private double clickX;
	private double clickY;

	private double score;
	private double armor;

	private boolean won;
	private boolean lose;
	private boolean paused;
	private long chronometer;
	private long pauseDuration;
	private long t1;
	private long pauseTime1;
	private long pauseTime2;

	private QuadTree qt;
	
	private int maxMobs = 3;

	private String saveName;

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

		toRemove = new ArrayList<Entity>();
		qt = null;
		
		score = 0;
		armor = 0;
		paused = false;
		lose = false;
		won = false;
		pauseDuration = 0;
		unPause();

	}

	public void updateEntities() throws FileNotFoundException {

		if (paused == false && lose == false) {
			updateChronometer();
			createEntitiesLoop();
			
			updateQuadTreeLoop();
			attackLoopQuadTree();
			
			// attackLoop();
			
			//attackThreadLoop(); // Testing
			
			
			removeEntitiesLoop();
			//updateLoop();
			updateThreadLoop();
		}

	}

	private void updateQuadTreeLoop() {
		
		qt = new QuadTree(0, 0, 1280, 720);
		
		for(int i=0; i<entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.resetQuadTree();
			qt.insert(entity);
		}
		
	}
	
	private void attackLoopQuadTree() {

		for (int i = 0; i < entities.size(); i++) {

			Entity curr = entities.get(i);

			if (curr instanceof Mob && curr.intersects(this)) {
				curr.attack(this);

			} else if (curr instanceof Spell) {

				ArrayList<QuadTree> currQTs = curr.getQuadTrees();
				
				for(int j=0; j<currQTs.size(); j++) {
					
					QuadTree qt = currQTs.get(j);
					ArrayList<Entity> QTentities = qt.getQTEntities();
					
					for(int z=0; z<QTentities.size(); z++) {
						
						Entity auxEntity = QTentities.get(z);
						
						if(curr.equals(auxEntity) == false && auxEntity instanceof Mob && curr.intersects(auxEntity)) {
							
							gainScore(curr);
							curr.attack(auxEntity);
							entities.remove(curr);
							i--;
						
						}
						
					}
					
					
				}
				
				
			} else if (curr instanceof Item && curr.intersects(this)) {
				curr.attack(this);
				entities.remove(curr);
				i--;
			}

		}
	}
	
	private void updateThreadLoop() {

		int numberOfThreads = entities.size();

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

	private void attackThreadLoop() {

		int numberOfThreads = entities.size();

		AttackThread[] threads = new AttackThread[(int) numberOfThreads];

		int intervals = (int) Math.ceil(entities.size() / (double) numberOfThreads);

		for (int i = 0; i < numberOfThreads; i++) {
			threads[i] = new AttackThread(this, entities, i * intervals, (i + 1) * intervals);
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

		for (Entity entity : toRemove) {
			entities.remove(entity);
		}

		toRemove.removeAll(toRemove);

	}

	private void updateChronometer() {
		chronometer = System.currentTimeMillis() - t1 - pauseDuration;
	}

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

	private void removeEntitiesLoop() {

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
				i = i - 1;
			}

		}
	}

	public void addToRemove(Entity entity) {
		toRemove.add(entity);
	}

	private void lose() {

		gameManager.addScore(gameManager.getCurrentUser(), score, chronometer, date);

	}

	private void updateLoop() {
		for (int i = 0; i < entities.size(); i++) {

			Entity entity = entities.get(i);

			if (entity instanceof RedSlime) {
				RedSlime redSlime = (RedSlime) entity;
				redSlime.setDestX(this.getPosX() + this.getWidth() / 2);
				redSlime.setDestY(this.getPosY() + this.getHeight() / 2);
				redSlime.update();

			} else {
				entities.get(i).update();
			}

		}
	}

	private void attackLoop() {

		for (int i = 0; i < entities.size(); i++) {

			Entity curr = entities.get(i);

			if (curr instanceof Mob && curr.intersects(this)) {
				curr.attack(this);

			} else if (curr instanceof Spell) {

				for (int j = 0; j < entities.size(); j++) {

					Entity aux = entities.get(j);

					if (i != j && aux instanceof Mob && curr.intersects(aux)) {

						gainScore(curr);
						curr.attack(aux);
						entities.remove(curr);
						i--;
					}
				}
			} else if (curr instanceof Item && curr.intersects(this)) {
				curr.attack(this);
				entities.remove(curr);
				i--;
			}

		}
	}

	public void loseScore(Attack attack) {

		score -= attack.getDamage();

		if (score < 0) {
			score = 0;
		}

	}

	public void gainScore(Entity entity) {
		Attack attack = entity.getAttack();
		score += attack.getDamage();

	}

	public void renderEntities(GraphicsContext gc, double t) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(gc, t);
		}
	}

	public void mouseClickEvent(MouseEvent event) throws FileNotFoundException {

		if (paused == false && lose == false) {

			clickX = event.getSceneX();
			clickY = event.getSceneY();

			// System.out.println("click X: " + clickX + " " + clickY);

			Fireball fb = (Fireball) FireballFactory.getInstance().createSpell(this, clickX, clickY);
			entities.add(fb);
		}

	}

	public void keyPressedEvent(KeyEvent event) {
		currentlyActiveKeys.add(event.getCode().toString());

		if (event.getCode().toString().equals("ESCAPE") && paused == false) {
			pause();
		} else if (event.getCode().toString().equals("ESCAPE") && paused == true) {
			unPause();
		}

	}

	public void keyReleasedEvent(KeyEvent event) {
		currentlyActiveKeys.remove(event.getCode().toString());
	}

	public HashSet<String> getCurrentlyActiveKeys() {
		return currentlyActiveKeys;
	}

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

	public void gainHealth(double healthGained) {
		health += healthGained;

		if (health > MAX_HEALTH) {
			health = MAX_HEALTH;
		}

	}

	public void gainArmor(double armorGained) {
		this.armor += armorGained;

		if (armor > MAX_ARMOR) {
			armor = MAX_ARMOR;
		}
	}

	public double getHealth() {
		return health;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public double getScore() {
		return score;
	}

	public double getArmor() {
		return armor;
	}

	public long getChronometer() {
		return chronometer;
	}

	public boolean isWon() {
		return won;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isLose() {
		return lose;
	}

	public User getUser() {
		return user;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getFormattedDuration() {

		long seconds = (chronometer / 1000) % 60;
		long minutes = (chronometer / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		return sMin + ":" + sSec;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

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

	public void unPause() {
		pauseTime2 = System.currentTimeMillis();
		pauseDuration += pauseTime2 - pauseTime1;
		paused = false;
	}

	public void pause() {
		pauseTime1 = System.currentTimeMillis();
		paused = true;
	}
}
