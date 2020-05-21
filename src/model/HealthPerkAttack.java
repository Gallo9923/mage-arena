package model;

public class HealthPerkAttack implements Attack {
	
	private double healthGained = 25;
	
	@Override
	public void attack(Entity entity) {
		
		Player player = (Player)entity;
		player.gainHealth(healthGained);
		
		
	}

	@Override
	public double getDamage() {
		return 0;
	}

}
