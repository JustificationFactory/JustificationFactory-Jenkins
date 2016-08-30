package fr.axonic.avek.gui.view.treat;

import fr.axonic.avek.gui.api.ComponentType;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.components.monitoredsystem.MonitoredSystemView;
import fr.axonic.avek.gui.components.parameters.groups.ParametersRoot;
import fr.axonic.avek.bus.DataTranslator;
import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.util.List;

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
        GUIAPIImpl guiapi = GUIAPIImpl.getInstance();

        monitoredSystemView.setMonitoredSystem((MonitoredSystem) guiapi.getData(ComponentType.MONITORED_SYSTEM));

        parametersRoot.setAList((AList<?>) guiapi.getData(ComponentType.EXPERIMENTATION_PARAMETERS));
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

