package fr.axonic.avek.gui.model.expEffects;

import fr.axonic.avek.gui.model.IExpEffect;
import javafx.scene.paint.Color;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class BooleanExpEffect implements IExpEffect {
	private final String name;
	private boolean status;

	public BooleanExpEffect(String effectName) {
		this.name = effectName;
		this.status = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onClick() {
		status = !status;
	}

	@Override
	public Color getColor() {
		return status ? Color.LIGHTGREEN : Color.INDIANRED;
	}

	@Override
	public String toString() {
		return name;
	}
}
