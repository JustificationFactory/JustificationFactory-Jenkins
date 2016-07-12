package fr.axonic.avek.gui.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import fr.axonic.avek.gui.model.results.AState;
import fr.axonic.avek.gui.model.results.ExampleState;
import fr.axonic.avek.gui.model.results.ExpEffect;
import fr.axonic.avek.gui.model.results.State;
import fr.axonic.avek.gui.view.parameters.ParametersPane;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.gui.view.subjects.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ABoolean;
import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.ARangedEnum;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {
	private final static URL GUI_FXML
			= MainController.class.getClassLoader().getResource("fxml/gui.fxml");
	private static Parent root;

	@FXML private Button btnStrategy;
	@FXML private ParametersPane paneExpParameters;
	@FXML private ExpSubject expSubject;
	@FXML private JellyBeansSelector jellyBeansSelector;

	private String getFileContent(String path) throws IOException, URISyntaxException {
		String res = "";
		File f = new File(getClass().getClassLoader()
				.getResource(path).toURI());
		List<String> ls = Files.readAllLines(f.toPath());
		for(String s : ls)
			res += s;

		return res;
	}

	@FXML
	protected void initialize() throws Exception {
		// TODO MOCK ONLY ↓↓↓  ////////////////////////////////////////////////
		String monitoredSystemJson = getFileContent("files/subjectFile.json");

		List<ExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			String aRangedEnumJson = getFileContent("files/resultEnum1.json");
			GsonBuilder gsonBuilder=new GsonBuilder().registerTypeAdapter(State.class,new EnumAdapter<>(State.class));
			AState be = gsonBuilder.create().fromJson(aRangedEnumJson, AState.class);
			expEffects.add(new ExpEffect(be, "AE"+i));
		}

		paneExpParameters.addParameter(new ANumber("Frequency", 42.0));
		paneExpParameters.addParameter(new ABoolean("Bool?", true));
		paneExpParameters.addParameter(new ANumber("Times redo", 12));
		// TODO MOCK ONLY ↑↑↑ ////////////////////////////////////////////////


		// Fill experiment subject informations
		expSubject.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));

		// Fill experiment results list
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}


	public static Parent getRoot() throws IOException {
		if(root == null)
			root = FXMLLoader.load(GUI_FXML);
		return root;
	}
}

