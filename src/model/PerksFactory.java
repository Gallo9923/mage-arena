package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class PerksFactory implements ItemsFactory {

	private static PerksFactory perksFactory;

	private int health;
	private int armor;

	private PerksFactory() {

	}

	@Override
	public Item createItem(Player player) throws FileNotFoundException {

		Item item = null;
		countItems(player.getEntities());
		Random r = new Random();
		
		if (player.getHealth() <= Player.MAX_HEALTH / 2 && health < 1) {

			
			double x = r.nextInt(1180) + 50;
			double y = r.nextInt(620) + 50;

			item = new Health(healthSprite(), x, y, 40, 40, 25, 20, new NoMovement(), new HealthPerkAttack());

		} else if (player.getHealth() == 100 && player.getArmor() < 100 && armor < 1) {

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

	public void countItems(ArrayList<Entity> entities) {

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

	public AnimatedImage armorSprite() throws FileNotFoundException {

		AnimatedImage armor = new AnimatedImage();

		Image[] imageArray = new Image[4];
		for (int i = 0; i < 4; i++) {
			imageArray[i] = new Image(new FileInputStream("sprites/BluePotion" + i + ".png"), 70, 70, false, false);
		}
		armor.frames = imageArray;
		armor.duration = 0.125;

		return armor;

	}

	public AnimatedImage healthSprite() throws FileNotFoundException {

		AnimatedImage health = new AnimatedImage();

		Image[] imageArray = new Image[4];
		for (int i = 0; i < 4; i++) {
			imageArray[i] = new Image(new FileInputStream("sprites/Heart" + i + ".png"), 90, 90, false, false);
		}
		health.frames = imageArray;
		health.duration = 0.125;

		return health;

	}

	public static PerksFactory getInstance() {

		if (perksFactory == null) {
			perksFactory = new PerksFactory();
		}

		return perksFactory;
	}

}
