package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import javafx.scene.Node;

/**
 * Created by Nathaël N on 21/07/16.
 */
class ParametersCategory extends ParametersGroup {
    ParametersCategory(int level, AList<AEntity> list) {
        super(level, new ExpParameterLeaf(level, new AString(list.getLabel(), "")));

        // Generating GUI component
        setColumnSpan(this, 3);

        getCategoryTitle().setExpandable(this::onClickExpand);

        super.setAList(list);
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
