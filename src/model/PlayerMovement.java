package model;

import java.io.Serializable;
import java.util.HashSet;

public class PlayerMovement implements Movement, Serializable {

	private static final long serialVersionUID = -1742767686780170839L;

	/**
	 * speed in x axis
	 */
	private double xChange = 2.5;

	/**
	 * speed in y axis
	 */
	private double yChange = 2.5;

	/**
	 * Creates an instance of player movement
	 */
	public PlayerMovement() {

	}

	/**
	 * Moves the entity
	 * 
	 * @param entity to move
	 */
	public void move(Entity entity) {

		double x = entity.getPosX();
		double y = entity.getPosY();
		HashSet<String> currentlyActiveKeys = ((Player) entity).getCurrentlyActiveKeys();

		if (currentlyActiveKeys.contains("A") && entity.intersectsLeftWall() == false) {
			x -= xChange;
			entity.setPosX(x);
		}
		if (currentlyActiveKeys.contains("D") && entity.intersectsRightWall() == false) {
			x += xChange;
			entity.setPosX(x);
		}
		if (currentlyActiveKeys.contains("W") && entity.intersectsTopWall() == false) {
			y -= yChange;
			entity.setPosY(y);
		}
		if (currentlyActiveKeys.contains("S") && entity.intersectsBottomWall() == false) {
			y += yChange;
			entity.setPosY(y);
		}

	}

}
