package model;

import java.io.Serializable;

public class FireballAttack implements Attack, Serializable{
	
	private static final long serialVersionUID = -8610077028202899734L;
	
	/**
	 * The last time in which the fireball attacked
	 */
	private long lastTime = 0;
	
	/**
	 * The damage dealt by the fireball
	 */
	private double damage = 100;
	
	/**
	 * Creates and instance of the FireballAttack
	 */
	public FireballAttack()  {
		
	}
	
	/**
	 * Performs the attack
	 * @param entity to perform the attack
	 */
	@Override
	public void attack(Entity entity) {
		
		long currTime = System.currentTimeMillis();
		
		if(currTime-lastTime >= 10) {
			performAttack(entity);
			lastTime = currTime;
		}
		
	}
	
	/**
	 * Performs the attack
	 * @param entity to perform the attack
	 */
	private void performAttack(Entity entity) {
		Mob mob = (Mob)entity;
		mob.loseHealth(damage);
		
	}

	/**
	 * Returns the damage dealt by the attack
	 * @return double damage of the attack
	 */
	@Override
	public double getDamage() {
		return damage;
	}
	

}
