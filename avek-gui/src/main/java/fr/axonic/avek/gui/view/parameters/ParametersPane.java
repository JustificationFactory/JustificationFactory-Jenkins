package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.model.base.engine.AVar;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ParametersPane extends ParametersGridPane implements IExpParameterContainer {
	public static final URL PARAMETERSPANE_FXML = ParametersPane.class.getClassLoader().getResource("fxml/parameters/parametersPane.fxml");

	public ParametersPane() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(PARAMETERSPANE_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		expParameters.add(null);
	}
}
