package model;

import java.util.ArrayList;

import javafx.geometry.Rectangle2D;

public class QuadTree {
	
	private QuadTree parent;
	
	private QuadTree northEast;
	private QuadTree northWest;
	private QuadTree southEast;
	private QuadTree southWest;
	
	private int capacity = 4;
	private ArrayList<Entity> QTentities;
	private boolean divided = false;
	
	private double x;
	private double y;
	private double w; 
	private double h;
	
	private Rectangle2D region;
	
	public QuadTree(double x, double y, double w, double h) {
		
		this.parent = null;
		this.northEast = null;
		this.northWest = null;
		this.southEast = null;
		this.southWest = null;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		this.region = new Rectangle2D(x, y , w, h);
		
		this.QTentities = new ArrayList<Entity>();
	}

	
	public void subdivide() {
		
		this.northEast = new QuadTree(x + w/2, y, w/2, h/2);
		this.northWest = new QuadTree(x, y, w/2, h/2);
		this.southEast = new QuadTree(x + w/2, y+h/2, w/2, h/2);
		this.southWest = new QuadTree(x, y + h/2, w/2, h/2);
		
	}
	
	public void insert(Entity entity) {
		
		if(region.intersects(entity.getBoundary())) {
			
			if(QTentities.size() <= capacity) {
				QTentities.add(entity);
			}else {
				
				if(!divided) {
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
		return this.region.intersects(entity.getBoundary());
	}
}
