package fr.axonic.avek.gui.model;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class StringExpEffect implements IExpEffect {
	private final String name;

	public StringExpEffect(String effectName) {
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
