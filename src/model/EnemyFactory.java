package model;

import java.io.FileNotFoundException;

public interface EnemyFactory {
	
	public Mob createMob() throws FileNotFoundException;
	
}
