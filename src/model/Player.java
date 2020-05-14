package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class Player extends Entity{
	
	private HashSet currentlyActiveKeys;
	
	private ArrayList<Entity> entities;
	private double health;
	
	public Player(AnimatedImage sprite, double posX, double posY, Movement movement) throws FileNotFoundException {
		super(sprite, posX, posY, 45, 54, 50, 45, movement);
		health = 100;
		
		currentlyActiveKeys = new HashSet();
		
		entities = new ArrayList<Entity>();
		entities.add(this);
		
		SlimeFactory sf = new SlimeFactory();
		entities.add(sf.createMob());
		
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
	

	public HashSet getCurrentlyActiveKeys() {
		return currentlyActiveKeys;
	}
	
	
}
