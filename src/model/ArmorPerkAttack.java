package model;

public class ArmorPerkAttack implements Attack{

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
