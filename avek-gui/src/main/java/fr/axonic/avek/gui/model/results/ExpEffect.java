package fr.axonic.avek.gui.model.results;

import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ARangedEnum;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class ExpEffect {
	private final AState states;
	private final String name;

	public ExpEffect(AState aEnum, String name) {
		this.states = aEnum;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public AState getStateClass() {
		return states;
	}

	@Override
	public String toString() {
		return getName();
	}
}
