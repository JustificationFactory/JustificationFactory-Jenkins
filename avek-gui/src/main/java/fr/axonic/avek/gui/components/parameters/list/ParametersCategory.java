package fr.axonic.avek.gui.components.parameters.list;

import fr.axonic.avek.gui.components.parameters.list.leaves.CategoryTitle;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class ParametersCategory extends ParametersGroup {
	private static final Logger logger = Logger.getLogger(ParametersCategory.class);
	private static final String CSS = "css/Parameters.css";

	private final CategoryTitle simpleParam;

	ParametersCategory(int level, String label) {
		super(level, label);

		simpleParam = new CategoryTitle(level, label, this::onClickExpand);

		// Generating GUI component
		GridPane.setColumnSpan(this, 6);
		addExpParameter(simpleParam);
		this.setVgap(2);


		logger.info("Adding Parameters.css");
		this.getStylesheets().add(CSS);
	}

	private void onClickExpand(boolean isExpanded) {
		for (Node n : getChildren()) {
			if (simpleParam.getElements().contains(n))
				continue;

			n.setVisible(isExpanded);
			n.setManaged(isExpanded);
		}
	}
}
