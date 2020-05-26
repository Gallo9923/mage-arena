package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Update, Serializable, Cloneable{

	private static final long serialVersionUID = 1391224659788340840L;
	
	/**
	 * Sprite Animated image of the entity
	 */
	private AnimatedImage sprite;
	
	/**
	 * The X coordinate of the entity
	 */
	private double posX;
	
	/**
	 * The y coordinate of the entity
	 */
	private double posY;
	
	/**
	 * The width of the entity
	 */
	private double width;
	
	/**
	 * The height of the entity
	 */
	private double height;
	
	/**
	 * The type of movement performed by the entity
	 */
	private Movement movement;
	
	/**
	 * The type of attack performed by the entity
	 */
	private Attack attack;
	
	/**
	 * The offset in the x axis of the entity
	 */
	private double offsetX;
	
	/**
	 * The offset in the y axis of the entity
	 */
	private double offsetY;
	
	private ArrayList<QuadTree> quadTrees;
	
	/**
	 * Creates a new instance of Entity
	 * @param sprite Animated image of the entity
	 * @param posX the X coordinate of the entity
	 * @param posY the y coordinate of the entity
	 * @param width The width of the entity
	 * @param height The height of the entity
	 * @param offsetX The offset in the x axis of the entity
	 * @param offsetY The offset in the y axis of the entity
	 * @param movement The type of movement performed by the entity
	 * @param attack The type of attack performed by the entity
	 */
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
		this.quadTrees = new ArrayList<QuadTree>();
	}

	/**
	 * Adds a new QuadTree in which this entity belongs
	 * @param qt QuadTree
	 */
	public void addQuadTree(QuadTree qt) {
		quadTrees.add(qt);
	}
	
	/**
	 * Removes all the QuadTrees-in which this entity belongs
	 */
	public void resetQuadTree() {
		quadTrees = new ArrayList<QuadTree>(); 
	}
	
	/**
	 * Returns the list of QuadTrees in which this entity belongs
	 * @return ArrayList<QuadTree> The QuadTrees that this entity belongs
	 */
	public ArrayList<QuadTree> getQuadTrees() {
		return quadTrees;
	}
	
	/**
	 * Renders the current entity in the game
	 * @param gc GraphicsContext of the game
	 * @param t the current time of the game
	 */
	@SuppressWarnings("unused")
	public void render(GraphicsContext gc, double t) {
		gc.drawImage(sprite.getFrame(t), posX, posY);
		
		
		if(GameManager.DEBUG_MODE == true) {
			gc.strokeLine(posX + offsetX,posY + offsetY, posX + offsetX + width, posY + offsetY);
			gc.strokeLine(posX + offsetX,posY + offsetY, posX + offsetX, posY + offsetY + height);
			gc.strokeLine(posX + offsetX + width,posY + offsetY, posX + offsetX + width, posY + offsetY + height);
			gc.strokeLine(posX + offsetX,posY + offsetY + height, posX + offsetX + width, posY + offsetY + height);
		}
	
	}

	/**
	 * Returns the hitbox of the entity
	 * @return Rectangle2D hitbox of the entity
	 */
	public Rectangle2D getBoundary() {
		return new Rectangle2D(posX + offsetX, posY + offsetY, width, height);
	}

	/**
	 * Determines if the hitbox of the entity intersects with another
	 * hitbox of other entity
	 * @param entity Entity to be checked
	 * @return boolen True if the hitboxes of the two entities intersects
	 */
	public boolean intersects(Entity entity) {

		return entity.getBoundary().intersects(this.getBoundary());

	}

	/**
	 * Determines if the entity intersects the limits of the window
	 * @return True if the hitbox of the entity intersects any of the edges of the window
	 */
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
	
	/**
	 * Determines if the entity intersects the left edge of the window
	 * @return True if the hitbox of the entity intersects the left edge of the window
	 */
	public boolean intersectsLeftWall() {
		Rectangle2D leftWall = new Rectangle2D(0, 0, 15, 720);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(leftWall);
		
	}

	/**
	 * Determines if the entity intersects the right edge of the window
	 * @return True if the hitbox of the entity intersects the right edge of the window
	 */
	public boolean intersectsRightWall() {
		Rectangle2D rightWall = new Rectangle2D(1280 - 15, 0, 15, 720);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(rightWall);
		
	}
	
	/**
	 * Determines if the entity intersects the top edge of the window
	 * @return True if the hitbox of the entity intersects the top edge of the window
	 */
	public boolean intersectsTopWall() {
		Rectangle2D topWall = new Rectangle2D(0, 0, 1280, 35);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(topWall);
		
	}
	
	/**
	 * Determines if the entity intersects the bottom edge of the window
	 * @return True if the hitbox of the entity intersects the bottom edge of the window
	 */
	public boolean intersectsBottomWall() {
		Rectangle2D bottomWall = new Rectangle2D(0, 720 - 15, 1280, 15);
		Rectangle2D b = this.getBoundary();
		
		return b.intersects(bottomWall);
		
	}
	
	/**
	 * Returns the x coordinate of the entity
	 * @return double x coordinate of the entity
	 */
	public double getPosX() {
		return posX;
	}

	/**
	 * Sets the new x coordinate of the entity
	 * @param posX new coordinate of the entity
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}

	/**
	 * Returns the y coordinate of the entity
	 * @return double y coordinate of the entity
	 */
	public double getPosY() {
		return posY;
	}

	/**
	 * Sets the new y coordinate of the entity
	 * @param posY y coordinate
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	/**
	 * Returns the width of the entity
	 * @return double width of the entity
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the entity
	 * @return double height of the entity
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Updated the x and y coordinate of the entity
	 */
	public void update() {
		this.move();
	}
	
	/**
	 * Performs the attack to another entity
	 * @param entity entity
	 */
	public void attack(Entity entity) {
		attack.attack(entity);
	}
	
	/**
	 * Move the entity
	 */
	public void move() {
		movement.move(this);
	}

	/**
	 * Returns the AnimatedImage of the entity 
	 * @return AnimatedImage sprite of the entity
	 */
	public AnimatedImage getSprite() {
		return sprite;
	}

	/**
	 * Returns the movement of the entity
	 * @return Movement movement
	 */
	public Movement getMovement() {
		return movement;
	}

	/**
	 * Sets a movement to the entity
	 * @param movement movement
 	 */
	public void setMovement(Movement movement) {
		this.movement = movement;
	}
	
	/**
	 * Returns the attack of the entity
	 * @return Attack attack of the entity
	 */
	public Attack getAttack() {
		return attack;
	}

	/**
	 * Returns the x offset of the entity
	 * @return double x offset
	 */
	public double getOffsetX() {
		return offsetX;
	}

	/**
	 * Returns the y offset of the entity
	 * @return double y offset
	 */
	public double getOffsetY() {
		return offsetY;
	}

	/**
	 * Clones the entity
	 * @return Object cloned entity
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
