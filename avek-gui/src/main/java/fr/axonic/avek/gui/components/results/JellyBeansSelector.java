package fr.axonic.avek.gui.components.results;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.structure.ExpEffect;
import fr.axonic.avek.gui.model.structure.ExperimentationResults;
import fr.axonic.avek.gui.util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cduffau on 02/07/16.
 */
public class JellyBeansSelector extends VBox {
	private static final Logger logger = Logger.getLogger(JellyBeansSelector.class);
	private static final URL FXML
			= JellyBeansSelector.class.getClassLoader().getResource("fxml/components/JellyBeansSelector.fxml");
	private static final String CSS = "css/results/jellyBeanSelector.css";

	@FXML
	private Button newExpEffectButton;
	@FXML
	private FlowPane jellyBeansPane;
	@FXML
	private ComboBox<ExpEffect> comboBoxJellyBean;

	private final Set<String> addedEffects;

	// should be public
	public JellyBeansSelector() {
		addedEffects = new HashSet<>();


		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading JellyBeanSelector... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("JellyBeanSelector loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for JellyBeanSelector", e);
		}

		this.getStylesheets().add(CSS);
		logger.info("Added css for jellyBeanSelector");


		// Fill experiment sample list
		String results = Util.getFileContent("files/resultEnum1.json");
		ExperimentationResults expr = new Jsonifier<>(ExperimentationResults.class).fromJson(results);
		setJellyBeansChoice(FXCollections.observableArrayList(expr.getList()));
	}

	@FXML
	protected void initialize() {
		updateJellyBeanChoice();
	}

	@FXML
	void onSelectorHidding(Event event) {
		addJellyBean();
	}

	@FXML
	void onNewExpEffectClicked(ActionEvent event) {
		newExpEffectButton.setDisable(true);
	}

	/**
	 * Set Combobox entries for already selected sample to 'selectedResult' style, and others to 'notSelectedResult'
	 * (typically, set selected sample entries in a grey color, and let the others black)
	 */
	private void updateJellyBeanChoice() {
		comboBoxJellyBean.setCellFactory(
				new Callback<ListView<ExpEffect>, ListCell<ExpEffect>>() {
					@Override
					public ListCell<ExpEffect> call(ListView<ExpEffect> param) {
						return new ListCell<ExpEffect>() {
							@Override
							public void updateItem(ExpEffect item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null) {
									setText(item.getName());
									getStyleClass().remove("selectedResult");
									getStyleClass().remove("notSelectedResult");
									getStyleClass().add(addedEffects.contains(item.getName()) ? "selectedResult" : "notSelectedResult");
								}
							}
						};
					}
				});
	}

	private void addJellyBean() {
		// Verify if JellyBean already created (this result is already selected)
		ExpEffect choice = comboBoxJellyBean.getValue();
		if (choice == null) {
			logger.warn("Choice is null");
			return;
		} else if (addedEffects.contains(choice.getName())) {
			logger.warn("Already selected: " + choice);
			return;
		}

		JellyBean jb2 = new JellyBean();
		jb2.setStateType(choice.getStateClass());
		jb2.setText(choice.getName());
		jb2.setMainController(this);
		jellyBeansPane.getChildren().add(jb2);
		addedEffects.add(choice.getName());
		updateJellyBeanChoice();
	}

	void removeJellyBean(JellyBean jbc) {
		addedEffects.remove(jbc.getText());
		jellyBeansPane.getChildren().remove(jbc);
		updateJellyBeanChoice();
	}

	public void setJellyBeansChoice(ObservableList<ExpEffect> items) {
		this.comboBoxJellyBean.setItems(items);
	}
}
