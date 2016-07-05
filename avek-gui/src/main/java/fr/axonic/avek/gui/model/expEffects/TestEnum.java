package fr.axonic.avek.gui.model.expEffects;

import javafx.scene.paint.Color;


/**
 * Created by Nathaël N on 05/07/16.
 */
public enum TestEnum {
	BLUE(Color.BLUE), GREEN(Color.GREEN), RED(Color.RED), YELLOW(Color.YELLOW),
	WHITE(Color.WHITE), BLACK(Color.BLACK);

	private final Color c;

	TestEnum(Color c) {
		this.c = c;
	}

	public Color getColor() {
		return c;
	}
}
