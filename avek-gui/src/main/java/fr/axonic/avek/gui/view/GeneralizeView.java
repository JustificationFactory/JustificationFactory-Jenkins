package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.components.filelist.FileListView;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.avek.gui.components.parameters.groups.GeneralizedRoot;
import fr.axonic.avek.gui.model.DataBus;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralizeView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(GeneralizeView.class);
    private final static String FXML = "fxml/views/GeneralizedView.fxml";

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
        parametersRoot.setAList(DataBus.getExperimentParams());
        monitoredSystemView.setMonitoredSystem(DataBus.getMonitoredSystem());

        Map<String, ARangedEnum> expResMap = DataBus.getExperimentResults();
        for (Map.Entry<String, ARangedEnum> entry : expResMap.entrySet()) {
            List<String> ls = new ArrayList<>();
            for (AEnum ae : new ArrayList<AEnum>(entry.getValue().getRange())) {
                ls.add(ae.getValue().toString());
            }
            jellyBeanPane.addJellyBean(entry.getKey(), ls);
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


    private final Map<SplitPane, double[]> mementos = new HashMap<>();

    private void showPane(int index, Pane pane, SplitPane splitPane, ToggleButton button) {
        splitPane.getItems().add(index, pane);
        pane.setVisible(true);
        pane.setManaged(true);
        button.setSelected(true);
        splitPane.setDividerPositions(mementos.get(splitPane));
    }

    private void hidePane(Pane pane, SplitPane splitPane, ToggleButton button) {
        mementos.put(splitPane, splitPane.getDividerPositions());
        splitPane.getItems().remove(pane);
        pane.setVisible(false);
        pane.setManaged(false);
        button.setSelected(false);
    }

    public Map<String, String> getEffects() {
        return jellyBeanPane.getJellyBeans();
    }
}

