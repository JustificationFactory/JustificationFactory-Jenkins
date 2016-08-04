package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.base.AString;
import javafx.scene.Node;

/**
 * Created by Nathaël N on 21/07/16.
 */
class ParametersCategory extends ParametersGroup {
    private final ExpParameterLeaf categoryTitle;

    ParametersCategory(int level, String label) {
        super(level, label);

        // Generating GUI component
        setColumnSpan(this, 3);

        categoryTitle = new ExpParameterLeaf(level, new AString(label, ""));
        categoryTitle.getLabelTitle().getStyleClass().add("category-title");
        addExpParameter(categoryTitle);

        categoryTitle.setExpandable(this::onClickExpand);
    }

    private void onClickExpand(boolean isExpanded) {
        for (Node n : getChildren()) {
            if (categoryTitle.getElements().contains(n)) {
                continue;
            }

            n.setVisible(isExpanded);
            n.setManaged(isExpanded);
        }
    }
}
