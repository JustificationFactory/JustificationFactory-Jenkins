package fr.axonic.avek.gui.view.treat;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.components.parameters.groups.ParametersRoot;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.avek.gui.view.AbstractView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

public class TreatView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(TreatView.class);
    private final static String FXML = "fr/axonic/avek/gui/view/treat/TreatView.fxml";

    @FXML
    private ParametersRoot parametersRoot;
    @FXML
    private MonitoredSystemView monitoredSystemView;
    @FXML
    public Label monitoredSystemTitle;
    @FXML
    public Button monitoredSystemHistory;

    @Override
    protected void onLoad() {
        LOGGER.info("Loading TreatView...");
        super.load(FXML);
        LOGGER.debug("TreatView loaded.");
    }

    @FXML
    private void initialize() {
        // Getting data from Data bus
        monitoredSystemView.setMonitoredSystem(DataBus.getMonitoredSystem());
        parametersRoot.setAList(DataBus.getExperimentParams());
    }

    @FXML
    private SplitPane monitoredSystemSplitPane;
    @FXML
    private BorderPane monitoredSystemPane;
    @FXML
    private ToggleButton outerMonitoredSystemButton;

    @FXML
    public void onClickMonitoredSystemButton(ActionEvent event) {
        boolean newState = !monitoredSystemPane.isVisible();
        if (newState) {
            showPane(0, monitoredSystemPane, monitoredSystemSplitPane, outerMonitoredSystemButton);
        } else {
            hidePane(monitoredSystemPane, monitoredSystemSplitPane, outerMonitoredSystemButton);
        }
    }

}

