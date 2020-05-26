package threads;

import java.util.ArrayList;

import model.Entity;
import model.Item;
import model.Mob;
import model.Player;
import model.QuadTree;

public class AttackPlayerThread extends Thread{

	private Player player;
	private ArrayList<QuadTree> quadTrees;
	private int i;
	private int j;
	
	public AttackPlayerThread(Player player, ArrayList<QuadTree> quadTrees, int i, int j) {
		
		this.player = player;
		this.quadTrees = quadTrees;
		this.i = i;
		this.j = j;
	}
	
	
	public void run() {
		
		for (int i = this.i; i< this.j && i < quadTrees.size(); i++) {

			QuadTree quadTree = quadTrees.get(i);
			ArrayList<Entity> QTentities = quadTree.getQTEntities();

			for (int j = 0; j < QTentities.size(); j++) {

				Entity aux = QTentities.get(j);

				if (player.equals(aux) == false && aux instanceof Mob && player.intersects(aux)) {
					aux.attack(player);

				} else if (player.equals(aux) == false && aux instanceof Item && player.intersects(aux)) {
					
					aux.attack(player);
					player.addToRemove(aux);
						
				}
			}
		}
		
	}
	
}
