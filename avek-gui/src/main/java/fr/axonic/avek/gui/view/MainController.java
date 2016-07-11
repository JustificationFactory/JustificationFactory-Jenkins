package fr.axonic.avek.gui.view;

import com.google.gson.Gson;
import fr.axonic.avek.gui.model.results.ExempleState;
import fr.axonic.avek.gui.model.results.ExpEffect;
import fr.axonic.avek.gui.view.parameters.ExpParameters;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.gui.view.subjects.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MainController {

	@FXML
	private Button btnStrategy;
	@FXML
	private VBox paneExpParameters;
	@FXML
	private ExpSubject expSubject;
	@FXML
	private JellyBeansSelector jellyBeansSelector;

	@FXML
	protected void initialize() throws VerificationException, IOException {
		String monitoredSystemJson = "";
		try {
			File f = new File(getClass().getClassLoader()
					.getResource("files/subjectFile.json").toURI());
			List<String> ls = Files.readAllLines(f.toPath());
			for(String s : ls)
				monitoredSystemJson += s;
		} catch (IOException | URISyntaxException e) {}

		// TODO MOCK ONLY ↓↓↓
		List<ExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			String s = "";
			{
				ExempleState val = ExempleState.values()[0];
				AEnum<ExempleState> aEnum = new AEnum(val);
				aEnum.setEnumsRange(ExempleState.values());
				//aEnum.setDefaultValue(ExempleState.MEDIUM);
				s = new Gson().toJson(aEnum);
			}
			AEnum ae = new Gson().fromJson(s, AEnum.class);

			expEffects.add(new ExpEffect(ae, "AE"+i));
		}

		// end MOCK ONLY ↑↑↑


		// Fill experiment subject informations
		expSubject.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));

		// Fill experiment results list
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));

		addParameter();
		addParameter();
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}


	private void addParameter() throws IOException {
		ExpParameters expp = new ExpParameters();
		expp.setParamName("Frequency");
		expp.setParamUnit("GHz");
		expp.setParamValue("42");
		paneExpParameters.getChildren().add(expp);
	}
}

