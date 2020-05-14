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
		/*
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
		*/
		if (currentlyActiveKeys.contains("A") && entity.intersectsLeftWall() == false) {
			x -= xChange;
			entity.setPosX(x);
		}
		if (currentlyActiveKeys.contains("D") && entity.intersectsRightWall() == false) {
			x += xChange;
			entity.setPosX(x);
		}
		if (currentlyActiveKeys.contains("W") && entity.intersectsTopWall() == false) {
			y -= yChange;
			entity.setPosY(y);
		}
		if (currentlyActiveKeys.contains("S") && entity.intersectsBottomWall() == false) {
			y += yChange;
			entity.setPosY(y);
		}
		
		if(entity.intersectsLeftWall()) {
			System.out.println("LEFT");
		}
		
		if(entity.intersectsRightWall()) {
			System.out.println("Right");
		}
		
		if(entity.intersectsBottomWall()) {
			System.out.println("Bot");
		}
		
		if(entity.intersectsTopWall()) {
			System.out.println("TOP");
		}
		
		//System.out.println("X: " + x + " Y: " + y);
		
	}
	

	
}
