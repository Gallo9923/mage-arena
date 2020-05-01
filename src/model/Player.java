package model;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class Player extends Entity{
	
	private HashSet currentlyActiveKeys;
	
	private ArrayList<Entity> entities;
	private double health;
	private Movement movement;
	
	public Player(AnimatedImage sprite, double posX, double posY, Movement movement) {
		super(sprite, posX, posY, 100, 100);
		health = 100;
		
		this.movement = movement;
		currentlyActiveKeys = new HashSet();
		
		entities = new ArrayList<Entity>();
		entities.add(this);
	}

	public void updateEntities() {
		
		for(int i=0; i<entities.size(); i++) {
			entities.get(i).update();
		}
		
		
		
	}
	
	@Override
	public void update() {
		this.move();
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
	
	public void move() {
		movement.move(this);
	}

	public HashSet getCurrentlyActiveKeys() {
		return currentlyActiveKeys;
	}
	
	
}
