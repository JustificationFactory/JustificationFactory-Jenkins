package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.FormatType;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaël N on 13/07/16.
 */
public class ParametersGridPane extends GridPane {

	List<IExpParameter> expParameters;

	ParametersGridPane() {
		expParameters = new ArrayList<>();
	}

	public boolean addExpParameter(AList<AVar> alist) {
		int i = expParameters.size();

		ExpParameterGroup epg =	new ExpParameterGroup(alist);

		// Adding graphical elements to the GUI
		for (Control c : epg.getElements()) {
			GridPane.setRowIndex(c, i);
			this.getChildren().add(c);
		}

		// Adding to the list
		return expParameters.add(epg);
	}
	public boolean addExpParameter(AVar avar) {
		int i = expParameters.size();

		ExpParameter ep =	new ExpParameter(avar);

		// Adding graphical elements to the GUI
		for (Control c : ep.getElements()) {
			GridPane.setRowIndex(c, i);
			this.getChildren().add(c);
		}

		// Adding to the list
		return expParameters.add(ep);
	}

	public synchronized IExpParameter rmParameter(String name) {
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
