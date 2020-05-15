package model;

public abstract class Mob extends Entity {

	private double health;
	private double damage;

	public Mob(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, double health, double damage, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
		this.health = health;
		this.damage = damage;
	}

}
