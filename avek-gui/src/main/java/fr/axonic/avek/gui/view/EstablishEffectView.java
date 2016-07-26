package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.components.parameters.ParametersPane;
import fr.axonic.avek.gui.components.results.JellyBeansSelector;
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

public class EstablishEffectView extends AbstractView {
	private final static Logger logger = Logger.getLogger(EstablishEffectView.class);
	private final static URL FXML
			= EstablishEffectView.class.getClassLoader().getResource("fxml/views/GeneralizedView.fxml");

	@FXML
	private Button btnStrategy;
	@FXML
	private ParametersPane paneParameters;
	@FXML
	private MonitoredSystemPane monitoredSystemPane;
	@FXML
	private JellyBeansSelector jellyBeansSelector;

	// Should be public
	public EstablishEffectView() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		logger.info("Loading EstablishEffectView... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("EstablishEffectView loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for EstablishEffectView", e);
		}
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}

	/** Fill experiment monitoredSystemPane informations
	 *
	 * @param monitoredSystemJson the MonitoredSystem as a Json String
	 */
	void setMonitoredSystem(String monitoredSystemJson) {
		monitoredSystemPane.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));
	}

	void setExperimentParameters(String experimentParametersJson) {
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(experimentParametersJson);

		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);
	}
}

