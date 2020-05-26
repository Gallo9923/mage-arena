package model;

import java.io.Serializable;

public class LinearMovement implements Movement, Serializable {

	private static final long serialVersionUID = -5898935576608943621L;
	
	/**
	 * Speed in x coordinate
	 */
	private double moveX;
	
	/**
	 * Speed in y coordinate
	 */
	private double moveY;
	
	/**
	 * Modifier of speed
	 */
	private double speed;

	/**
	 * Creates an instance of LinearMovement
	 * @param originX x1 coordinate of origin movement
	 * @param originY y1 coordinate of origin movement
	 * @param clickX x2 coordinate of the destiny
	 * @param clickY y2 coordinate of the destiny
	 * @param speed Modifier of speed
	 */
	public LinearMovement(double originX, double originY, double clickX, double clickY, double speed) {

		this.moveX = (clickX - originX);
		this.moveY = (clickY - originY);
		this.speed = speed;

	}

	/**
	 * Moves the entity
	 * @param entity to move
	 */
	@Override
	public void move(Entity entity) {

		double x = entity.getPosX();
		double y = entity.getPosY();

		entity.setPosX(x + (moveX * speed));
		entity.setPosY(y + (moveY * speed));

	}

}
