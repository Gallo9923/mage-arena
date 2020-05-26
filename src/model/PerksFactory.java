package model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


public class PerksFactory implements ItemsFactory {
	
	/**
	 * Instance of the class
	 */
	private static PerksFactory perksFactory;

	/**
	 * Amount of health items in the match
	 */
	private int health;
	
	/**
	 * Amount of armor items in the match
	 */
	private int armor;

	/**
	 * Creates and instance of PerksFactory
	 */
	private PerksFactory() {

	}
	
	/**
	 * Creates an instance of an item 
	 * @param player Current match
	 */
	@Override
	public Item createItem(Player player) throws FileNotFoundException {

		Item item = null;
		countItems(player.getEntities());
		Random r = new Random();
		
		if (player.getHealth() <= Player.MAX_HEALTH / 2 && health < 1) {

			
			double x = r.nextInt(1180) + 50;
			double y = r.nextInt(620) + 50;

			item = new Health(healthSprite(), x, y, 40, 40, 25, 20, new NoMovement(), new HealthPerkAttack());

		} else if (player.getHealth() == 100 && player.getArmor() < 100 && armor < 1 && r.nextDouble() > 0.999) {

			double x = r.nextInt(1180) + 50;
			double y = r.nextInt(620) + 50;

			item = new Armor(armorSprite(), x, y, 40, 50, 15, 10, new NoMovement(), new ArmorPerkAttack());
		}else if(player.getHealth() < 100 && health < 1 && r.nextDouble() > 0.999) {
			
			double x = r.nextInt(1180) + 50;
			double y = r.nextInt(620) + 50;

			item = new Health(healthSprite(), x, y, 40, 40, 25, 20, new NoMovement(), new HealthPerkAttack());
		}

		return item;
	}

	/**
	 * Counts the amount of items in the match
	 * @param entities Entities of the match
	 */
	private void countItems(ArrayList<Entity> entities) {

		health = 0;
		armor = 0;

		for (int i = 0; i < entities.size(); i++) {

			Entity curr = entities.get(i);

			if (curr instanceof Health) {
				health++;
			} else if (curr instanceof Armor) {
				armor++;
			}

		}

	}
	
	/**
	 * Returns animated image of the armor sprite
	 * @return AnimatedImage armor sprite
	 * @throws FileNotFoundException
	 */
	private AnimatedImage armorSprite() throws FileNotFoundException {

		String[] imageArray = new String[4];
		for (int i = 0; i < 4; i++) {
			imageArray[i] = "sprites/BluePotion" + i + ".png";
		}
		
		AnimatedImage armor = new AnimatedImage(imageArray, 0.125, 70, 70);
		
		return armor;

	}
	
	/**
	 * Returns the animated image of the health sprite
	 * @return AnimatedImage health sprite
	 * @throws FileNotFoundException
	 */
	private AnimatedImage healthSprite() throws FileNotFoundException {

		String[] imageArray = new String[4];
		for (int i = 0; i < 4; i++) {
			imageArray[i] = "sprites/Heart" + i + ".png";
		}
		
		AnimatedImage health = new AnimatedImage(imageArray, 0.125, 90, 90);
		
		return health;

	}

	/**
	 * Returns the instance of PerskFactory
	 * @return PerksFactory 
	 */
	public static PerksFactory getInstance() {

		if (perksFactory == null) {
			perksFactory = new PerksFactory();
		}

		return perksFactory;
	}

}
