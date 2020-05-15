package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Player extends Entity {

	public static final double MAX_HEALTH = 100;
	
	private HashSet<String> currentlyActiveKeys;

	private ArrayList<Entity> entities;
	private double health;
	private double clickX;
	private double clickY;

	public Player(AnimatedImage sprite, double posX, double posY, Movement movement, Attack attack)
			throws FileNotFoundException {
		super(sprite, posX, posY, 45, 54, 50, 45, movement, attack);
		health = MAX_HEALTH;

		currentlyActiveKeys = new HashSet<String>();

		entities = new ArrayList<Entity>();
		entities.add(this);

		/*
		SlimeFactory sf = new SlimeFactory();
		entities.add(sf.createMob());
		*/
		
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
						
						curr.attack(aux);
					}
				}
			} // else if(curr instanceof Item)

		}
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
		health = health - damage;
	}

	public double getHealth() {
		return health;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	
	
}
