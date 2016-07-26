package fr.axonic.avek.gui.components.parameters.list;

import javafx.scene.Node;

import java.util.Set;

/**
 * Created by Nathaël N on 13/07/16.
 */
public interface IExpParameter {
	Set<Node> getElements();

	String getName();
}
