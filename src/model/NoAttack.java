package model;

import java.io.Serializable;

public class NoAttack implements Attack, Serializable{

	private static final long serialVersionUID = 2435008620019760355L;

	@Override
	public void attack(Entity entity) {
		
		
	}

	@Override
	public double getDamage() {
		return 0;
	}

}
