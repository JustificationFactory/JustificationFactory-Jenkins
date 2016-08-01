package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.components.jellyBeans.JellyBeansSelector;
import fr.axonic.avek.gui.components.parameters.list.parametersGroup.ParametersRoot;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

public class EstablishEffectView extends AbstractView {
	private final static Logger logger = Logger.getLogger(EstablishEffectView.class);
	private final static String FXML = "fxml/views/EstablishEffectView.fxml";

	@FXML private JellyBeansSelector jellyBeansSelector;
	@FXML private ParametersRoot parametersRoot;
	@FXML private MonitoredSystemView monitoredSystemView;
	@FXML public Label monitoredSystemTitle;
	@FXML public Button monitoredSystemHistory;

	@Override
	protected void onLoad() {
		logger.info("Loading EstablishEffectView...");
		super.load(FXML);
		logger.debug("EstablishEffectView loaded.");
	}

	@FXML
	private void initialize() {
		logger.info("Getting monitored system...");
		monitoredSystemView.setMonitoredSystem(DataBus.getMonitoredSystem());

		logger.info("Getting experiment parameters...");
		AList<AEntity> list = DataBus.getExperimentParameters();
		list.getList().forEach(parametersRoot::addParameter);

		logger.info("Getting experiment results...");
		jellyBeansSelector.setJellyBeansChoice(DataBus.getExperimentResults());
	}


	@FXML private SplitPane monitoredSystemSplitPane;
	@FXML private BorderPane monitoredSystemPane;
	@FXML private ToggleButton outerMonitoredSystemButton;
	@FXML public void onClickMonitoredSystemButton(ActionEvent event) {
		boolean newState = !monitoredSystemPane.isVisible();
		if(newState) {
			monitoredSystemSplitPane.getItems().add(0, monitoredSystemPane);
			monitoredSystemPane.setVisible(true);
			outerMonitoredSystemButton.setSelected(true);
		}
		else {
			monitoredSystemSplitPane.getItems().remove(monitoredSystemPane);
			monitoredSystemPane.setVisible(false);
			outerMonitoredSystemButton.setSelected(false);
		}
	}

}

