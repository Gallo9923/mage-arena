package model;

import java.util.Random;

public class SlimeMovement implements Movement{

	public static final int TURN_SPEED = 50;
	public static final double SPEED = 1;
	
	private int count = 0;
	boolean xBool = true;
	boolean yBool = true;
	
	@Override
	public void move(Entity entity) {
		
		Random r = new Random();
		double x = entity.getPosX();
		double y = entity.getPosY();
		
		if(count == TURN_SPEED) {
			count = 0;
			
		}
		if (count == 0) {
			xBool = r.nextBoolean();
			yBool = r.nextBoolean();
			System.out.println("\n \n \n \n");
			System.out.println(xBool + " " + yBool );
		}
		
		System.out.println(count);
		
		if(xBool == true) {
			entity.setPosX(x+SPEED);
		}else {
			entity.setPosX(x-SPEED);
		}
		
		if(yBool == true) {
			entity.setPosY(y+SPEED);
		}else {
			entity.setPosY(y-SPEED);
		}
		
		count++;
		
	}
	
	
}
