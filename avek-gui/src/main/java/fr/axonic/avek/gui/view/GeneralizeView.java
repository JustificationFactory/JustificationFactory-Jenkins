package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.components.filelist.FileListView;
import fr.axonic.avek.gui.components.jellyBeans.JellyBeanPane;
import fr.axonic.avek.gui.components.parameters.list.parametersGroup.GeneralizedParametersRoot;
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

public class GeneralizeView extends AbstractView {
	private final static Logger logger = Logger.getLogger(GeneralizeView.class);
	private final static String FXML = "fxml/views/GeneralizedView.fxml";

	@FXML private JellyBeanPane jellyBeanPane;
	@FXML private GeneralizedParametersRoot parametersRoot;
	@FXML private MonitoredSystemView monitoredSystemView;
	@FXML public FileListView uploadedFiles;
	@FXML public TextArea textfieldComments;
	@FXML public Label monitoredSystemTitle;
	@FXML public Button monitoredSystemHistory;

	@Override
	protected void onLoad() {
		logger.info("Loading GeneralizeView...");
		super.load(FXML);
		logger.debug("GeneralizeView loaded.");
	}

	@FXML
	private void initialize() {
		logger.info("Getting monitored system...");
		monitoredSystemView.setMonitoredSystem(DataBus.getMonitoredSystem());

		logger.info("Getting experiment parameters...");
		AList<AEntity> list = DataBus.getExperimentParameters();
		list.getList().forEach(parametersRoot::addParameter);

		logger.info("Getting experiment results...");
		ExperimentResultsMap expResMap = DataBus.getExperimentResults();

		for(ExperimentResult expRes : expResMap.getList())
			jellyBeanPane.addJellyBean(expRes);
	}


	@FXML private SplitPane commentsSplitPane;
	@FXML private BorderPane commentsPane;
	@FXML private ToggleButton outerCommentsButton;
	@FXML public void onClickCommentsButton(ActionEvent event) {
		boolean newState = !commentsPane.isVisible();
		if(newState)
			showPane(1,commentsPane,
					commentsSplitPane, outerCommentsButton);
		else
			hidePane(commentsPane,
					commentsSplitPane, outerCommentsButton);
	}

	@FXML private SplitPane fileListSplitPane;
	@FXML private BorderPane fileListPane;
	@FXML private ToggleButton outerFileListButton;
	@FXML public void onClickFileListViewButton(ActionEvent event) {
		boolean newState = !fileListPane.isVisible();
		if(newState)
			showPane(1, fileListPane,
					fileListSplitPane, outerFileListButton);
		else
			hidePane(fileListPane,
					fileListSplitPane, outerFileListButton);
	}

	@FXML private SplitPane monitoredSystemSplitPane;
	@FXML private BorderPane monitoredSystemPane;
	@FXML private ToggleButton outerMonitoredSystemButton;
	@FXML public void onClickMonitoredSystemButton(ActionEvent event) {
		boolean newState = !monitoredSystemPane.isVisible();
		if(newState)
			showPane(0, monitoredSystemPane,
					monitoredSystemSplitPane, outerMonitoredSystemButton);
		else
			hidePane(monitoredSystemPane,
					monitoredSystemSplitPane, outerMonitoredSystemButton);
	}

	@FXML private BorderPane resultsPane;
	@FXML private ToggleButton outerResultsButton;
	@FXML public void onClickResultsButton(ActionEvent event) {
		boolean newState = !resultsPane.isVisible();
		resultsPane.setVisible(newState);
		resultsPane.setManaged(newState);
		outerResultsButton.setSelected(newState);
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

}

