package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.components.parameters.list.ParametersGroup;
import fr.axonic.avek.model.base.engine.AEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class ParametersPane extends SplitPane {
	private final static Logger logger = Logger.getLogger(ParametersPane.class);

	private static final URL FXML
			= ParametersPane.class.getClassLoader().getResource("fxml/components/Parameters.fxml");

	@FXML
	private ParametersGroup parametersGroup;

	// should be public
	public ParametersPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading ParametersPane... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("ParametersPane loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for ParametersPane", e);
		}
	}

	public void addExpParameter(AEntity value) {
		parametersGroup.addParameter(value);
	}
}
