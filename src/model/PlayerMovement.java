package model;

import java.util.HashSet;

import javafx.scene.input.KeyEvent;

public class PlayerMovement implements Movement{
	
	
	public void move(Entity entity) {
		
		
		
		double x = entity.getPosX();
		double y = entity.getPosY();
		HashSet currentlyActiveKeys = ((Player)entity).getCurrentlyActiveKeys();
		
		if(currentlyActiveKeys.contains("A")) {
			x -= 2.5;
			entity.setPosX(x);

		}
		if(currentlyActiveKeys.contains("D")) {
			x += 2.5;
			entity.setPosX(x);
		}
		if(currentlyActiveKeys.contains("W")) {
			y -= 2.5;
			entity.setPosY(y);
		}
		if(currentlyActiveKeys.contains("S")) {
			y += 2.5;
			entity.setPosY(y);
		}
	}
	
}
