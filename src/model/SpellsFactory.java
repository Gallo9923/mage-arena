package model;

import java.io.FileNotFoundException;

public interface SpellsFactory {

	public Spell createSpell(Entity entity, double clickX, double clickY) throws FileNotFoundException;
}
