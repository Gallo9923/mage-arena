package model;

import java.io.Serializable;

public class NoAttack implements Attack, Serializable{

	private static final long serialVersionUID = 2435008620019760355L;

	/**
	 * Creates an instance of NoAttack
	 */
	public NoAttack() {
		
	}
	
	/**
	 * No attack
	 * @param Entity Entity to attack
	 */
	@Override
	public void attack(Entity entity) {
		
		
	}

	/**
	 * No damage
	 */
	@Override
	public double getDamage() {
		return 0;
	}

}
