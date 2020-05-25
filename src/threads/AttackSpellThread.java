package threads;

import java.util.ArrayList;

import model.Entity;
import model.Mob;
import model.Player;

public class AttackSpellThread extends Thread {

	private Player player;
	private ArrayList<Entity> entities;
	private Entity curr;
	private int i;
	private int j;

	public AttackSpellThread(Player player, ArrayList<Entity> entities, Entity curr, int i, int j) {

		this.player = player;
		this.entities = entities;
		this.curr = curr;
		this.i = i;
		this.j = j;
	}

	public void run() {

		for (int i = this.i; i < this.j && i < entities.size(); i++) {

			Entity aux = entities.get(i);

			if (i != j && aux instanceof Mob && curr.intersects(aux)) {

				player.gainScore(curr);
				curr.attack(aux);
				// entities.remove(curr);
				// i--;
				player.addToRemove(curr);

			}

		}
	}
}
