package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.structure.ExperimentationResults;
import fr.axonic.avek.gui.view.parameters.ParametersPane;
import fr.axonic.avek.gui.view.results.JellyBeansSelector;
import fr.axonic.avek.gui.view.subjects.ExpSubject;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class MainController {
	private final static Logger logger = Logger.getLogger(MainController.class);
	private final static URL GUI_FXML
			= MainController.class.getClassLoader().getResource("fxml/gui.fxml");
	private static Parent root;

	@FXML
	private Button btnStrategy;
	@FXML
	private ParametersPane paneParameters;
	@FXML
	private ExpSubject expSubject;
	@FXML
	private JellyBeansSelector jellyBeansSelector;

	private String getFileContent(String path) throws IOException, URISyntaxException {
		String res = "";
		File f = new File(getClass().getClassLoader()
				.getResource(path).toURI());
		List<String> ls = Files.readAllLines(f.toPath());
		for (String s : ls)
			res += s;

		return res;
	}

	@FXML
	protected void initialize() throws Exception {
		// TODO MOCK ONLY ↓↓↓  ////////////////////////////////////////////////
		String monitoredSystemJson = getFileContent("files/subjectFile.json");
		String results = getFileContent("files/resultEnum1.json");
		String expParameters = getFileContent("files/parametersFile.json");
		// TODO MOCK ONLY ↑↑↑ ////////////////////////////////////////////////

		ExperimentationResults expr = new Jsonifier<>(ExperimentationResults.class).fromJson(results);
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(expParameters);

		for (AEntity ae : list.getEntities())
			paneParameters.addExpParameter(ae);

		// Fill experiment subject informations
		expSubject.setMonitoredSystem(MonitoredSystem.fromJson(monitoredSystemJson));

		// Fill experiment sample list
		jellyBeansSelector.setJellyBeansChoice(FXCollections.observableArrayList(expr.getList()));
	}

	@FXML
	void onClicStrategyButton(ActionEvent event) {
		btnStrategy.setDisable(true);
	}

	public static Parent getRoot() throws IOException {
		if (root == null) {
			logger.debug("Loading gui.fxml");
			root = FXMLLoader.load(GUI_FXML);
		}
		return root;
	}
}

