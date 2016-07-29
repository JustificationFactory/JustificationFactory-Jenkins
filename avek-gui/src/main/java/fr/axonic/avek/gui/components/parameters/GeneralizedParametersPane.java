
package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.components.filelist.FileListView;
import fr.axonic.avek.gui.components.parameters.list.parametersGroup.GeneralizedParametersRoot;
import fr.axonic.base.engine.AEntity;
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
public class GeneralizedParametersPane extends SplitPane {
	private final static Logger logger = Logger.getLogger(GeneralizedParametersPane.class);

	private static final URL FXML
			= GeneralizedParametersPane.class.getClassLoader().getResource("fxml/components/GeneralizedParametersPane.fxml");

	@FXML
	private TextArea textfieldComplements;
	@FXML
	private FileListView uploadedFiles;
	@FXML
	private GeneralizedParametersRoot parametersRoot;
	@FXML
	private BorderPane complementaryFilePane;
	@FXML
	private BorderPane additionalInfoPane;
	@FXML
	private SplitPane horizontalSplit;

	// should be public
	public GeneralizedParametersPane() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading GeneralizedParametersPane... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("GeneralizedParametersPane loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for GeneralizedParametersPane", e);
		}
	}

	@FXML
	void onHideComplementaryFiles(MouseEvent event) {
		boolean b = complementaryFilePane.isVisible();
		complementaryFilePane.setVisible(!b);
		complementaryFilePane.setManaged(!b);
		if (b) {
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
	void onHideAdditionalInfo(MouseEvent event) {
		boolean b = additionalInfoPane.isVisible();

		if (b) {
			this.getItems().remove(additionalInfoPane);
			additionalInfoPane.setVisible(false);
			additionalInfoPane.setManaged(false);
		} else {
			additionalInfoPane.setVisible(true);
			additionalInfoPane.setManaged(true);
			this.getItems().add(additionalInfoPane);
		}
	}

	BorderPane getComplementaryFilePane() {
		return complementaryFilePane;
	}
	BorderPane getAdditionalInfoPane() {
		return additionalInfoPane;
	}

	public void addExpParameter(AEntity value) {
		parametersRoot.addParameter(value);
	}
}
