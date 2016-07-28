package fr.axonic.avek.gui.components.parameters.list.parametersGroup;

import fr.axonic.avek.gui.components.parameters.list.leaves.SimpleParameter;
import fr.axonic.avek.model.base.AString;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Created by Nathaël N on 21/07/16.
 */
class GeneralizedParametersCategory extends GeneralizedParametersGroup {
	private final SimpleParameter categoryTitle;

	GeneralizedParametersCategory(int level, String label) {
		super(level, label);

		categoryTitle = new SimpleParameter(level, new AString(label,""));

		// Generating GUI component
		GridPane.setColumnSpan(this, 5);
		addExpParameter(categoryTitle);

		categoryTitle.setExpandable(this::onClickExpand);
		categoryTitle.setOnClickMarkedUtil(this::onClickExpand);
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
