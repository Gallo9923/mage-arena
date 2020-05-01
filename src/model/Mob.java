package model;

public abstract class Mob extends Entity{

	private double health;
	private double damage;
	
	public Mob(AnimatedImage sprite, double posX, double posY, double width, double height, double health, double damage) {
		super(sprite, posX, posY, width, height);
		this.health = health;
		this.damage = damage;
		
	}

}
