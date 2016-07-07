package fr.axonic.avek.gui.view;

import com.google.gson.Gson;
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
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

		String monitoredSystemJson = "";
		try {
			File f = new File(getClass().getClassLoader()
					.getResource("files/subjectFile.json").toURI());
			List<String> ls = Files.readAllLines(f.toPath());
			for(String s : ls)
				monitoredSystemJson += s;
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

		// TODO MOCK ONLY ↓↓↓
		/*{
			MonitoredSystem ms = new MonitoredSystem(42);
			ms.addCategory("Static");

			ms.addAVar("Static", new ANumber("Id", 42));
			ms.addAVar("Static", new ANumber("Age", 25));
			ms.addAVar("Static", new ANumber("Sex", 1));

			ms.addCategory("Pathologic");
			ms.addAVar("Pathologic", new AString("Pathology", "OVERWEIGHT"));
			ms.addAVar("Pathologic", new ADate("Beginning", new Date()));
			ms.addAVar("Pathologic", new AString("Type", "Gynoid"));

			ms.addCategory("Dynamic");
			ms.addAVar("Dynamic", new ANumber("Size", 123.45));
			ms.addAVar("Dynamic", new ANumber("Weight", 67));
			ms.addAVar("Dynamic", new ANumber("IMC", 67d/(1.2345*1.2345)));

			monitoredSystemJson = ms.toJson();
			System.out.println(monitoredSystemJson);
		}*/


		List<IExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 30; i++)
			expEffects.add(i % 3 == 0 ? new StringExpEffect("SEffect " + i) :
					i % 3 == 1 ? new BooleanExpEffect("BEffect " + i) :
							new EnumExpEffect("EEffect " + i));
		// end MOCK ONLY ↑↑↑


		// Fill experiment subject informations
		expSubject.setData(MonitoredSystem.fromJson(monitoredSystemJson));

		// Fill experiment effects list
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}
}

