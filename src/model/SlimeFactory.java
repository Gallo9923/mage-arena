package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class SlimeFactory implements MobFactory {

	/**
	 * Instance of the factory
	 */
	private static SlimeFactory factory;

	/**
	 * Amount of mobs in the match
	 */
	private int mobs;

	/**
	 * Max number of mobs allowed in the match
	 */
	private int maxMobs;

	/**
	 * creates an instance of the SlimeFactory
	 */
	private SlimeFactory() {
		mobs = 0;
		maxMobs = 3;
	}

	/**
	 * Returns a Mob entity
	 * 
	 * @param entity Player of the match
	 */
	@Override
	public Mob createMob(Entity entity) throws FileNotFoundException {

		Mob mob = null;
		countMobs(entity);

		if (mobs <= maxMobs) {

			Random r = new Random();

			if (r.nextDouble() > 0.5) {
				mob = createSlime();
			} else {
				mob = createRedSlime();
			}

		}

		return mob;
		// return new Slime(slimeSprite(), 400, 400, 60 ,50, 10, 15, 65, 20, new
		// NoMovement(), new SlimeAttack());
	}

	/**
	 * Sets the maximum number of mobs allowed in the match
	 * 
	 * @param num max number of mobs
	 */
	public void setMaxMobs(int num) {
		maxMobs = num;
	}

	/**
	 * Returns a RedSlime instance
	 * 
	 * @return Mob RedSlime instance
	 * @throws FileNotFoundException file not found
	 */
	public Mob createRedSlime() throws FileNotFoundException {

		RedSlime redSlime = null;
		Random r = new Random();
		double x = r.nextInt(1180) + 50;

		double y = r.nextInt(620) + 50;

		redSlime = new RedSlime(redSlimeSprite(), x, y, 90, 75, 15, 22.5, 65, 20, new SlimeMovement(),
				new SlimeAttack());

		return redSlime;
	}

	/**
	 * Returns a Slime instance
	 * 
	 * @return Mob Slime instance
	 * @throws FileNotFoundException file not found
	 */
	public Mob createSlime() throws FileNotFoundException {
		Slime slime = null;
		Random r = new Random();
		double x = r.nextInt(1180) + 50;

		double y = r.nextInt(620) + 50;

		slime = new Slime(slimeSprite(), x, y, 90, 75, 15, 22.5, 65, 20, new SlimeMovement(), new SlimeAttack());
		return slime;
	}

	/**
	 * Counts the amount of mobs present in the match
	 * 
	 * @param entity player
	 */
	public void countMobs(Entity entity) {

		ArrayList<Entity> entities = ((Player) entity).getEntities();
		int counter = 0;

		for (int i = 0; i < entities.size(); i++) {

			Entity curr = entities.get(i);

			if (curr instanceof Mob) {
				counter++;
			}

		}

		mobs = counter;
	}

	/**
	 * Returns the sprite of the RedSlime
	 * 
	 * @return AnimatedImage RedSlime sprite
	 * @throws FileNotFoundException file not found
	 */
	public AnimatedImage redSlimeSprite() throws FileNotFoundException {

		String[] imageArray = new String[3];
		for (int i = 0; i < 3; i++) {
			imageArray[i] = "sprites" + File.separator + "RedSlime" + i + ".png";
		}

		AnimatedImage slime = new AnimatedImage(imageArray, 0.300, 120, 120);

		return slime;
	}

	/**
	 * Returns the sprite of the Slime
	 * 
	 * @return AnimatedImage Slime sprite
	 * @throws FileNotFoundException file not found
	 */
	public AnimatedImage slimeSprite() throws FileNotFoundException {

		String[] imageArray = new String[3];
		for (int i = 0; i < 3; i++) {
			imageArray[i] = "sprites" + File.separator + "PinkSlime" + i + ".png";
		}

		AnimatedImage slime = new AnimatedImage(imageArray, 0.300, 120, 120);

		return slime;

	}

	/**
	 * Returns the instance of the SlimeFactory
	 * 
	 * @return SlimeFactory SlimeFactory instance
	 */
	public static SlimeFactory getInstance() {

		if (factory == null) {
			factory = new SlimeFactory();
		}

		return factory;

	}

}
