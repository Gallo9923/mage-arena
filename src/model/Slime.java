package model;

public class Slime extends Mob{
	
	private Movement movement;
	
	public Slime(AnimatedImage sprite, double posX, double posY, double width, double height, double health,
			double damage, Movement movement) {
		super(sprite, posX, posY, width, height, health, damage);
		
		this.movement = movement;
	}
	
	@Override
	public void update() {
		this.move();
	}
	
	public void move() {
		movement.move(this);
	}

}
