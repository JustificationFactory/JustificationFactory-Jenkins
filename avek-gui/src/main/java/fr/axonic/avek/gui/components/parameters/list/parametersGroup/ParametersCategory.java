package fr.axonic.avek.gui.components.parameters.list.parametersGroup;

import fr.axonic.avek.gui.components.parameters.list.ExpParameterLeaf;
import fr.axonic.avek.model.base.AString;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Created by Nathaël N on 21/07/16.
 */
class ParametersCategory extends ParametersGroup {
	private final ExpParameterLeaf categoryTitle;

	ParametersCategory(int level, String label) {
		super(level, label);

		// Generating GUI component
		GridPane.setColumnSpan(this, 3);

		categoryTitle = new ExpParameterLeaf(level, new AString(label,""));
		addExpParameter(categoryTitle);

		categoryTitle.setExpandable(this::onClickExpand);
	}

	private void onClickExpand(boolean isExpanded) {
		for (Node n : getChildren()) {
			if (categoryTitle.getElements().contains(n))
				continue;

			n.setVisible(isExpanded);
			n.setManaged(isExpanded);
		}
	}
}
