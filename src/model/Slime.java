package model;

public class Slime extends Mob{

	private static final long serialVersionUID = -6954209882572698770L;

	public Slime(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX, double offsetY, double health,
			double damage, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, health, damage, movement, attack);
		
	}
	

}
