package model;

import java.io.Serializable;
import java.util.Random;

public class SlimeMovement implements Movement, Serializable {

	private static final long serialVersionUID = 299327592650627822L;
	public static final int TURN_SPEED = 50;
	public static final double SPEED = 1;

	/**
	 * Cycle counter of moves
	 */
	private int count = 0;

	/**
	 * Determines if it will move in the x coordinate
	 */
	boolean xBool = true;

	/**
	 * Determine if it will move in the y coordiante
	 */
	boolean yBool = true;

	/**
	 * Creates an instance of SlimeMovement
	 */
	public SlimeMovement() {

	}

	/**
	 * Moves the Entity
	 * 
	 * @param entity Entity to be moved
	 */
	@Override
	public void move(Entity entity) {

		Random r = new Random();
		double x = entity.getPosX();
		double y = entity.getPosY();

		if (count == TURN_SPEED) {
			count = 0;

		}
		if (count == 0) {
			xBool = r.nextBoolean();
			yBool = r.nextBoolean();
			// System.out.println("\n \n \n \n");
			// System.out.println(xBool + " " + yBool );
		}

		if (entity.intersectsWall() == false) {
			if (xBool == true) {
				entity.setPosX(x + SPEED);
			} else {
				entity.setPosX(x - SPEED);
			}

			if (yBool == true) {
				entity.setPosY(y + SPEED);
			} else {
				entity.setPosY(y - SPEED);
			}

			count++;

		} else {

			if (entity.intersectsBottomWall()) {
				entity.setPosY(y - SPEED);
			} else if (entity.intersectsTopWall()) {
				entity.setPosY(y + SPEED);
			} else if (entity.intersectsLeftWall()) {
				entity.setPosX(x + SPEED);
			} else {
				entity.setPosX(x - SPEED);
			}

			count = 0;
		}

	}

}
