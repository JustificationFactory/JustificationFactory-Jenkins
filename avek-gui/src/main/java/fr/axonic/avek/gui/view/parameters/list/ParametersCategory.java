package fr.axonic.avek.gui.view.parameters.list;

import fr.axonic.avek.gui.view.parameters.list.types.SimpleParameter;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class ParametersCategory extends ParametersGroup {
	private static final Logger logger = Logger.getLogger(ParametersCategory.class);
	private static final String PARAMETERS_CSS = "css/parameters/parameters.css";

	private final SimpleParameter simpleParam;

	ParametersCategory(int level, String label) {
		super(level, label);

		simpleParam = new SimpleParameter(level, "");

		// Generating GUI component
		GridPane.setColumnSpan(this, 6);
		addExpParameter(simpleParam);
		this.setVgap(2);


		logger.info("Adding parameters.css");
		this.getStylesheets().add(PARAMETERS_CSS);
	}
}
