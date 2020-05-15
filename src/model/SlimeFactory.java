package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class SlimeFactory implements MobFactory{
	
	private static SlimeFactory factory;
	private int mobs;
	
	private SlimeFactory() {
		mobs = 0;
	}
	
	@Override
	public Mob createMob(Entity entity) throws FileNotFoundException {
		
		Slime slime = null;
		countMobs(entity);
		
		if(mobs <= 3) {
			
			Random r = new Random();
			double x = r.nextInt(1180) + 50;
			
			double y = r.nextInt(620) + 50;
		
			slime = new Slime(slimeSprite(), x, y, 60 ,50, 10, 15, 65, 20, new SlimeMovement(), new SlimeAttack());
		}
		
		return slime;
		//return new Slime(slimeSprite(), 400, 400, 60 ,50, 10, 15, 65, 20, new NoMovement(), new SlimeAttack());
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
	
	public AnimatedImage slimeSprite() throws FileNotFoundException {

		AnimatedImage slime = new AnimatedImage();

		Image[] imageArray = new Image[3];
		for (int i = 0; i < 3; i++) {
			imageArray[i] = new Image(new FileInputStream("sprites/PinkSlime" + i + ".png"), 80, 80, false, false);
		}
		slime.frames = imageArray;
		slime.duration = 0.300;

		return slime;

	}
	
	public static SlimeFactory getInstance() {
		
		if(factory == null) {
			factory = new SlimeFactory();
		}
		
		return factory;
		
	}
	
}
