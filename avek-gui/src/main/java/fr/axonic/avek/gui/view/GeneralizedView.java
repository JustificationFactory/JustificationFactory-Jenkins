package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.parameters.GeneralizedParametersPane;
import fr.axonic.avek.gui.components.parameters.ParametersPane;
import fr.axonic.avek.gui.components.results.JellyBeansSelector;
import fr.axonic.avek.gui.components.subjects.Subject;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class GeneralizedView extends AbstractView {
	private final static Logger logger = Logger.getLogger(GeneralizedView.class);
	private final static URL FXML
			= GeneralizedView.class.getClassLoader().getResource("fxml/views/GeneralizedView.fxml");

	@FXML
	private Button btnStrategy;
	@FXML
	private GeneralizedParametersPane paneParameters;
	@FXML
	private Subject subject;
	private JellyBeansSelector jellyBeansSelector;

	// Should be public
	public GeneralizedView() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		logger.info("Loading GeneralizedView... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("GeneralizedView loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for GeneralizedView", e);
		}
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}

	/** Fill experiment subject informations
	 *
	 * @param monitoredSystemJson the MonitoredSystem as a Json String
	 */
	void setMonitoredSystem(String monitoredSystemJson) {
		subject.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));
	}

	void setExperimentParameters(String experimentParametersJson) {
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(experimentParametersJson);

		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);
	}
}

