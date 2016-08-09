package fr.axonic.avek.gui.components.parameters;

import fr.axonic.base.engine.AEntity;
import javafx.scene.Node;

import java.util.Set;

/**
 * Created by Nathaël N on 13/07/16.
 */
public interface IExpParameter {
    Set<Node> getElements();

    String getName();

    AEntity getAsAEntity();
}
