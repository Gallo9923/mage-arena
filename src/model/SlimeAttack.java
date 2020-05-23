package model;

import java.io.Serializable;

public class SlimeAttack implements Attack, Serializable{
	
	private static final long serialVersionUID = -5388217505030785486L;
	private long lastTime = 0;
	private double damage = 15;
	
	@Override
	public void attack(Entity entity) {
		
		long currTime = System.currentTimeMillis();
	
		if(currTime-lastTime >= 1000) {
			performAttack(entity);
			lastTime = currTime;
		}
	}
	
	
	public void performAttack(Entity entity) {
		Player player = (Player)entity;
		
		player.loseScore(this);
		player.loseHealth(damage);
	}

	public double getDamage() {
		return damage;
	}
	
}
