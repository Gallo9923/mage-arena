package model;


public class FireballAttack implements Attack{
	
	private long lastTime = 0;
	private double damage = 100;
	
	public FireballAttack()  {
		
	}
	
	@Override
	public void attack(Entity entity) {
		
		long currTime = System.currentTimeMillis();
		
		if(currTime-lastTime >= 10) {
			performAttack(entity);
			lastTime = currTime;
		}
		
	}
	
	
	public void performAttack(Entity entity) {
		Mob mob = (Mob)entity;
		mob.loseHealth(damage);
		
	}

	@Override
	public double getDamage() {
		return damage;
	}
	

}
