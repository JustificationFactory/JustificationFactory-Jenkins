package fr.axonic.avek.gui.model.structure;

import fr.axonic.avek.model.base.ARangedEnum;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class ExperimentationResult {
	private final ARangedEnum states;
	private final String name;

	public ExperimentationResult(String name, ARangedEnum states) {
		this.states = states;
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
