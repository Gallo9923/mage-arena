package model;

public class Fireball extends Spell{
	
	private static final long serialVersionUID = -3607091972978814286L;

	public Fireball(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, Movement movement, Attack attack) {
		
		super(sprite, posX, posY, width, height, offsetX, offsetY, movement, attack);
	
	}

}
