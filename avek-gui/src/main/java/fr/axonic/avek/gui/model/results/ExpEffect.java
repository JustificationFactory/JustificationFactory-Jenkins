package fr.axonic.avek.gui.model.results;

import fr.axonic.avek.gui.model.json.BEnum;
import fr.axonic.avek.model.base.ARangedEnum;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class ExpEffect {
	private final BEnum states;
	private final String name;

	public ExpEffect(BEnum aEnum, String name) {
		this.states = aEnum;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public BEnum getStateClass() {
		return states;
	}

	@Override
	public String toString() {
		return getName();
	}
}
