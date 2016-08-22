package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.leaves.SimpleParameter;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;

/**
 * Created by Nathaël N on 21/07/16.
 */
class GeneralizedCategory extends GeneralizedGroup {

    GeneralizedCategory(int level, AList list) {
        super(level, new SimpleParameter(level, new AString(list.getLabel(), "")));

        // Generating GUI component
        getCategoryTitle().setExpandable(this::onClickExpand);
        ((SimpleParameter)getCategoryTitle())
                .setOnClickMarkedUtil((b) -> getCategoryTitle().setExpanded((boolean)b));

        setAList(list);
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
