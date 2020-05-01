package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import model.AnimatedImage;

public class GameManager {
	
	private Player match;
	
	public GameManager() {
		
	}
	
	public void newMatch() throws FileNotFoundException {
		
		match = new Player(mageSprite(), 500, 500, new PlayerMovement());
		
	}
	
	public void update() {
		match.update();
	}
	
	public AnimatedImage mageSprite() throws FileNotFoundException {
		
		AnimatedImage mage = new AnimatedImage();

		Image[] imageArray = new Image[2];
		for (int i = 0; i < 2; i++) {
			imageArray[i] = new Image(new FileInputStream("sprites/Mage" + i + ".png"), 100, 100, true, true);
			;
		}
		mage.frames = imageArray;
		mage.duration = 0.200;
		
		return mage;
		
	}
	
	public void keyPressedEvent(KeyEvent event) {
		match.keyPressedEvent(event);
		
	}
	
	public void keyReleasedEvent(KeyEvent event) {
		match.keyReleasedEvent(event);
	}
	
	public Player getMatch() {
		return match;
	}

	
}
