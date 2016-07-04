package fr.axonic.avek.gui.view;
import fr.axonic.avek.gui.model.IExpEffect;
import fr.axonic.avek.gui.model.StringExpEffect;
import fr.axonic.avek.gui.view.jellyBean.JellyBean;
import fr.axonic.avek.gui.view.jellyBean.JellyBeansSelector;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import fr.axonic.avek.model.base.AVar;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class MainController {

	@FXML
	private Button btnStrategy;
	@FXML
	private VBox paneExpParameters;
	@FXML
	private Button btnHistory;
	@FXML
	private JellyBeansSelector jellyBeansSelector;
	@FXML
	private Accordion acrdnExpSubject;

	// All ExpEffect JellyBean's controllers
	// Used to know what effect is selected
	private HashSet<JellyBean> jellyBeen;

	@FXML
	protected void initialize() {
		jellyBeen = new HashSet<>();

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
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
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

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		showPopUp("Strategy !");
	}

	private HashSet<JellyBean> getJellyBeen() {
		return jellyBeen;
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

