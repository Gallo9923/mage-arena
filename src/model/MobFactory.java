package model;

import java.io.FileNotFoundException;

public interface MobFactory {
	
	/**
	 * Creates a mob instance
	 * @param entity Player
	 * @return Mob instance of a mob
	 * @throws FileNotFoundException
	 */
	public Mob createMob(Entity entity) throws FileNotFoundException;
	
}
