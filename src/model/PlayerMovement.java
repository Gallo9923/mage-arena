package model;

import java.util.HashSet;

import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyEvent;

public class PlayerMovement implements Movement{
	
	private double xChange = 2.5;
	private double yChange = 2.5;
	
	public void move(Entity entity) {
		
		double x = entity.getPosX();
		double y = entity.getPosY();
		HashSet currentlyActiveKeys = ((Player)entity).getCurrentlyActiveKeys();
		
		if (currentlyActiveKeys.contains("A") && x-xChange+40 > 0) {
			x -= xChange;
			entity.setPosX(x);
		}
		if (currentlyActiveKeys.contains("D") && x+xChange+entity.getWidth() <1280) {
			x += xChange;
			entity.setPosX(x);
		}
		if (currentlyActiveKeys.contains("W") && y-yChange+20 > 0) {
			y -= yChange;
			entity.setPosY(y);
		}
		if (currentlyActiveKeys.contains("S") && y+yChange+entity.getHeight()+10 < 720) {
			y += yChange;
			entity.setPosY(y);
		}
		
		System.out.println("X: " + x + " Y: " + y);
		
	}
	

	
}
