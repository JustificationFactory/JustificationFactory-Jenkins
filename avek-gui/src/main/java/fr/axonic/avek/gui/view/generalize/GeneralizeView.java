package fr.axonic.avek.gui.view.generalize;

import fr.axonic.avek.gui.api.ComponentType;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.components.filelist.FileListView;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.avek.gui.components.monitoredsystem.MonitoredSystemView;
import fr.axonic.avek.gui.components.parameters.groups.GeneralizedRoot;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.avek.gui.model.GUIExperimentParameter;
import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.model.MonitoredSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

public class GeneralizeView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(GeneralizeView.class);
    private final static String FXML = "fr/axonic/avek/gui/view/generalize/GeneralizeView.fxml";

    @FXML
    private JellyBeanPane jellyBeanPane;
    @FXML
    private GeneralizedRoot parametersRoot;
    @FXML
    private MonitoredSystemView monitoredSystemView;
    @FXML
    public FileListView uploadedFiles;
    @FXML
    public TextArea textfieldComments;
    @FXML
    public Label monitoredSystemTitle;
    @FXML
    public Button monitoredSystemHistory;

    @Override
    protected void onLoad() {
        LOGGER.info("Loading GeneralizeView...");
        super.load(FXML);
        LOGGER.debug("GeneralizeView loaded.");
    }

    @FXML
    private void initialize() {
        GUIAPIImpl guiapi = GUIAPIImpl.getInstance();

        // Get data from Data bus
        parametersRoot.setData((GUIExperimentParameter) guiapi.getData(ComponentType.EXPERIMENTATION_PARAMETERS));
        monitoredSystemView.setMonitoredSystem((MonitoredSystem) guiapi.getData(ComponentType.MONITORED_SYSTEM));
        GUIEffect<?> effects = (GUIEffect) guiapi.getData(ComponentType.EFFECTS);

        for(JellyBeanItem jbi : effects.getJellyBeanItemList()) {
            jellyBeanPane.addJellyBean(jbi);
        }
    }


    @FXML
    private SplitPane commentsSplitPane;
    @FXML
    private BorderPane commentsPane;
    @FXML
    private ToggleButton outerCommentsButton;

    @FXML
    public void onClickCommentsButton(ActionEvent event) {
        boolean newState = !commentsPane.isVisible();
        if (newState) {
            showPane(1, commentsPane,
                    commentsSplitPane, outerCommentsButton);
        } else {
            hidePane(commentsPane,
                    commentsSplitPane, outerCommentsButton);
        }
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
        setResultsVisible(!resultsPane.isVisible());
    }

    private void setResultsVisible(boolean isVisible) {
        resultsPane.setVisible(isVisible);
        resultsPane.setManaged(isVisible);
        outerResultsButton.setSelected(isVisible);
    }
}

