package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.model.base.AVar;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ParametersPane extends GridPane {

	private List<ExpParameter> expParameters;

	public ParametersPane() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/parameters/parametersPane.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		expParameters = new ArrayList<>();
		expParameters.add(null);
	}

	public void addParameter(AVar value) {
		int i = expParameters.size();

		ExpParameter epm = new ExpParameter(value);

		for(Control c : epm.getElements()) {
			GridPane.setRowIndex(c, i);
			this.getChildren().add(c);
		}

		expParameters.add(epm);
	}

	public synchronized void rmParameter(String name) {
		// searching parameter index
		int i = -1;

		// int j=0+1 because of title line at index 0
		for (int j = 1; j < expParameters.size(); j++)
			if (expParameters.get(j).getName().equals(name)) {
				i = j;
				break;
			}

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
		expParameters.remove(i);
	}
}
