package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Update{
	
	private AnimatedImage sprite;
	private double posX;
	private double posY;
	private double width;
	private double height;
	private Movement movement;
	
	public Entity(AnimatedImage sprite, double posX, double posY, double width, double height, Movement movement) {
		this.sprite = sprite;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.movement = movement;
	}
	
	
	public void render(GraphicsContext gc, double t) {
		gc.drawImage(sprite.getFrame(t), posX, posY);
	}
	
	public Rectangle2D getBoundary() {
		return new Rectangle2D(posX, posY, width, height);
	}
	
	public boolean intersects(Entity entity) {
		
		return entity.getBoundary().intersects(this.getBoundary());
		
	}
	
	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}


	public double getWidth() {
		return width;
	}


	public double getHeight() {
		return height;
	}
	
	public void update() {
		this.move();
	}
	
	public void move() {
		movement.move(this);
	}
	
}
