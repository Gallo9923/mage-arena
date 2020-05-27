package model;

import java.io.FileNotFoundException;

public interface ItemsFactory {

	/**
	 * Creates instances of items
	 * 
	 * @param player Player of the match
	 * @return Item instance of an item
	 * @throws FileNotFoundException File not found
	 */
	public Item createItem(Player player) throws FileNotFoundException;

}
