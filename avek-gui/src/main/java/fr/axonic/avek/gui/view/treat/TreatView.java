package fr.axonic.avek.gui.view.treat;

import fr.axonic.avek.gui.api.ComponentType;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.components.monitoredsystem.MonitoredSystemView;
import fr.axonic.avek.gui.components.parameters.groups.ParametersRoot;
import fr.axonic.avek.gui.model.GUIExperimentParameter;
import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.model.MonitoredSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

public class TreatView extends AbstractView {
    private static final Logger LOGGER = Logger.getLogger(TreatView.class);
    private static final String FXML = "fr/axonic/avek/gui/view/treat/TreatView.fxml";

    @FXML
    private ParametersRoot parametersRoot;
    @FXML
    private MonitoredSystemView monitoredSystemView;
    @FXML
    public Label monitoredSystemTitle;
    @FXML
    public Button monitoredSystemHistory;

    @FXML
    private SplitPane monitoredSystemSplitPane;
    @FXML
    private BorderPane monitoredSystemPane;
    @FXML
    private ToggleButton outerMonitoredSystemButton;

    @Override
    protected void onLoad() {
        LOGGER.info("Loading TreatView...");
        super.load(FXML);
        LOGGER.debug("TreatView loaded.");
    }

    @FXML
    private void initialize() {
        GUIAPIImpl guiapi = GUIAPIImpl.getInstance();

        monitoredSystemView.setMonitoredSystem((MonitoredSystem) guiapi.getData(ComponentType.MONITORED_SYSTEM));

        parametersRoot.setData((GUIExperimentParameter) guiapi.getData(ComponentType.EXPERIMENTATION_PARAMETERS));
    }

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

