package fr.axonic.avek.gui.model.expEffects;

import fr.axonic.avek.gui.model.IExpEffect;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaël N on 05/07/16.
 */
public class EnumExpEffect implements IExpEffect {
	private final String name;
	private TestEnum value;

	public EnumExpEffect(String effectName) {
		this.name = effectName;
		value = TestEnum.BLUE;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onClick() {
		List<TestEnum> values = new ArrayList<>();
		for(TestEnum t : TestEnum.values())
			values.add(t);
		values.add(TestEnum.values()[0]); // Avoid loop problem

		value = values.get(values.indexOf(value) + 1); // get next value
	}

	@Override
	public Color getColor() {
		return value.getColor();
	}

	@Override
	public String toString() {
		return name;
	}
}
