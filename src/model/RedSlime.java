package model;

public class RedSlime extends Mob {

	private static final long serialVersionUID = -1268473125115216302L;

	/**
	 * x coordinate of player
	 */
	private double destX;

	/**
	 * y coordinate of player
	 */
	private double destY;

	/**
	 * Creates an instance of a RedSlime * @param sprite Animated image of the
	 * entity
	 * 
	 * @param sprite   Animated image of the entity
	 * @param posX     the X coordinate of the entity
	 * @param posY     the y coordinate of the entity
	 * @param width    The width of the entity
	 * @param height   The height of the entity
	 * @param offsetX  The offset in the x axis of the entity
	 * @param offsetY  The offset in the y axis of the entity
	 * @param movement The type of movement performed by the entity
	 * @param attack   The type of attack performed by the entity
	 * @param health   health of the entity
	 * @param damage   damage of the entity
	 */
	public RedSlime(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, double health, double damage, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, health, damage, movement, attack);

		this.destX = 0;
		this.destY = 0;

	}

	/**
	 * Moves the slime
	 */
	@Override
	public void move() {

		double originX = this.getPosX() + this.getWidth() / 2;
		double originY = this.getPosY() + this.getHeight() / 2;

		this.setMovement(new ConstantLinearMovement(originX, originY, destX, destY));

		this.getMovement().move(this);

	}

	/**
	 * Sets the new x coordinate of the player
	 * 
	 * @param destX player x coordinate
	 */
	public void setDestX(double destX) {
		this.destX = destX;
	}

	/**
	 * Sets the new y coordinate of the player
	 * 
	 * @param destY player y coordinate
	 */
	public void setDestY(double destY) {
		this.destY = destY;
	}

}
