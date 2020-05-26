package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.image.Image;

public class AnimatedImage implements Serializable {

	private static final long serialVersionUID = 2507031481538731540L;

	/**
	 * Path of each image for the animation
	 */
	private String[] frames;
	/**
	 * The duration of each frame in ms
	 */
	private double duration;
	/**
	 * The width of the image to be loaded
	 */
	private double width;
	/**
	 * The height of the image to be loaded
	 */
	private double height;

	/**
	 * Constructs an AnimatedImage in charge of displaying the movement animations
	 * of each entity
	 * 
	 * @param frames Path of each image for the animation
	 * @param duration The duration of each frame in ms
	 * @param width    The width of the image to be loaded
	 * @param height   The height of the image to be loaded
	 */
	public AnimatedImage(String[] frames, double duration, double width, double height) {
		this.frames = frames;
		this.duration = duration;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the current image of the animations given a certain duration, which
	 * cycles between all frames
	 * 
	 * @param time Time of the match
	 * @return Image JavaFx Image of the current animation
	 */
	public Image getFrame(double time) {
		int index = (int) ((time % (frames.length * duration)) / duration);

		Image image = null;

		try {
			image = new Image(new FileInputStream(frames[index]), width, height, false, false);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return image;
	}

}