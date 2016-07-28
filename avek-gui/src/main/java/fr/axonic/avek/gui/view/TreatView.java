package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.components.parameters.ParametersPane;
import fr.axonic.avek.gui.model.DataBus;
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

		logger.info("Getting monitored system...");
		monitoredSystemPane.setMonitoredSystem(DataBus.getMonitoredSystem());

		logger.info("Getting experiment parameters...");
		AList<AEntity> list = DataBus.getExperimentParameters();
		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);

		logger.debug("TreatView loaded.");
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}
}

