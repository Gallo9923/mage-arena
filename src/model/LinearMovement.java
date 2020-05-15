package model;

public class LinearMovement implements Movement{
	
	private double moveX;
	private double moveY;
	private double slope;
	private double speed;
	
	public LinearMovement(double originX, double originY, double clickX, double clickY, double speed) {
		
		this.moveX = (clickX - originX);
		this.moveY = (clickY - originY);
		
		slope = moveY / moveX;
		
		this.speed = speed;	
		
	}

	@Override
	public void move(Entity entity) {
		
		double x = entity.getPosX();
		double y = entity.getPosY();
		
		entity.setPosX(x+(moveX*speed));
		entity.setPosY(y+(moveY*speed));
		
		
	}
	
	
	
	
}
w