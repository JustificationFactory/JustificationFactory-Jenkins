package fr.axonic.avek.gui.model.structure;

import fr.axonic.avek.model.base.ARangedEnum;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class ExpEffect {
	private final ARangedEnum states;
	private final String name;

	public ExpEffect(ARangedEnum aEnum, String name) {
		this.states = aEnum;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public ARangedEnum getStateClass() {
		return states;
	}

	@Override
	public String toString() {
		return getName();
	}
}
