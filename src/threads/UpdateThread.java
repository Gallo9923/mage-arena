package threads;

import java.util.ArrayList;

import model.Entity;
import model.Player;
import model.RedSlime;

public class UpdateThread extends Thread {

	/**
	 * The current match
	 */
	private Player player;

	/**
	 * List of entities
	 */
	private ArrayList<Entity> entities;

	/**
	 * Starting index to run inclusive
	 */
	private int i;

	/**
	 * End index to run exclusive
	 */
	private int j;

	/**
	 * Creates and instance of UpdateThread
	 * 
	 * @param player   The current match
	 * @param entities List of entities
	 * @param i        Starting index to run inclusive
	 * @param j        End index to run exclusive
	 */
	public UpdateThread(Player player, ArrayList<Entity> entities, int i, int j) {
		this.player = player;
		this.entities = entities;
		this.i = i;
		this.j = j;
	}

	/**
	 * Performs the update of all entities
	 */
	public void run() {

		for (int i = this.i; i < this.j && i < entities.size(); i++) {

			Entity entity = entities.get(i);

			if (entity instanceof RedSlime) {
				RedSlime redSlime = (RedSlime) entity;
				redSlime.setDestX(player.getPosX() + player.getWidth() / 2);
				redSlime.setDestY(player.getPosY() + player.getHeight() / 2);
				redSlime.update();

			} else {
				entities.get(i).update();
			}

		}

	}

}
