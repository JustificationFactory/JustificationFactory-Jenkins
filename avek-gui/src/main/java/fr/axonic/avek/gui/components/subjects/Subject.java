package fr.axonic.avek.gui.components.subjects;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class Subject extends BorderPane {
	private final static Logger logger = Logger.getLogger(Subject.class);
	private static final URL FXML
			= Subject.class.getClassLoader().getResource("fxml/components/Subject.fxml");
	private static final String CSS = "css/subjects/subjects.css";

	@FXML
	private Label title;
	@FXML
	private Accordion acrdnExpSubject;
	@FXML
	private Button btnHistory;

	// should be public
	public Subject() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading Subject... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("Subject loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for Subject", e);
		}

		logger.debug("Adding subject.css");
		this.getStylesheets().add(CSS);
	}

	@FXML
	private void onClicShowHistory(ActionEvent event) {
		btnHistory.setDisable(true);
	}

	public void setMonitoredSystem(MonitoredSystem ms) {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		logger.debug("Setting monitored system");
		ctm.runLaterOnPlatform(acrdnExpSubject.getPanes()::clear);

		Map<String, Set<AVar>> map = ms.getMap();
		for (String category : map.keySet()) {
			ScrollPane sp = new ScrollPane();
			VBox vb = new VBox();

			ctm.runLaterOnPlatform(() -> acrdnExpSubject.getPanes().add(new TitledPane(category, sp)));
			sp.setContent(vb);

			for (AVar av : map.get(category))
				vb.getChildren().add(new Label(av.getLabel() + " : " + av.getValue().toString()));
		}

		ctm.waitForTasks();
	}

}
