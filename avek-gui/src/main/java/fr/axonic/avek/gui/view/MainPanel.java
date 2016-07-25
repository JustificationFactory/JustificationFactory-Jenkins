package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.view.parameters.ParametersPane;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.gui.view.subjects.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

public class MainPanel extends BorderPane {
	private final static Logger logger = Logger.getLogger(MainPanel.class);
	private final static URL MAINPANEL_FXML
			= MainPanel.class.getClassLoader().getResource("fxml/MainPanel.fxml");

	@FXML
	private Button btnStrategy;
	@FXML
	private ParametersPane paneParameters;
	@FXML
	private ExpSubject expSubject;
	private JellyBeansSelector jellyBeansSelector;

	public MainPanel() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(MAINPANEL_FXML);
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		logger.debug("Loading MainPanel... (fxml)");
		fxmlLoader.load();
		logger.debug("MainPanel loaded.");
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}

	public void setEffectSelector() {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		jellyBeansSelector = new JellyBeansSelector();
		ctm.runLaterOnPlatform(() -> ((BorderPane) this.getCenter()).setTop(jellyBeansSelector));
	}

	/** Fill experiment subject informations
	 *
	 * @param monitoredSystemJson the MonitoredSystem as a Json String
	 */
	public void setMonitoredSystem(String monitoredSystemJson) {
		expSubject.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));
	}

	public void setExperimentParameters(String experimentParametersJson) {
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(experimentParametersJson);

		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);
	}
}

