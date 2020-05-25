package threads;

import java.util.ArrayList;

import model.Entity;
import model.Player;
import model.RedSlime;


public class UpdateThread extends Thread {

	private Player player;
	private ArrayList<Entity> entities;
	private int i;
	private int j;
	
	public UpdateThread(Player player, ArrayList<Entity> entities, int i, int j) {
		this.player = player;
		this.entities = entities;
		this.i = i;
		this.j = j;
	}

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
