package model;

public class RedSlime extends Mob{

	private static final long serialVersionUID = -1268473125115216302L;
	
	private double destX;
	private double destY;
	
	public RedSlime(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX,
			double offsetY, double health, double damage, Movement movement, Attack attack) {
		super(sprite, posX, posY, width, height, offsetX, offsetY, health, damage, movement, attack);
		
		this.destX = 0;
		this.destY = 0;
		
	}
	
	@Override
	public void move() {
		
		double originX = this.getPosX() + this.getWidth() / 2;
		double originY = this.getPosY() + this.getHeight() / 2;
		
		this.setMovement(new ConstantLinearMovement(originX, originY, destX, destY));
		
		this.getMovement().move(this);

	}

	public void setDestX(double destX) {
		this.destX = destX;
	}

	public void setDestY(double destY) {
		this.destY = destY;
	}
	
	
	
}
