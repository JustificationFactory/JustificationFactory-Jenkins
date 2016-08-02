package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemView;
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

public class TreatView extends AbstractView {
	private final static Logger logger = Logger.getLogger(TreatView.class);
	private final static String FXML = "fxml/views/TreatView.fxml";

	@FXML private ParametersRoot parametersRoot;
	@FXML private MonitoredSystemView monitoredSystemView;
	@FXML public Label monitoredSystemTitle;
	@FXML public Button monitoredSystemHistory;

	@Override
	protected void onLoad() {
		logger.info("Loading TreatView...");
		super.load(FXML);
		logger.debug("TreatView loaded.");
	}

	@FXML
	private void initialize() {
		logger.info("Getting monitored system...");
		monitoredSystemView.setMonitoredSystem(DataBus.getMonitoredSystem());

		logger.info("Getting experiment parameters...");
		AList<AEntity> list = DataBus.getExperimentParameters();
		list.getList().forEach(parametersRoot::addParameter);
	}

	@FXML private SplitPane monitoredSystemSplitPane;
	@FXML private BorderPane monitoredSystemPane;
	@FXML private ToggleButton outerMonitoredSystemButton;
	private double[] memento = {};
	@FXML public void onClickMonitoredSystemButton(ActionEvent event) {
		boolean newState = !monitoredSystemPane.isVisible();
		if(newState) {
			monitoredSystemSplitPane.getItems().add(0, monitoredSystemPane);
			outerMonitoredSystemButton.setSelected(true);
			monitoredSystemPane.setVisible(true);
			monitoredSystemSplitPane.setDividerPositions(memento);
		}
		else {
			memento = monitoredSystemSplitPane.getDividerPositions();
			monitoredSystemSplitPane.getItems().remove(monitoredSystemPane);
			outerMonitoredSystemButton.setSelected(false);
			monitoredSystemPane.setVisible(false);
		}
	}

}

