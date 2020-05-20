package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Player extends Entity {

	public static final double MAX_HEALTH = 100;
	public static final double MAX_ARMOR = 100;
	
	private HashSet<String> currentlyActiveKeys;

	private ArrayList<Entity> entities;
	private double health;
	private double clickX;
	private double clickY;

	private double score;
	private double armor;
	
	public Player(AnimatedImage sprite, double posX, double posY, Movement movement, Attack attack)
			throws FileNotFoundException {
		super(sprite, posX, posY, 45, 54, 50, 45, movement, attack);
		health = MAX_HEALTH;

		currentlyActiveKeys = new HashSet<String>();

		entities = new ArrayList<Entity>();
		entities.add(this);

		score = 0;
		armor = 0;
	}
	
	public void updateEntities() throws FileNotFoundException {
		
		createEntitiesLoop();
		attackLoop();
		removeEntitiesLoop();
		updateLoop();
	
	}
	
	private void createEntitiesLoop() throws FileNotFoundException {
		
		Mob mob = SlimeFactory.getInstance().createMob(this);
		if(mob != null) {
			entities.add(mob);
		}
		
		Item item = PerksFactory.getInstance().createItem(this);
		if(item != null) {
			entities.add(item);
		}
		
	}

	private void removeEntitiesLoop() {
		
		for(int i=0; i<entities.size(); i++) {
			
			Entity entity = entities.get(i);
			
			if(entity instanceof Player && ((Player) entity).getHealth() <= 0) {
				
				entities.remove(i);
				i = i-1;
				
			}else if(entity instanceof Mob && ((Mob) entity).getHealth() <= 0) {
				entities.remove(i);
				i = i-1;
			}else if(entity instanceof Spell && ((Spell) entity).intersectsWall()) {
				entities.remove(i);
				i = i-1;
			}
			
		}
	}
	
	private void updateLoop() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
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
			} else if(curr instanceof Item && curr.intersects(this)) {
				curr.attack(this);
				entities.remove(curr);
				i--;
			}
			
		

		}
	}
	
	public void loseScore(Attack attack) {
		
		score -= attack.getDamage();
		
		if(score < 0) {
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
		clickX = event.getSceneX();
		clickY = event.getSceneY();

		//System.out.println("click X: " + clickX + " " + clickY);

		Fireball fb = (Fireball) FireballFactory.getInstance().createSpell(this, clickX, clickY);
		entities.add(fb);

	}
	
	public void keyPressedEvent(KeyEvent event) {
		currentlyActiveKeys.add(event.getCode().toString());
		//System.out.println(currentlyActiveKeys.toString());
	}

	public void keyReleasedEvent(KeyEvent event) {
		currentlyActiveKeys.remove(event.getCode().toString());
	}

	public HashSet<String> getCurrentlyActiveKeys() {
		return currentlyActiveKeys;
	}
	
	public void loseHealth(double damage) {
		
		if(armor > 0 && damage > armor) {
			armor = 0;
			health -= damage - armor;
			
		}else if(armor > 0) {
			armor -= damage;
		}else {
			
			health -= damage;
			
		}
	
	}
	
	public void gainHealth(double healthGained) {
		health += healthGained;
		
		if(health > MAX_HEALTH) {
			health = MAX_HEALTH;
		}
		
	}
	
	public void gainArmor(double armorGained) {
		this.armor += armorGained;
		
		if(armor > MAX_ARMOR) {
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
}
