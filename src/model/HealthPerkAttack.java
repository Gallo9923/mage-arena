package model;

import java.io.Serializable;

public class HealthPerkAttack implements Attack, Serializable {
	
	private static final long serialVersionUID = -5508463669289322059L;
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
