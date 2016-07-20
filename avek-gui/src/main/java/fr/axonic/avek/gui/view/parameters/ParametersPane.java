package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.view.parameters.list.ParametersCategory;
import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Nathaël N on 18/07/16.
 */
public class ParametersPane extends SplitPane {
	private final static Logger logger = Logger.getLogger(ParametersPane.class);

	private static final URL PARAMPANE_FXML
			= ParametersPane.class.getClassLoader().getResource("fxml/parameters/parameters.fxml");

	@FXML private TextArea textfieldComplements;
	@FXML private FileListView uploadedFiles;
	@FXML private ParametersCategory paneParameters;

	public ParametersPane() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(PARAMPANE_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading parametersPane.fxml");
		fxmlLoader.load();
	}

	public void addParamCategory(AList list) {
		paneParameters.addParamCategory(list);
	}
	public void addExpParameter(AVar value) {
		paneParameters.addExpParameter(value);
	}
}
