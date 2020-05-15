package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Player extends Entity{
	
	private HashSet<String> currentlyActiveKeys;
	
	private ArrayList<Entity> entities;
	private double health;
	private double clickX;
	private double clickY;
	
	public Player(AnimatedImage sprite, double posX, double posY, Movement movement, Attack attack) throws FileNotFoundException {
		super(sprite, posX, posY, 45, 54, 50, 45, movement, attack);
		health = 100;
		
		currentlyActiveKeys = new HashSet<String>();
		
		entities = new ArrayList<Entity>();
		entities.add(this);
		
		SlimeFactory sf = new SlimeFactory();
		entities.add(sf.createMob());
		
	}
	
	public void mouseClickEvent(MouseEvent event) throws FileNotFoundException {
		clickX = event.getSceneX();
		clickY = event.getSceneY();
		
		System.out.println("click X: " + clickX + " " + clickY);
		
		Fireball fb = (Fireball) FireballFactory.getInstance().createSpell(this, clickX, clickY);
		entities.add(fb);
		
		
	}
	
	public void updateEntities() {
		
		for(int i=0; i<entities.size(); i++) {
			entities.get(i).update();
		}

	}
	
	public void renderEntities(GraphicsContext gc, double t) {
		for(int i=0; i<entities.size(); i++) {
			entities.get(i).render(gc, t);
		}
	}
	
	public void keyPressedEvent(KeyEvent event) {
		currentlyActiveKeys.add(event.getCode().toString());
		System.out.println(currentlyActiveKeys.toString());
	}
	
	public void keyReleasedEvent(KeyEvent event) {
		currentlyActiveKeys.remove(event.getCode().toString());
	}
	

	public HashSet<String> getCurrentlyActiveKeys() {
		return currentlyActiveKeys;
	}
	
	
}
