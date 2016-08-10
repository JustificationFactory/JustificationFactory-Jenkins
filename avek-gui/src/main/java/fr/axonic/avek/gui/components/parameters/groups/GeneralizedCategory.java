package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.leaves.SimpleParameter;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import javafx.scene.Node;

/**
 * Created by Nathaël N on 21/07/16.
 */
class GeneralizedCategory extends GeneralizedGroup {

    GeneralizedCategory(int level, AList<AEntity> list) {
        super(level, new SimpleParameter(level, new AString(list.getLabel(), "")));

        // Generating GUI component
        setColumnSpan(this, 5);

        getCategoryTitle().setExpandable(this::onClickExpand);
        ((SimpleParameter)getCategoryTitle()).setOnClickMarkedUtil((b) -> getCategoryTitle().setExpanded((boolean)b));

        setAList(list);
    }

    private void onClickExpand(boolean isExpanded) {
        for (Node n : getChildren()) {
            if (getCategoryTitle().getElements().contains(n)) {
                continue;
            }

            n.setVisible(isExpanded);
            n.setManaged(isExpanded);
        }
    }
}
