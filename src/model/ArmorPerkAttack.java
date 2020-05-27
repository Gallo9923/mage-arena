package model;

import java.io.Serializable;

public class ArmorPerkAttack implements Attack, Serializable {

	private static final long serialVersionUID = -7084016586950639511L;

	/**
	 * The amount of armor that gives the Armor attack to the player
	 */
	private double armorGained = 25;

	/**
	 * Attack of the armor Item which gives armor to the player
	 * 
	 * @param entity entity that will perform the attack
	 */
	@Override
	public void attack(Entity entity) {

		Player player = (Player) entity;
		player.gainArmor(armorGained);

	}

	/**
	 * Returns the amount of damage dealt by the armor
	 * 
	 * @return double damage dealt
	 */
	@Override
	public double getDamage() {
		return 0;
	}

}
