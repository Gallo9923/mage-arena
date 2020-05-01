package model;

import java.util.ArrayList;

public class Player extends Entity{
	
	private ArrayList<Entity> entities;
	private double health;
	private Movement movement;
	
	public Player(AnimatedImage sprite, double posX, double posY, Movement movement) {
		super(sprite, posX, posY);
		health = 100;
	}

	public void move() {
		movement.move();
	}
	
	
}
