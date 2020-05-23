package model;

import java.io.Serializable;

public class LinearMovement implements Movement, Serializable {

	private static final long serialVersionUID = -5898935576608943621L;
	private double moveX;
	private double moveY;
	private double speed;

	public LinearMovement(double originX, double originY, double clickX, double clickY, double speed) {

		this.moveX = (clickX - originX);
		this.moveY = (clickY - originY);
		this.speed = speed;

	}

	@Override
	public void move(Entity entity) {

		double x = entity.getPosX();
		double y = entity.getPosY();

		entity.setPosX(x + (moveX * speed));
		entity.setPosY(y + (moveY * speed));

	}

}
