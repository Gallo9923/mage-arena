package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class SlimeFactory implements MobFactory{
	
	private static SlimeFactory factory;
	private int mobs;
	private int maxMobs;
	
	private SlimeFactory() {
		mobs = 0;
		maxMobs = 3;
	}
	
	@Override
	public Mob createMob(Entity entity) throws FileNotFoundException {
		
		Mob mob = null;
		countMobs(entity);
		
		if(mobs <= maxMobs) {
			
			Random r = new Random();
			
			if(r.nextDouble() > 0.5) {
				mob = createSlime();
			}else {
				mob = createRedSlime();
			}
			
			
			
		}
		
		return mob;
		//return new Slime(slimeSprite(), 400, 400, 60 ,50, 10, 15, 65, 20, new NoMovement(), new SlimeAttack());
	}
	
	public void setMaxMobs(int num) {
		maxMobs = num;
	}
	
	public Mob createRedSlime() throws FileNotFoundException {
		
		RedSlime redSlime = null;
		Random r = new Random();
		double x = r.nextInt(1180) + 50;
		
		double y = r.nextInt(620) + 50;
		
		redSlime = new RedSlime(redSlimeSprite(), x, y, 60 ,50, 10, 15, 65, 20, new SlimeMovement(), new SlimeAttack());
		
		
		return redSlime;
	}
	
	
	public Mob createSlime() throws FileNotFoundException {
		Slime slime = null;
		Random r = new Random();
		double x = r.nextInt(1180) + 50;
		
		double y = r.nextInt(620) + 50;
	
		slime = new Slime(slimeSprite(), x, y, 60 ,50, 10, 15, 65, 20, new SlimeMovement(), new SlimeAttack());
		return slime;
	}
	
	public void countMobs(Entity entity) {
		
		ArrayList<Entity> entities = ((Player)entity).getEntities();
		int counter = 0;
		
		for(int i=0; i<entities.size(); i++) {
			
			Entity curr = entities.get(i);
			
			if(curr instanceof Mob) {
				counter++;
			}
			
		}
		
		mobs = counter;
	}
	
	public AnimatedImage redSlimeSprite() throws FileNotFoundException{
		
		String[] imageArray = new String[3];
		for (int i = 0; i < 3; i++) {
			imageArray[i] = "sprites" + File.separator + "RedSlime" + i + ".png";
		}

		AnimatedImage slime = new AnimatedImage(imageArray, 0.300, 80, 80);
		
		return slime;
	}
	
	public AnimatedImage slimeSprite() throws FileNotFoundException {

		String[] imageArray = new String[3];
		for (int i = 0; i < 3; i++) {
			imageArray[i] = "sprites" + File.separator + "PinkSlime" + i + ".png";
		}

		AnimatedImage slime = new AnimatedImage(imageArray, 0.300, 80, 80);
		
		return slime;

	}
	
	public static SlimeFactory getInstance() {
		
		if(factory == null) {
			factory = new SlimeFactory();
		}
		
		return factory;
		
	}
	
}
