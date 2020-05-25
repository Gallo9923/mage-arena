package threads;

import java.util.ArrayList;

import model.Entity;
import model.Item;
import model.Mob;
import model.Player;
import model.Spell;

public class AttackThread extends Thread {

	private Player player;
	private ArrayList<Entity> entities;
	private int i;
	private int j;

	public AttackThread(Player player, ArrayList<Entity> entities, int i, int j) {
		this.player = player;
		this.entities = entities;
		this.i = i;
		this.j = j;

	}

	public void run() {

		for (int i = this.i; i < this.j && i < entities.size(); i++) {
			
			Entity curr = entities.get(i);

			if (curr instanceof Mob && curr.intersects(player)) {
				curr.attack(player);

			} else if (curr instanceof Spell) {

				for (int j = 0; j < entities.size(); j++) {

					Entity aux = entities.get(j);

					if (i != j && aux instanceof Mob && curr.intersects(aux)) {

						player.gainScore(curr);
						curr.attack(aux);
						//entities.remove(curr);
						//i--;
						player.addToRemove(curr);
						
					}
				}
			} else if (curr instanceof Item && curr.intersects(player)) {
				curr.attack(player);
				//entities.remove(curr);
				//i--;
				player.addToRemove(curr);
			}
		}

	}
	
	public void startAttackSpellThread() {
		
		
		
	}
	
	
}
