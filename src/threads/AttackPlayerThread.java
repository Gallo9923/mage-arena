package threads;

import java.util.ArrayList;

import model.Entity;
import model.Item;
import model.Mob;
import model.Player;
import model.QuadTree;

public class AttackPlayerThread extends Thread {

	/**
	 * The current match
	 */
	private Player player;

	/**
	 * List of QuadTrees
	 */
	private ArrayList<QuadTree> quadTrees;

	/**
	 * Starting index to run inclusive
	 */
	private int i;

	/**
	 * End index to run exclusive
	 */
	private int j;

	/**
	 * Creates and instance of AttackPlayerThread
	 * 
	 * @param player    the current match
	 * @param quadTrees List of quadTrees
	 * @param i         Starting index to run inclusive
	 * @param j         End index to run exclusive
	 */
	public AttackPlayerThread(Player player, ArrayList<QuadTree> quadTrees, int i, int j) {

		this.player = player;
		this.quadTrees = quadTrees;
		this.i = i;
		this.j = j;
	}

	/**
	 * Performs all the attacks of the player
	 */
	public void run() {

		for (int i = this.i; i < this.j && i < quadTrees.size(); i++) {

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
