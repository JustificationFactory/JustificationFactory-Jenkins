package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.AString;
import javafx.scene.layout.GridPane;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class CategoryTitle extends ExpParameterLeaf {
    public CategoryTitle(int level, String label) {
        super(level, new AString(label,""));

        // span on [levelMark Title|:|Value]
        GridPane.setColumnSpan(paneTitle, 3);
        paramTitle.getStyleClass().add("category-title");
    }
}
