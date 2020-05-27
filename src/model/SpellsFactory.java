package model;

import java.io.FileNotFoundException;

public interface SpellsFactory {

	/**
	 * Creates an instance of a Spell
	 * 
	 * @param entity Player
	 * @param clickX x coordinate of the click event
	 * @param clickY y coordinate of the click event
	 * @return Spell spell
	 * @throws FileNotFoundException file not found
	 */
	public Spell createSpell(Entity entity, double clickX, double clickY) throws FileNotFoundException;
}
