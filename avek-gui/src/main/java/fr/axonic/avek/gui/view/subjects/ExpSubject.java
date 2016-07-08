package fr.axonic.avek.gui.view.subjects;

import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.AVar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ExpSubject extends BorderPane {

	@FXML
	private Label title;
	@FXML
	private Accordion acrdnExpSubject;
	@FXML
	private Button btnHistory;

	public ExpSubject() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/subjects/subject.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	@FXML
	private void onClicShowHistory(ActionEvent event) {
		btnHistory.setDisable(true);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setMonitoredSystem(MonitoredSystem ms) {
		Platform.runLater(() -> {
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
		});
	}

}
