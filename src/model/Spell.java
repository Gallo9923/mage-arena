package model;

public class Spell extends Entity{

	private static final long serialVersionUID = 2319411223993619863L;

	public Spell(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
		
	}

}
