package fr.axonic.avek.gui.model.results;

import javafx.scene.paint.Color;

/**
 * Created by Nathaël N on 07/07/16.
 */
public enum ExempleState implements IColorState {
	VERRY_LOW(Color.RED),
	LOW(Color.ORANGE),
	MEDIUM(Color.YELLOW),
	HIGH(Color.BROWN),
	VERY_HIGH(Color.GREEN);

	private Color color;
	ExempleState(Color c) {
		this.color=c;
	}

	@Override
	public Color getColor() {
		return color;
	}
}
