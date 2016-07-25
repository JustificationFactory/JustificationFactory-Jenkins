package fr.axonic.avek.gui.view.subjects;

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
public class ExpSubject extends BorderPane {
	private final static Logger logger = Logger.getLogger(ExpSubject.class);
	private static final URL SUBJECT_FXML
			= ExpSubject.class.getClassLoader().getResource("fxml/subjects/subject.fxml");
	private static final String SUBJECT_CSS = "css/subjects/subjects.css";

	@FXML
	private Label title;
	@FXML
	private Accordion acrdnExpSubject;
	@FXML
	private Button btnHistory;

	// should be public
	public ExpSubject() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(SUBJECT_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.debug("Loading subject.fxml");
		fxmlLoader.load();

		logger.debug("Adding subject.css");
		this.getStylesheets().add(SUBJECT_CSS);
	}

	@FXML
	private void onClicShowHistory(ActionEvent event) {
		btnHistory.setDisable(true);
	}

	public boolean setMonitoredSystem(MonitoredSystem ms) {
		logger.debug("Setting monitored system");
		acrdnExpSubject.getPanes().clear();

		Map<String, Set<AVar>> map = ms.getMap();
		for (String category : map.keySet()) {
			ScrollPane sp = new ScrollPane();
			VBox vb = new VBox();

			acrdnExpSubject.getPanes().add(new TitledPane(category, sp));

			sp.setContent(vb);

			for (AVar av : map.get(category))
				vb.getChildren().add(new Label(av.getLabel() + " : " + av.getValue().toString()));
		}

		return true;
	}

}
