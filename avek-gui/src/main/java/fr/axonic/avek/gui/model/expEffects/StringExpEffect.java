package fr.axonic.avek.gui.model.expEffects;

import fr.axonic.avek.gui.model.IExpEffect;
import javafx.scene.paint.Color;

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
	public void onClick() {
	}

	@Override
	public Color getColor() {
		return Color.LIGHTBLUE;
	}

	@Override
	public String toString() {
		return name;
	}
}
