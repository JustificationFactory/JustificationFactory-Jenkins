package fr.axonic.avek.gui.view;

import com.google.gson.Gson;
import fr.axonic.avek.gui.model.results.ExampleState;
import fr.axonic.avek.gui.model.results.ExpEffect;
import fr.axonic.avek.gui.view.parameters.ParametersPane;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.gui.view.subjects.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ABoolean;
import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainController {

	private Pane root;

	@FXML
	private Button btnStrategy;
	@FXML
	private ParametersPane paneExpParameters;
	@FXML
	private ExpSubject expSubject;
	@FXML
	private JellyBeansSelector jellyBeansSelector;

	@FXML
	protected void initialize() throws VerificationException, IOException, ExecutionException, InterruptedException {
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
				ExampleState val = ExampleState.values()[0];
				ARangedEnum<ExampleState> aEnum = new ARangedEnum(val);
				aEnum.setRange(Arrays.asList(ExampleState.values()));
				//aEnum.setDefaultValue(ExampleState.MEDIUM);
				s = new Gson().toJson(aEnum);
			}
			ARangedEnum ae = new Gson().fromJson(s, ARangedEnum.class);

			expEffects.add(new ExpEffect(ae, "AE"+i));
		}

		paneExpParameters.addParameter(new ANumber("Frequency", 42.0));
		paneExpParameters.addParameter(new ABoolean("Bool?", true));
		paneExpParameters.addParameter(new ANumber("Times redo", 12));
		// TODO MOCK ONLY ↑↑↑


		// Fill experiment subject informations
		expSubject.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));

		// Fill experiment results list
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}
}

