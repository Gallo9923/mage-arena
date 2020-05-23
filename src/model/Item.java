package model;

public class Item extends Entity{

	private static final long serialVersionUID = -2633120274605645804L;

	public Item(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
	}

}
