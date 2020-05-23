package model;

import java.io.Serializable;

public class NoMovement implements Movement, Serializable{

	private static final long serialVersionUID = -1416324783179318045L;

	@Override
	public void move(Entity entity) {
		
		//Dont move
		
	}

}
