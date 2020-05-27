package model;

import java.io.Serializable;

public class ConstantLinearMovement implements Movement, Serializable {

	private static final long serialVersionUID = -3256537714278160876L;

	/**
	 * The x coordinate of the origin point
	 */
	private double x1;

	/**
	 * The y coordinate of the origin point
	 */
	private double y1;

	/**
	 * The x coordinate of the destiny point
	 */
	private double x2;

	/**
	 * The y point of the destiny point
	 */
	private double y2;

	/**
	 * Speed of movement in the X axis
	 */
	private double MX = 1.25;

	/**
	 * The speed of movement in the Y axis
	 */
	private double MY = 1.25;

	/**
	 * Creates an instance of the ConstantLinearMovement
	 * 
	 * @param x1 The x coordinate of the origin point
	 * @param y1 The y coordinate of the origin point
	 * @param x2 The x coordinate of the destiny point
	 * @param y2 The y point of the destiny point
	 */
	public ConstantLinearMovement(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * Moves the entity
	 * 
	 * @param entity Entity that will move
	 */
	@Override
	public void move(Entity entity) {

		double x = entity.getPosX();
		double y = entity.getPosY();

		double DX = x2 - x1;
		double DY = y2 - y1;

		if (DX > 0) {
			x += MX;
		} else if (DX < 0) {
			x -= MX;
		}

		if (DY > 0) {
			y += MY;
		} else if (DY < 0) {
			y -= MY;
		}

		entity.setPosX(x);
		entity.setPosY(y);

	}

	/**
	 * Updates the new x coordinate of the destiny point
	 * 
	 * @param x2 the new x coordinate
	 */
	public void setX2(double x2) {
		this.x2 = x2;
	}

	/**
	 * Updates the new y coordinate of the destiny point
	 * 
	 * @param y2 the new y coordinate
	 */
	public void setY2(double y2) {
		this.y2 = y2;
	}

}
