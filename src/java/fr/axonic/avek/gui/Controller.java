package fr.axonic.avek.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.PopupBuilder;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {

	@FXML
	private Button b_strategy;

	@FXML
	private VBox pane_data;

	@FXML
	private Button b_addeffect;

	@FXML
	private FlowPane pane_effects;

	@FXML
	private Button b_history;

	@FXML
	private ComboBox<String> cb_selecteffect;

	@FXML
	protected void initialize() {
		cb_selecteffect.setItems(FXCollections.observableArrayList(
				"",
				"Effet 1",
				"Effet 2",
				"Effet 3"
		));
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		showPopUp("Strategy !");
	}

	@FXML
	void onClicAddEffect(ActionEvent event) {
		String choice = cb_selecteffect.getValue();
		if(choice.equals(""))
			return;

		//showPopUp("Effect Add: "+choice);
		cb_selecteffect.getItems().remove(choice);

		pane_effects.getChildren().add(new EffectNode(this, choice));
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

	public void removeEffectNode(EffectNode effectNode) {
		List<String> l = new ArrayList(cb_selecteffect.getItems());
		l.add(effectNode.getName());

		Collections.sort(l);
		cb_selecteffect.getItems().clear();
		cb_selecteffect.getItems().addAll(l);

		pane_effects.getChildren().remove(effectNode);
	}
}

