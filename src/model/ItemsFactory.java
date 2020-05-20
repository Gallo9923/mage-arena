package model;

import java.io.FileNotFoundException;

public interface ItemsFactory {
	
	public Item createItem(Player player) throws FileNotFoundException;

	
	
}
