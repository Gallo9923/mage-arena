package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class QuadTree implements Serializable {

	private static final long serialVersionUID = 2262569611549874765L;

	private int level;

	private QuadTree northEast;
	private QuadTree northWest;
	private QuadTree southEast;
	private QuadTree southWest;

	private int capacity = 4; // 4
	private ArrayList<Entity> QTentities;
	private boolean divided = false;

	private double x;
	private double y;
	private double w;
	private double h;

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

	public ArrayList<Entity> getQTEntities() {
		return QTentities;
	}

	public void subdivide() {

		this.northEast = new QuadTree(level+1, x + w / 2, y, w / 2, h / 2);
		this.northWest = new QuadTree(level+1, x, y, w / 2, h / 2);
		this.southEast = new QuadTree(level+1, x + w / 2, y + h / 2, w / 2, h / 2);
		this.southWest = new QuadTree(level+1, x, y + h / 2, w / 2, h / 2);

		for (int i = 0; i < QTentities.size(); i++) {
			Entity entity = QTentities.get(i);

			northEast.insert(entity);
			northWest.insert(entity);
			southEast.insert(entity);
			southWest.insert(entity);
		}

	}

	public void insert(Entity entity) {

		System.out.println(level);
		
		Rectangle2D region = new Rectangle2D(x, y, w, h);

		if (region.intersects(entity.getBoundary())) {

			if (QTentities.size() < capacity) {
				QTentities.add(entity);
				entity.addQuadTree(this);
			}else if(level ==3) {
				
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

	public boolean contains(Entity entity) {

		Rectangle2D region = new Rectangle2D(x, y, w, h);

		return region.intersects(entity.getBoundary());
	}

	public void render(GraphicsContext gc) {

		gc.strokeLine(x, y, x + w, y);
		gc.strokeLine(x, y + h, x + w, y + h);

		gc.strokeLine(x, y, x, y + h);
		gc.strokeLine(x + w, y, x + w, y + h);
	}

	public QuadTree getNorthEast() {
		return northEast;
	}

	public QuadTree getNorthWest() {
		return northWest;
	}

	public QuadTree getSouthEast() {
		return southEast;
	}

	public QuadTree getSouthWest() {
		return southWest;
	}

}
