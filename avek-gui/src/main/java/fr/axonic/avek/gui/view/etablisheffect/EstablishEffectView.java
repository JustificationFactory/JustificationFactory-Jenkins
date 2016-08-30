package fr.axonic.avek.gui.view.etablisheffect;

import fr.axonic.avek.gui.api.ComponentType;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.components.monitoredsystem.MonitoredSystemView;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanSelector;
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

public class EstablishEffectView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(EstablishEffectView.class);

    private final static String FXML = "fr/axonic/avek/gui/view/etablisheffect/EstablishEffectView.fxml";

    @FXML
    private JellyBeanSelector jellyBeanSelector;
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
        LOGGER.info("Loading EstablishEffectView...");
        super.load(FXML);
    }

    @FXML
    private void initialize() {
        GUIAPIImpl guiapi = GUIAPIImpl.getInstance();

        monitoredSystemView.setMonitoredSystem((MonitoredSystem) guiapi.getData(ComponentType.MONITORED_SYSTEM));

        parametersRoot.setAList((AList<?>) guiapi.getData(ComponentType.EXPERIMENTATION_PARAMETERS));



        jellyBeanSelector.setJellyBeansChoice((List<JellyBeanItem>) guiapi.getData(ComponentType.EFFECTS));
        LOGGER.debug("EstablishEffectView loaded.");
    }

    @FXML
    private SplitPane fileListSplitPane;
    @FXML
    private BorderPane fileListPane;
    @FXML
    private ToggleButton outerFileListButton;

    @FXML
    public void onClickFileListViewButton(ActionEvent event) {
        boolean newState = !fileListPane.isVisible();
        if (newState) {
            showPane(1, fileListPane,
                    fileListSplitPane, outerFileListButton);
        } else {
            hidePane(fileListPane,
                    fileListSplitPane, outerFileListButton);
        }
    }

    @FXML
    private SplitPane monitoredSystemSplitPane;
    @FXML
    private BorderPane monitoredSystemPane;
    @FXML
    private ToggleButton outerMonitoredSystemButton;

    @FXML
    public void onClickMonitoredSystemButton(ActionEvent event) {
        setMonitoredSystemVisible(!monitoredSystemPane.isVisible());
    }

    private void setMonitoredSystemVisible(boolean isVisible) {
        if (isVisible) {
            showPane(0, monitoredSystemPane,
                    monitoredSystemSplitPane, outerMonitoredSystemButton);
        } else {
            hidePane(monitoredSystemPane,
                    monitoredSystemSplitPane, outerMonitoredSystemButton);
        }
    }

    @FXML
    private BorderPane resultsPane;
    @FXML
    private ToggleButton outerResultsButton;

    @FXML
    public void onClickResultsButton(ActionEvent event) {
        boolean newState = !resultsPane.isVisible();
        resultsPane.setVisible(newState);
        resultsPane.setManaged(newState);
        outerResultsButton.setSelected(newState);
    }

    public List<JellyBeanItem> getEffects() {
        return jellyBeanSelector.getSelected();
    }
}

