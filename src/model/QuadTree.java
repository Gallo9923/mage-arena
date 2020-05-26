package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class QuadTree implements Serializable {

	private static final long serialVersionUID = 2262569611549874765L;

	/**
	 * The level of the QuadTree hierarchy Tree
	 */
	private int level;

	/**
	 * northEast QuadTree
	 */
	private QuadTree northEast;

	/**
	 * northWest QuadTree
	 */
	private QuadTree northWest;

	/**
	 * southEast QuadTree
	 */
	private QuadTree southEast;

	/**
	 * southWest QuadTree
	 */
	private QuadTree southWest;

	/**
	 * Entities capacity of the QuadTree
	 */
	private int capacity = 4; // 4

	/**
	 * List of entities in the QuadTree
	 */
	private ArrayList<Entity> QTentities;

	/**
	 * Determines if the QuadTree is divided or not
	 */
	private boolean divided = false;

	/**
	 * Coordinate x of the QuadTree
	 */
	private double x;
	/**
	 * Coordinate y of the QuadTree
	 */
	private double y;

	/**
	 * Width of the QuadTree
	 */
	private double w;

	/**
	 * Height of the QuadTree
	 */
	private double h;

	/**
	 * Creates and instance of a QuadTree
	 * 
	 * @param level
	 * @param x Coordinate x of the QuadTree
	 * @param y Coordinate y of the QuadTree
	 * @param w Width of the QuadTree
	 * @param h Height of the QuadTree
	 */ 
	public QuadTree(int level, double x, double y, double w, double h) {

		this.northEast = null;
		this.northWest = null;
		this.southEast = null;
		this.southWest = null;

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		this.level = level;

		this.QTentities = new ArrayList<Entity>();
	}

	/**
	 * Returns the list of entities of the QuadTree
	 * @return ArrayList<Entity> List of entities of the QuadTree
	 */
	public ArrayList<Entity> getQTEntities() {
		return QTentities;
	}

	
	/**
	 * Subdivides the current QuadTree in other four smaller QuadTrees
	 */
	public void subdivide() {

		this.northEast = new QuadTree(level + 1, x + w / 2, y, w / 2, h / 2);
		this.northWest = new QuadTree(level + 1, x, y, w / 2, h / 2);
		this.southEast = new QuadTree(level + 1, x + w / 2, y + h / 2, w / 2, h / 2);
		this.southWest = new QuadTree(level + 1, x, y + h / 2, w / 2, h / 2);

		for (int i = 0; i < QTentities.size(); i++) {
			Entity entity = QTentities.get(i);

			northEast.insert(entity);
			northWest.insert(entity);
			southEast.insert(entity);
			southWest.insert(entity);
		}

	}

	/**
	 * Inserts an entity to the QuadTree recursively
	 * @param entity Entity to be inserted
	 */
	public void insert(Entity entity) {

		Rectangle2D region = new Rectangle2D(x, y, w, h);

		if (region.intersects(entity.getBoundary())) {

			if (QTentities.size() < capacity) {
				QTentities.add(entity);
				entity.addQuadTree(this);
			} else if (level == 3) {

				divided = true;
				QTentities.add(entity);
				entity.addQuadTree(this);

			} else {

				if (!divided) {
					subdivide();
					divided = true;
				}

				northEast.insert(entity);
				northWest.insert(entity);
				southEast.insert(entity);
				southWest.insert(entity);

			}
		}

	}

	/**
	 * Determines if a entity is contained in a QuadTree
	 * @param entity Entity to be checked
	 * @return True if the entity is contained in the QuadTree
	 */
	public boolean contains(Entity entity) {

		Rectangle2D region = new Rectangle2D(x, y, w, h);

		return region.intersects(entity.getBoundary());
	}

	/**
	 * Renders the QuadTree delimitations or borders
	 * @param gc GraphicsContext
	 */
	public void render(GraphicsContext gc) {

		gc.strokeLine(x, y, x + w, y);
		gc.strokeLine(x, y + h, x + w, y + h);

		gc.strokeLine(x, y, x, y + h);
		gc.strokeLine(x + w, y, x + w, y + h);
	}

	/**
	 * Returns the northEast QuadTree
	 * @return QuadTree
	 */
	public QuadTree getNorthEast() {
		return northEast;
	}

	/**
	 * Returns the northWest QuadTree
	 * @return QuadTree
	 */
	public QuadTree getNorthWest() {
		return northWest;
	}

	/**
	 * Returns the southEast QuadTree
	 * @return QuadTree
	 */
	public QuadTree getSouthEast() {
		return southEast;
	}

	/**
	 * Returns the southWest QuadTree
	 * @return QuadTree
	 */
	public QuadTree getSouthWest() {
		return southWest;
	}

}
