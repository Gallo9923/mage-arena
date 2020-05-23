package model;

import java.io.FileNotFoundException;


public class FireballFactory implements SpellsFactory {

	private static FireballFactory factory = null;

	private FireballFactory() {

	}

	public static FireballFactory getInstance() {

		if (factory == null) {
			factory = new FireballFactory();
		}

		return factory;
	}

	@Override
	public Spell createSpell(Entity entity, double clickX, double clickY) throws FileNotFoundException {

		double x = entity.getPosX() + entity.getOffsetX();
		double y = entity.getPosY() + entity.getOffsetY();

		double originX = x + entity.getWidth() / 2;
		double originY = y + entity.getHeight() / 2;

		System.out.println("X: " + originX + " Y: " + originY);

		LinearMovement fireballMovement = new LinearMovement(originX, originY, clickX, clickY, 0.0125);

		Fireball fb = new Fireball(fireballSprite(), x, y, 43, 43, 8, 8, fireballMovement, new FireballAttack());

		return fb;
	}

	public AnimatedImage fireballSprite() throws FileNotFoundException {

		String[] imageArray = new String[5];
		for (int i = 0; i < 5; i++) {
			imageArray[i] = "sprites/FB500-" + i + ".png";
		}

		AnimatedImage fireball = new AnimatedImage(imageArray, 0.125, 60, 60);

		return fireball;

	}

}
