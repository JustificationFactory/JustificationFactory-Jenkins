package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.view.parameters.list.ParametersGroup;
import fr.axonic.avek.model.base.engine.AEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
	@FXML private ParametersGroup paneParameters;
	@FXML private BorderPane complementaryFilePane;
	@FXML private BorderPane additionalInfoPane;
	@FXML private SplitPane horizontalSplit;

	public ParametersPane() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(PARAMPANE_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading parametersPane.fxml");
		fxmlLoader.load();
	}

	@FXML
	public void onHideComplementaryFiles(MouseEvent event) {
		boolean b = complementaryFilePane.isVisible();
		complementaryFilePane.setVisible(!b);
		complementaryFilePane.setManaged(!b);
		if(b) {
			horizontalSplit.getItems().remove(complementaryFilePane);
			complementaryFilePane.setVisible(false);
			complementaryFilePane.setManaged(false);
		} else {
			complementaryFilePane.setVisible(true);
			complementaryFilePane.setManaged(true);
			horizontalSplit.getItems().add(complementaryFilePane);
		}
	}

	@FXML
	public void onHideAdditionalInfo(MouseEvent event) {
		boolean b = additionalInfoPane.isVisible();

		if(b) {
			this.getItems().remove(additionalInfoPane);
			additionalInfoPane.setVisible(false);
			additionalInfoPane.setManaged(false);
		} else {
			additionalInfoPane.setVisible(true);
			additionalInfoPane.setManaged(true);
			this.getItems().add(additionalInfoPane);
		}
	}

	public void addExpParameter(AEntity value) {
		paneParameters.addParameter(value);
	}
}
