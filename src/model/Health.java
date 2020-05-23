package model;

public class Health extends Item{

	private static final long serialVersionUID = -6216874199233634810L;

	public Health(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
		
	}

}
