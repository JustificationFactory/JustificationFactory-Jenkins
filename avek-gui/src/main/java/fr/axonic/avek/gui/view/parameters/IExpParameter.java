package fr.axonic.avek.gui.view.parameters;

import javafx.scene.control.Control;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 04/07/16.
 */
public interface IExpParameter {
	Set<Control> getElements();
	String getName();
}
