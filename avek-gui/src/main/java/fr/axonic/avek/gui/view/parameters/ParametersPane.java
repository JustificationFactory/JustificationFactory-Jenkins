package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.model.base.AVar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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

	public void addParameter(AVar value) throws ExecutionException, InterruptedException {
		int i = expParameters.size();

		ExpParameter epm = new ExpParameter(value);

		final Set<FutureTask> tasks = new HashSet<>();

		for(Control c : epm.getElements()) {
			GridPane.setRowIndex(c, i);

			FutureTask ft = new FutureTask(() ->
					ParametersPane.this.getChildren().add(c));
			Platform.runLater(ft);
			tasks.add(ft);
		}

		expParameters.add(epm);

		// Wait for tasks
		/*for(FutureTask ft : tasks)
			ft.get();*/
	}

	public synchronized void rmParameter(String name) throws ExecutionException, InterruptedException {
		final Set<FutureTask> tasks = new HashSet<>();

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

			FutureTask ft = new FutureTask(() ->
					this.getChildren().remove(c));
			Platform.runLater(ft);
			tasks.add(ft);
		}

		// Decal following parameters
		for(int j=i+1; j<expParameters.size(); j++)
			for(Control c : expParameters.get(j).getElements())
				GridPane.setRowIndex(c, j-1);

		// Removing parameter from list
		expParameters.remove(i);

		// Wait for tasks
		/*for(FutureTask ft : tasks)
			ft.get();*/
	}
}
