package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class SlimeFactory implements EnemyFactory{

	@Override
	public Mob createMob() throws FileNotFoundException {
		
		 return new Slime(slimeSprite(), 500, 500, 60 ,50, 10, 15, 65, 20, new SlimeMovement(), new SlimeAttack());
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
	

}
