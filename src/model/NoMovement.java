package model;

import java.io.Serializable;

public class NoMovement implements Movement, Serializable {

	private static final long serialVersionUID = -1416324783179318045L;

	/**
	 * Creates an instance of NoMovement
	 */
	public NoMovement() {

	}

	/**
	 * Dont move the entity
	 * 
	 * @param entity Entity to not move
	 */
	@Override
	public void move(Entity entity) {

		// Dont move

	}

}
