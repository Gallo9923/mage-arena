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

		if (player.getHealth() <= Player.MAX_HEALTH / 2 && health < 1) {

			Random r = new Random();
			double x = r.nextInt(1180) + 50;
			double y = r.nextInt(620) + 50;

			item = new Health(healthSprite(), x, y, 100, 100, 0, 0, new NoMovement(), new ActivatePerkAttack());

		} else if (player.getHealth() == 100 && player.getArmor() < 100 && armor < 1) {

			Random r = new Random();
			double x = r.nextInt(1180) + 50;
			double y = r.nextInt(620) + 50;

			item = new Armor(armorSprite(), x, y, 100, 100, 0, 0, new NoMovement(), new ActivatePerkAttack());
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
			imageArray[i] = new Image(new FileInputStream("sprites/BluePotion" + i + ".png"), 60, 60, false, false);
		}
		armor.frames = imageArray;
		armor.duration = 0.125;

		return armor;

	}

	public AnimatedImage healthSprite() throws FileNotFoundException {

		AnimatedImage health = new AnimatedImage();

		Image[] imageArray = new Image[4];
		for (int i = 0; i < 4; i++) {
			imageArray[i] = new Image(new FileInputStream("sprites/Heart" + i + ".png"), 60, 60, false, false);
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
