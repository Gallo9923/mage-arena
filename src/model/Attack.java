package model;

public interface Attack {

	/**
	 * The attack that realizes each entity
	 * 
	 * @param entity Entity that performs the attack
	 */
	public void attack(Entity entity);

	/**
	 * The amount of damage dealt by the attack
	 * 
	 * @return double damage dealt
	 */
	public double getDamage();
}
