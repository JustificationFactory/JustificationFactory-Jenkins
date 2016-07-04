package fr.axonic.avek.gui.view;
import fr.axonic.avek.gui.model.IExpEffect;
import fr.axonic.avek.gui.model.MonitoredSystem;
import fr.axonic.avek.gui.model.StringExpEffect;
import fr.axonic.avek.gui.model.base.ADate;
import fr.axonic.avek.gui.model.base.ANumber;
import fr.axonic.avek.gui.model.base.AString;
import fr.axonic.avek.gui.model.base.AVar;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.*;

public class MainController {

	@FXML
	private Button btnStrategy;
	@FXML
	private VBox paneExpParameters;
	@FXML
	private Button btnAddExpEffect;
	@FXML
	private FlowPane paneExpEffects;
	@FXML
	private Button btnHistory;
	@FXML
	private ComboBox<IExpEffect> cbboxExpEffectList;
	@FXML
	private Accordion acrdnExpSubject;

	// All ExpEffect JellyBean's controllers
	// Used to know what effect is selected
	private HashSet<JellyBeanController> jellyBeanControllers;

	@FXML
	protected void initialize() {
		jellyBeanControllers = new HashSet<>();

		updateSelectedEffect();
		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string","strval1"));
		ms.addAVar("Category 1", new ANumber("an integer",123456789));
		ms.addAVar("Category 1", new ANumber("a double",12345.6789));
		ms.addAVar("Category 1", new ADate("a date",Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer",987654321));
		ms.addAVar("Category 2", new ANumber("a double",98765.4321));

		List<IExpEffect> lre = new ArrayList<>();
		for(int i=1; i<=30; i++)
			lre.add(new StringExpEffect("Effect "+i));

		fillExpSubjectInfos(ms);
		fillExpEffectList(lre);
	}

	private void fillExpEffectList(List<IExpEffect> expEffects) {
		cbboxExpEffectList.setItems(FXCollections.observableArrayList(expEffects));
	}
	private void fillExpSubjectInfos(MonitoredSystem ms) {
		Map<String, Set<AVar>> map = ms.getMap();
		for(String category : map.keySet()) {
			VBox vb = new VBox();
			acrdnExpSubject.getPanes().add(new TitledPane(category, vb));

			for(AVar av : map.get(category))
				vb.getChildren().add(new Label(av.getLabel() + " : " + new Label(av.getValue().toString())));
		}
	}

	/**
	 * Set Combobox entries for already selected effects to 'selectedResult' style, and others to 'notSelectedResult'
	 * (typically, set selected results entries in a grey color, and let the others black)
	 */
	private void updateSelectedEffect() {
		cbboxExpEffectList.setCellFactory(
				new Callback<ListView<IExpEffect>, ListCell<IExpEffect>>() {
					@Override public ListCell<IExpEffect> call(ListView<IExpEffect> param) {
						final ListCell<IExpEffect> cell = new ListCell<IExpEffect>() {
							@Override public void updateItem(IExpEffect item, boolean empty) {
								super.updateItem(item, empty);
								if(item != null) {
									setText(item.getName());
									getStyleClass().remove("selectedResult");
									getStyleClass().remove("notSelectedResult");
									getStyleClass().add(getJellyBeanControllers().contains(item)?"selectedResult":"notSelectedResult");
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

		// Verify if JellyBeanController already created (this result is already selected)
		IExpEffect choice = cbboxExpEffectList.getValue();
		if(getJellyBeanControllers().contains(choice))
			return;

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jellyBean.fxml"));
			paneExpEffects.getChildren().add(fxmlLoader.load());
			JellyBeanController jb2 = fxmlLoader.getController();
			//fxmlLoader.setController(jb);
			jb2.setExpEffect(choice);
			jb2.setMainController(this);

			jellyBeanControllers.add(jb2);
			updateSelectedEffect();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private HashSet<JellyBeanController> getJellyBeanControllers() {
		return jellyBeanControllers;
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

	public void removeEffectNode(JellyBeanController jbc) {
		jellyBeanControllers.remove(jbc);
		paneExpEffects.getChildren().remove(jbc.getNode());
		updateSelectedEffect();
	}
}

