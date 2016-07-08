package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.model.results.ExempleState;
import fr.axonic.avek.gui.model.results.ExempleStateBool;
import fr.axonic.avek.gui.model.results.ExpEffect;
import fr.axonic.avek.gui.view.parameters.ExpParameters;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.gui.view.subjects.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
		List<ExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			expEffects.add(new ExpEffect(ExempleState.class, "ES1-"+i));
			expEffects.add(new ExpEffect(ExempleStateBool.class, "ESB-"+i));
			expEffects.add(new ExpEffect(ExempleState.class, "ES2-"+i));
		}
		// end MOCK ONLY ↑↑↑


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

