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

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	public AnimatedImage getSprite() {
		return sprite;
	}
	
}
