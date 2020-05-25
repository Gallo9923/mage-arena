package model;

import java.io.Serializable;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Update, Serializable, Cloneable{

	private static final long serialVersionUID = 1391224659788340840L;
	private AnimatedImage sprite;
	private double posX;
	private double posY;
	private double width;
	private double height;
	private Movement movement;
	private Attack attack;

	private double offsetX;
	private double offsetY;
	
	public Entity(AnimatedImage sprite, double posX, double posY, double width, double height, double offsetX, double offsetY, Movement movement, Attack attack) {
		this.sprite = sprite;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.movement = movement;
		this.attack = attack;
	}

	public void render(GraphicsContext gc, double t) {
		gc.drawImage(sprite.getFrame(t), posX, posY);
		
		//Debuggin Colisions
		/*
		gc.strokeLine(posX + offsetX,posY + offsetY, posX + offsetX + width, posY + offsetY);
		gc.strokeLine(posX + offsetX,posY + offsetY, posX + offsetX, posY + offsetY + height);
		gc.strokeLine(posX + offsetX + width,posY + offsetY, posX + offsetX + width, posY + offsetY + height);
		gc.strokeLine(posX + offsetX,posY + offsetY + height, posX + offsetX + width, posY + offsetY + height);
		*/
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(posX + offsetX, posY + offsetY, width, height);
	}

	public boolean intersects(Entity entity) {

		return entity.getBoundary().intersects(this.getBoundary());

	}

	public boolean intersectsWall() {

		Rectangle2D leftWall = new Rectangle2D(0, 0, 0.1, 720);
		Rectangle2D rightWall = new Rectangle2D(1280, 0, 0.1, 720);
		Rectangle2D topWall = new Rectangle2D(0, 0, 1280, 35);
		Rectangle2D bottomWall = new Rectangle2D(0, 720, 1280, 0.1);
		
		boolean intersect = false;
		Rectangle2D b = this.getBoundary();

		if (b.intersects(leftWall) || b.intersects(rightWall) || b.intersects(topWall) || b.intersects(bottomWall)) {
			intersect = true;
		}

		return intersect;
		
	}
	
	public boolean intersectsLeftWall() {
		Rectangle2D leftWall = new Rectangle2D(0, 0, 15, 720);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(leftWall);
		
	}

	public boolean intersectsRightWall() {
		Rectangle2D rightWall = new Rectangle2D(1280 - 15, 0, 15, 720);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(rightWall);
		
	}
	
	public boolean intersectsTopWall() {
		Rectangle2D topWall = new Rectangle2D(0, 0, 1280, 35);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(topWall);
		
	}
	
	public boolean intersectsBottomWall() {
		Rectangle2D bottomWall = new Rectangle2D(0, 720 - 15, 1280, 15);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(bottomWall);
		
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
	
	public void attack(Entity entity) {
		attack.attack(entity);
	}
	
	public void move() {
		movement.move(this);
	}

	public AnimatedImage getSprite() {
		return sprite;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	
	public Attack getAttack() {
		return attack;
	}

	public double getOffsetX() {
		return offsetX;
	}

	public double getOffsetY() {
		return offsetY;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
