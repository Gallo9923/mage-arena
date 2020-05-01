package model;

public abstract class Entity {
	
	private AnimatedImage sprite;
	private double posX;
	private double posY;
	
	public Entity(AnimatedImage sprite, double posX, double posY) {
		this.sprite = sprite;
		this.posX = posX;
		this.posY = posY;
	}
	
}
