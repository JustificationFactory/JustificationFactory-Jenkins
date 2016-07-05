package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.model.IExpEffect;
import fr.axonic.avek.gui.model.expEffects.BooleanExpEffect;
import fr.axonic.avek.gui.model.expEffects.EnumExpEffect;
import fr.axonic.avek.gui.model.expEffects.StringExpEffect;
import fr.axonic.avek.gui.view.expEffects.JellyBeansSelector;
import fr.axonic.avek.gui.view.expParameters.ExpParameters;
import fr.axonic.avek.gui.view.expSubject.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainController {

	@FXML
	private Button btnStrategy;
	@FXML
	private ExpParameters paneExpParameters;
	@FXML
	private ExpSubject expSubject;
	@FXML
	private JellyBeansSelector jellyBeansSelector;

	@FXML
	protected void initialize() {
		// TODO MOCK ONLY ↓↓↓
		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string", "strval1"));
		ms.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms.addAVar("Category 1", new ANumber("a double", -12345.6789));
		ms.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer", -987654321));
		ms.addAVar("Category 2", new ANumber("a double", 98765.4321));

		List<IExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 30; i++)
			expEffects.add(i % 3 == 0 ? new StringExpEffect("SEffect " + i) :
					i % 3 == 1 ? new BooleanExpEffect("BEffect " + i) :
							new EnumExpEffect("EEffect " + i));
		// end MOCK ONLY ↑↑↑


		// Fill experiment subject informations
		expSubject.setData(ms);

		// Fill experiment effects list
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}
}

