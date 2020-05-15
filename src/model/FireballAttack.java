package model;


public class FireballAttack implements Attack{
	
	private long lastTime = 0;
	
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
		mob.loseHealth(100);
		
		System.out.println("HIYYYY");
	}
	

}
