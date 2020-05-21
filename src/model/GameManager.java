package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.AnimatedImage;

public class GameManager {

	private final double MAGE_WIDTH = 150;
	private final double MAGE_HEIGHT = 150;

	private Player match;

	public GameManager() {
		this.match = null;
	}

	public void newMatch() throws FileNotFoundException {

		match = new Player(mageSprite(), 500, 500, new PlayerMovement(), new FireballAttack());

	}

	public void updateEntities() throws FileNotFoundException {
		match.updateEntities();
	}

	public void renderEntities(GraphicsContext gc, double t) {
		match.renderEntities(gc, t);
	}

	public AnimatedImage mageSprite() throws FileNotFoundException {

		AnimatedImage mage = new AnimatedImage();

		Image[] imageArray = new Image[2];
		for (int i = 0; i < 2; i++) {
			imageArray[i] = new Image(new FileInputStream("sprites/OrangeMage" + i + ".png"), MAGE_WIDTH, MAGE_HEIGHT,
					false, false);
			;
		}
		mage.frames = imageArray;
		mage.duration = 0.200;

		return mage;

	}
	
	public void unPauseGame() {
		match.unPause();
	}
	
	public void mouseClickEvent(MouseEvent event) throws FileNotFoundException {
		match.mouseClickEvent(event);
	}
	
	public double getScore() {
		return match.getScore();
	}
	
	//To Delete
	public void keyPressedEvent(KeyEvent event) {
		match.keyPressedEvent(event);

	}

	//To Delete
	public void keyReleasedEvent(KeyEvent event) {
		match.keyReleasedEvent(event);
	}

	//Is Needed?
	public Player getMatch() {
		return match;
	}
	
	public boolean isWon() {
		return match.isWon();
	}

	public boolean isPaused() {
		return match.isPaused();
	}

	public boolean isLose() {
		return match.isLose();
	}

}
