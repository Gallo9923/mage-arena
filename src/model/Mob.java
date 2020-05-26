package model;

public abstract class Mob extends Entity {

	private static final long serialVersionUID = 3567264014866794180L;
	
	/**
	 * health of the mob
	 */
	private double health;
	
	/**
	 * damage that deals the mob
	 */
	private double damage;

	/**
	 * Creates an instance of Mob
	 * @param sprite Animated image of the entity
	 * @param posX the X coordinate of the entity
	 * @param posY the y coordinate of the entity
	 * @param width The width of the entity
	 * @param height The height of the entity
	 * @param offsetX The offset in the x axis of the entity
	 * @param offsetY The offset in the y axis of the entity
	 * @param movement The type of movement performed by the entity
	 * @param attack The type of attack performed by the entity
	 * @param health health of the mob
	 * @param damage damage that deals the mob
	 */
	public Mob(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, double health, double damage, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
		this.health = health;
		this.damage = damage;
	}

	/**
	 * Returns the health of the mob
	 * @return double health of mob
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * Returns the damage that deals the mob
	 * @return double damage
	 */
	public double getDamage() {
		return damage;
	}
	
	/**
	 * Performs action of loose health
	 * @param damage Damage received
	 */
	public void loseHealth(double damage) {
		health = health - damage;
	}
}
