package fr.axonic.avek.gui.model;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class StringEffect implements IEffect {
	private final String name;

	public StringEffect(String effectName) {
		this.name = effectName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
