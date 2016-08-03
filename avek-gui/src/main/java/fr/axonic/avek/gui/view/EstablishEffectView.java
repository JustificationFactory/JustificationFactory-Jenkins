package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.components.jellyBeans.JellyBeanSelector;
import fr.axonic.avek.gui.components.parameters.list.parametersGroup.ParametersRoot;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		new Thread(() -> { // Load asynchronously
			Platform.runLater(() -> monitoredSystemView.setMonitoredSystem(DataBus.getMonitoredSystem()));

			Platform.runLater(() ->
					DataBus.getExperimentParameters().forEach(parametersRoot::addParameter));

			Map<String, ARangedEnum> expResMap = DataBus.getExperimentResults();
			Map<String, List<String>> map = new HashMap<>();
			for (Map.Entry<String, ARangedEnum> entry : expResMap.entrySet()) {
				List<String> ls = new ArrayList<>();
				for (AEnum ae : new ArrayList<AEnum>(entry.getValue().getRange()))
					ls.add(ae.getValue().toString());
				map.put(entry.getKey(), ls);
			}
			Platform.runLater(() -> jellyBeanSelector.setJellyBeansChoice(map));
		}).start();
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

