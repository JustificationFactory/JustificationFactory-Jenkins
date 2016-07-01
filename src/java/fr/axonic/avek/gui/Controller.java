package fr.axonic.avek.gui;

import fr.axonic.avek.gui.model.AVar;
import fr.axonic.avek.gui.model.IResultElement;
import fr.axonic.avek.gui.model.MonitoredSystem;
import fr.axonic.avek.gui.model.StringResultElement;
import fr.axonic.avek.gui.view.JellyBean;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

	@FXML
	private Button b_strategy;
	@FXML
	private VBox pane_data;
	@FXML
	private Button b_addResult;
	@FXML
	private FlowPane pane_selectedResult;
	@FXML
	private Button b_history;
	@FXML
	private ComboBox<IResultElement> cb_selecteffect;
	@FXML
	private Accordion accordion;

	@FXML
	protected void initialize() {
		updateSelectedEffect();

		cb_selecteffect.setItems(FXCollections.observableArrayList(
				new StringResultElement("Effect 1"),
				new StringResultElement("Effect 2"),
				new StringResultElement("Effect 3")
		));


		MonitoredSystem ms = new MonitoredSystem(42);
		{ // Replace all this by a request of Subject informations
			ms.addCategory("Category 1");
			ms.addAVar("Category 1", new AVar("a string", String.class, "strval1"));
			ms.addAVar("Category 1", new AVar("age", Integer.class, 123456789, "year"));
			ms.addAVar("Category 1", new AVar("size", Double.class, 12345.6789, "mm"));
			ms.addAVar("Category 1", new AVar("a date", Date.class, Calendar.getInstance().getTime()));
			ms.addCategory("Category 2");
			ms.addAVar("Category 2", new AVar("an integer", Integer.class, 987654321));
			ms.addAVar("Category 2", new AVar("a double", Double.class, 98765.4321));
		}

		fillSubjectInfos(ms);
	}

	private void fillSubjectInfos(MonitoredSystem ms) {
		Map<String, Set<AVar>> map = ms.getMap();
		for(String category : map.keySet()) {

			VBox vb = new VBox();
			accordion.getPanes().add(new TitledPane(category, vb));

			for(AVar av : map.get(category)) {
				HBox hb = new HBox(2);
				hb.getChildren().add(new Label(av.toString()));
				vb.getChildren().add(hb);
			}
		}
	}

	private void updateSelectedEffect() {
		cb_selecteffect.setCellFactory(
				new Callback<ListView<IResultElement>, ListCell<IResultElement>>() {
					@Override public ListCell<IResultElement> call(ListView<IResultElement> param) {
						final ListCell<IResultElement> cell = new ListCell<IResultElement>() {
							@Override public void updateItem(IResultElement item, boolean empty) {
								super.updateItem(item, empty);
								if(item != null) {
									setText(item.getName());
									getStyleClass().remove("selectedEffect");
									getStyleClass().remove("notSelectedEffect");
									getStyleClass().add(getSelectedEffects().contains(item)?"selectedEffect":"notSelectedEffect");
								}
							}
						};
						return cell;
					}
				});
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		showPopUp("Strategy !");
	}

	@FXML
	void onClicAddEffect(ActionEvent event) {
		IResultElement choice = cb_selecteffect.getValue();
		if(getSelectedEffects().contains(choice))
			return;

		pane_selectedResult.getChildren().add(new JellyBean(this, choice));
		updateSelectedEffect();
	}

	private HashSet<IResultElement> getSelectedEffects() {
		return pane_selectedResult.getChildren().stream()
				.map(n -> ((JellyBean) n).getIEffect())
				.collect(Collectors.toCollection(HashSet::new));
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

	public void removeEffectNode(JellyBean jellyBean) {
		pane_selectedResult.getChildren().remove(jellyBean);
		updateSelectedEffect();
	}
}

