package model;

import java.io.Serializable;
import java.util.HashSet;

public class PlayerMovement implements Movement, Serializable{
	
	private static final long serialVersionUID = -1742767686780170839L;
	private double xChange = 2.5;
	private double yChange = 2.5;
	
	public void move(Entity entity) {
		
		double x = entity.getPosX();
		double y = entity.getPosY();
		HashSet<String> currentlyActiveKeys = ((Player)entity).getCurrentlyActiveKeys();
		
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
		
		
		/*
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
		*/
		//System.out.println("X: " + x + " Y: " + y);
		
	}
	

	
}
