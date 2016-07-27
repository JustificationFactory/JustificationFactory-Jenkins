package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.components.parameters.ParametersPane;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

public class TreatView extends AbstractView {
	private final static Logger logger = Logger.getLogger(TreatView.class);
	private final static String FXML = "fxml/views/TreatView.fxml";

	@FXML
	private Button btnStrategy;
	@FXML
	private ParametersPane paneParameters;
	@FXML
	private MonitoredSystemPane monitoredSystemPane;

	@Override
	protected void onLoad() {
		logger.info("Loading TreatView...");
		super.load(FXML);
		logger.debug("TreatView loaded.");
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
		monitoredSystemPane.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));
	}

	public void setExperimentParameters(String experimentParametersJson) {
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(experimentParametersJson);

		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);
	}
}

