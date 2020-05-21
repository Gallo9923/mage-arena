package model;

import java.io.FileNotFoundException;

public interface MobFactory {
	
	public Mob createMob(Entity entity) throws FileNotFoundException;
	
}
