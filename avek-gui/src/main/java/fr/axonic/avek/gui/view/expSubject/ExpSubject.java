package fr.axonic.avek.gui.view.expSubject;

import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.AVar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

	public ExpSubject() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/expSubject/expSubject.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	private void onClicShowHistory(ActionEvent event) {
		btnHistory.setDisable(true);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void setData(MonitoredSystem ms) {
		Map<String, Set<AVar>> map = ms.getMap();
		for (String category : map.keySet()) {
			VBox vb = new VBox();
			acrdnExpSubject.getPanes().add(new TitledPane(category, vb));

			for (AVar av : map.get(category))
				vb.getChildren().add(new Label(av.getLabel() + " : " + new Label(av.getValue().toString())));
		}
	}

}
