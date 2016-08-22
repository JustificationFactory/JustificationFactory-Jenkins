package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;

/**
 * Created by Nathaël N on 21/07/16.
 */
class ParametersCategory extends ParametersGroup {
    ParametersCategory(int level, AList list) {
        super(level, new ExpParameterLeaf(level, new AString(list.getLabel(), "")));

        // Generating GUI component
        getCategoryTitle().setExpandable(this::onClickExpand);

        super.setAList(list);
    }

    private void onClickExpand(boolean isExpanded) {
        getChildren()
                .stream()
                .filter(n -> !getCategoryTitle().getParameterLine().equals(n))
                .forEach(n -> {
                    n.setVisible(isExpanded);
                    n.setManaged(isExpanded);
                });
    }
}
