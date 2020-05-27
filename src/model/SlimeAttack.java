package model;

import java.io.Serializable;

public class SlimeAttack implements Attack, Serializable {

	private static final long serialVersionUID = -5388217505030785486L;

	/**
	 * Time of the last performed attack
	 */
	private long lastTime = 0;

	/**
	 * Damage of the attack
	 */
	private double damage = 15;

	/**
	 * Attacks the entity
	 * 
	 * @param entity Entity to be attacked
	 */
	@Override
	public void attack(Entity entity) {

		long currTime = System.currentTimeMillis();

		if (currTime - lastTime >= 1000) {
			performAttack(entity);
			lastTime = currTime;
		}
	}

	/**
	 * Performs the slime Attack
	 * 
	 * @param entity entity to be attacked
	 */
	private void performAttack(Entity entity) {
		Player player = (Player) entity;

		player.loseScore(this);
		player.loseHealth(damage);
	}

	/**
	 * Returns the amount of damage dealt by the attack
	 * 
	 * @return double damage dealt
	 */
	public double getDamage() {
		return damage;
	}

}
