package fr.axonic.avek.gui.model.results;

import fr.axonic.avek.model.base.AEnum;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class ExpEffect {
	private final AEnum states;
	private final String name;

	public ExpEffect(AEnum aEnum, String name) {
		this.states = aEnum;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public AEnum getStateClass() {
		return states;
	}

	@Override
	public String toString() {
		return getName();
	}
}
