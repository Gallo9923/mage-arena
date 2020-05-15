package model;

public class SlimeAttack implements Attack{
	
	private long lastTime = 0;
	
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
		player.loseHealth(15);
	}

}
