package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.components.parameters.GeneralizedParametersPane;
import fr.axonic.avek.gui.components.results.JellyBeansSelector;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

public class GeneralizedView extends AbstractView {
	private final static Logger logger = Logger.getLogger(GeneralizedView.class);
	private final static String FXML = "fxml/views/GeneralizedView.fxml";

	@FXML
	private Button btnStrategy;
	@FXML
	private GeneralizedParametersPane paneParameters;
	@FXML
	private MonitoredSystemPane monitoredSystemPane;
	@FXML
	private JellyBeansSelector jellyBeansSelector;

	@Override
	protected void onLoad() {
		logger.info("Loading GeneralizedView...");
		super.load(FXML);
		logger.debug("GeneralizedView loaded.");
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}

	/** Fill experiment monitoredSystemPane informations
	 *
	 * @param monitoredSystemJson the MonitoredSystem as a Json String
	 */
	public void setMonitoredSystem(String monitoredSystemJson) {
		MonitoredSystem ms = MonitoredSystem.fromJson(monitoredSystemJson);

		monitoredSystemPane.setMonitoredSystem(ms);
	}


	public void setExperimentParameters(String experimentParametersJson) {
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(experimentParametersJson);

		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);
	}
}

