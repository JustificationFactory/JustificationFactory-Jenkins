package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.components.filelist.FileListView;
import fr.axonic.avek.gui.components.jellyBeans.JellyBeanPane;
import fr.axonic.avek.gui.components.jellyBeans.JellyBeanSelector;
import fr.axonic.avek.gui.components.parameters.list.parametersGroup.GeneralizedParametersRoot;
import fr.axonic.avek.gui.components.parameters.list.parametersGroup.ParametersRoot;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.avek.gui.model.structure.ExperimentResult;
import fr.axonic.avek.gui.model.structure.ExperimentResultsMap;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EstablishEffectView extends AbstractView {
	private final static Logger logger = Logger.getLogger(EstablishEffectView.class);
	private final static String FXML = "fxml/views/EstablishEffectView.fxml";

	@FXML private JellyBeanSelector jellyBeanSelector;
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
		jellyBeanSelector.setJellyBeansChoice(DataBus.getExperimentResults());
	}

	@FXML private SplitPane monitoredSystemSplitPane;
	@FXML private BorderPane monitoredSystemPane;
	@FXML private ToggleButton outerMonitoredSystemButton;
	private double[] memento = {};
	@FXML public void onClickMonitoredSystemButton(ActionEvent event) {
		boolean newState = !monitoredSystemPane.isVisible();
		if(newState) {
			monitoredSystemSplitPane.getItems().add(0, monitoredSystemPane);
			monitoredSystemPane.setVisible(true);
			outerMonitoredSystemButton.setSelected(true);
			monitoredSystemSplitPane.setDividerPositions(memento);
		}
		else {
			memento = monitoredSystemSplitPane.getDividerPositions();
			monitoredSystemSplitPane.getItems().remove(monitoredSystemPane);
			monitoredSystemPane.setVisible(false);
			outerMonitoredSystemButton.setSelected(false);
		}
	}

	@FXML private BorderPane resultsPane;
	@FXML private ToggleButton outerResultsButton;
	@FXML public void onClickResultsButton(ActionEvent event) {
		boolean newState = !resultsPane.isVisible();
		resultsPane.setVisible(newState);
		resultsPane.setManaged(newState);
		outerResultsButton.setSelected(newState);
	}
}

