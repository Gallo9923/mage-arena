package model;

import java.io.Serializable;

public class ConstantLinearMovement implements Movement, Serializable  {


	private static final long serialVersionUID = -3256537714278160876L;
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	private double MX = 1.25;
	private double MY = 1.25;
	
	public ConstantLinearMovement(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	
	@Override
	public void move(Entity entity) {
		
		double x = entity.getPosX();
		double y = entity.getPosY();
		
		double DX = x2 - x1;
		double DY = y2 - y1;
		
		if(DX > 0) {
			x += MX;
		}else if(DX < 0) {
			x -= MX;
		}
		
		if(DY > 0) {
			y += MY;
		}else if(DY < 0) {
			y -= MY;
		}
		
		entity.setPosX(x);
		entity.setPosY(y);
	
	}


	public void setX2(double x2) {
		this.x2 = x2;
	}


	public void setY2(double y2) {
		this.y2 = y2;
	}
	
	
	
	
	
}
