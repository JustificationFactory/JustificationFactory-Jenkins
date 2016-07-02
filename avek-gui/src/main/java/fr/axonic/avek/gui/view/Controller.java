package fr.axonic.avek.gui.view;
import fr.axonic.avek.gui.model.IResultElement;
import fr.axonic.avek.gui.view.jellyBean.JellyBeansSelector;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.gui.model.StringResultElement;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import fr.axonic.avek.model.base.AVar;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class Controller {

	@FXML
	private Button b_strategy;
	@FXML
	private VBox pane_data;


	@FXML
	private Button b_history;

	@FXML
	private Accordion accordion;

	@FXML
	private JellyBeansSelector jellyBeansSelector;


	@FXML
	protected void initialize() {

		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string", "strval1"));
		ms.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms.addAVar("Category 1", new ANumber("a double", 12345.6789));
		ms.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms.addAVar("Category 2", new ANumber("a double", 98765.4321));

		List<IResultElement> lre = new ArrayList<>();
		for (int i = 1; i <= 30; i++)
			lre.add(new StringResultElement("Effect " + i));

		fillSubjectInfos(ms);
		fillEffects(lre);
	}

	private void fillEffects(List<IResultElement> lre) {
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(lre));
	}

	private void fillSubjectInfos(MonitoredSystem ms) {
		Map<String, Set<AVar>> map = ms.getMap();
		for (String category : map.keySet()) {

			VBox vb = new VBox();
			accordion.getPanes().add(new TitledPane(category, vb));

			for (AVar av : map.get(category)) {
				HBox hb = new HBox(2);
				hb.getChildren().add(new Label(av.getLabel()));
				hb.getChildren().add(new Label(":"));
				hb.getChildren().add(new Label(av.getValue().toString()));

				vb.getChildren().add(hb);
			}
		}
	}


	@FXML
	void onClicStrategyButton(ActionEvent event) {
		showPopUp("Strategy !");
	}


	@FXML
	void onClicShowHistory(ActionEvent event) {
		showPopUp("History !");
	}

	void showPopUp(String text) {
		Label textField = new Label(text);

		Stage newStage = new Stage();
		VBox comp = new VBox();
		comp.getChildren().add(textField);

		Scene stageScene = new Scene(comp);
		newStage.setScene(stageScene);
		newStage.sizeToScene();

		newStage.show();
	}
}


