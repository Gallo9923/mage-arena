package threads;

import java.util.ArrayList;

import model.Entity;
import model.Mob;
import model.Player;
import model.QuadTree;
import model.Spell;

public class AttackSpellThread extends Thread {

	/**
	 * The current match
	 */
	private Player player;

	/**
	 * List of spells
	 */
	private ArrayList<Spell> spells;

	/**
	 * Starting index to run inclusive
	 */
	private int i;

	/**
	 * End index to run exclusive
	 */
	private int j;

	/**
	 * Creates an instance of AttackSpellThread
	 * 
	 * @param player current match
	 * @param spells List of spells
	 * @param i      Starting index to run inclusive
	 * @param j      End index to run exclusive
	 */
	public AttackSpellThread(Player player, ArrayList<Spell> spells, int i, int j) {
		this.player = player;
		this.spells = spells;
		this.i = i;
		this.j = j;
	}

	/**
	 * Performs all the spells attack
	 */
	public void run() {

		for (int i = this.i; i < this.j && i < spells.size(); i++) {

			Spell spell = spells.get(i);
			ArrayList<QuadTree> currQTs = spell.getQuadTrees();

			for (int j = 0; j < currQTs.size(); j++) {

				QuadTree quadTree = currQTs.get(j);
				ArrayList<Entity> QTentities = quadTree.getQTEntities();

				for (int z = 0; z < QTentities.size(); z++) {

					Entity auxEntity = QTentities.get(z);

					if (spell.equals(auxEntity) == false && auxEntity instanceof Mob && spell.intersects(auxEntity)) {

						player.gainScore(spell);
						spell.attack(auxEntity);

						player.addToRemove(spell);

					}

				}

			}

		}

	}

}
