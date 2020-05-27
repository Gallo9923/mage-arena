package model;

import java.io.Serializable;

public class HealthPerkAttack implements Attack, Serializable {

	private static final long serialVersionUID = -5508463669289322059L;

	/**
	 * Health gained
	 */
	private double healthGained = 25;

	/**
	 * Creates an instance of HealthPerkAttack
	 */
	public HealthPerkAttack() {

	}

	/**
	 * Attack of Health
	 * 
	 * @param entity Entity to perform attack
	 */
	@Override
	public void attack(Entity entity) {

		Player player = (Player) entity;
		player.gainHealth(healthGained);

	}

	/**
	 * Returns the amount of damage to the player
	 * 
	 * @return double amount of damage
	 */
	@Override
	public double getDamage() {
		return 0;
	}

}
