package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.leaves.SimpleParameter;
import fr.axonic.base.AString;
import javafx.scene.Node;

/**
 * Created by Nathaël N on 21/07/16.
 */
class GeneralizedCategory extends GeneralizedGroup {
    private final SimpleParameter categoryTitle;

    GeneralizedCategory(int level, String label) {
        super(level, label);

        categoryTitle = new SimpleParameter(level, new AString(label, ""));
        categoryTitle.getLabelTitle().getStyleClass().add("category-title");

        // Generating GUI component
        setColumnSpan(this, 5);
        addExpParameter(categoryTitle);

        categoryTitle.setExpandable(this::onClickExpand);
        categoryTitle.setOnClickMarkedUtil(this::onClickExpand);
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
