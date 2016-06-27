package fr.axonic.avek.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

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
	private ComboBox<?> cb_selecteffect;

	@FXML
	void clicOnStrategyButton(ActionEvent event) {

	}

	@FXML
	void addEffect(ActionEvent event) {

	}

	@FXML
	void showHistory(ActionEvent event) {

	}

}

