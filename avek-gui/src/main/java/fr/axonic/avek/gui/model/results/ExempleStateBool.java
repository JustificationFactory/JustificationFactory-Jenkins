package fr.axonic.avek.gui.model.results;

import javafx.scene.paint.Color;

/**
 * Created by Nathaël N on 07/07/16.
 */
public enum ExempleStateBool implements IColorState {
	TRUE, FALSE;

	@Override
	public Color getColor() {
		return this == TRUE?Color.GREEN:Color.RED;
	}
}
