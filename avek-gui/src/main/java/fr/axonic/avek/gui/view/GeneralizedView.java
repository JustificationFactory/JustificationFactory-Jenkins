package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.components.jellyBeans.JellyBeanPane;
import fr.axonic.avek.gui.components.parameters.GeneralizedParametersPane;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.avek.gui.model.structure.ExperimentResult;
import fr.axonic.avek.gui.model.structure.ExperimentResultsMap;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
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
	private JellyBeanPane jellyBeanPane;

	@Override
	protected void onLoad() {
		logger.info("Loading GeneralizedView...");
		super.load(FXML);

		logger.info("Getting monitored system...");
		monitoredSystemPane.setMonitoredSystem(DataBus.getMonitoredSystem());

		logger.info("Getting experiment parameters...");
		AList<AEntity> list = DataBus.getExperimentParameters();
		for (AEntity ae : list.getList())
			paneParameters.addExpParameter(ae);

		logger.info("Getting experiment results...");
		ExperimentResultsMap expResMap =
				DataBus.getExperimentResults();

		for(ExperimentResult expRes : expResMap.getList())
			jellyBeanPane.addJellyBean(expRes);

		logger.debug("GeneralizedView loaded.");
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}

}

