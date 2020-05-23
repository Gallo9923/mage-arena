package model;

import java.io.Serializable;

public class ArmorPerkAttack implements Attack, Serializable{
	
	private static final long serialVersionUID = -7084016586950639511L;
	private double armorGained = 25;
	
	@Override
	public void attack(Entity entity) {
		
		Player player = (Player)entity;
		player.gainArmor(armorGained);
		
	}

	@Override
	public double getDamage() {
		return 0;
	}
	
	
}
