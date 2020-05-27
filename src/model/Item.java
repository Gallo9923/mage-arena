package model;

public class Item extends Entity {

	private static final long serialVersionUID = -2633120274605645804L;

	/**
	 * Creates an instance of item
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
	 */
	public Item(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
	}

}
