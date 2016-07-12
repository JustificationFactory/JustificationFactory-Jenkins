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
public class ParametersPane extends GridPane {
	public static final URL PARAMETERSPANE_FXML = ParametersPane.class.getClassLoader().getResource("fxml/parameters/parametersPane.fxml");

	private List<ExpParameter> expParameters;

	public ParametersPane() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(PARAMETERSPANE_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		expParameters = new ArrayList<>();
		expParameters.add(null);
	}

	public boolean addParameter(AVar value) {
		int i = expParameters.size();

		ExpParameter epm = new ExpParameter(value);

		for(Control c : epm.getElements()) {
			GridPane.setRowIndex(c, i);
			this.getChildren().add(c);
		}

		return expParameters.add(epm);
	}

	public synchronized ExpParameter rmParameter(String name) {
		// searching parameter index
		int i = -1;

		// int j=0+1 because of title line at index 0
		for (int j = 1; j < expParameters.size(); j++)
			if (expParameters.get(j).getName().equals(name)) {
				i = j;
				break;
			}

		if(i==-1)
			return null;

		// Removing parameter from view
		for(Control c : expParameters.get(i).getElements()) {
			c.setDisable(true);
			this.getChildren().remove(c);
		}

		// Decal following parameters
		for(int j=i+1; j<expParameters.size(); j++)
			for(Control c : expParameters.get(j).getElements())
				GridPane.setRowIndex(c, j-1);

		// Removing parameter from list
		return expParameters.remove(i);
	}
}
