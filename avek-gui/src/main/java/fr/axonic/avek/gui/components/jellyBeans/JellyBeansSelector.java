package fr.axonic.avek.gui.components.jellyBeans;

import fr.axonic.avek.gui.model.structure.ExperimentResult;
import fr.axonic.avek.gui.model.structure.ExperimentResultsMap;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
	private JellyBeanPane jellyBeanPane;
	@FXML
	private ComboBox<ExperimentResult> comboBoxJellyBean;

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
	}

	@FXML
	protected void initialize() {
		jellyBeanPane.onRemoveJellyBean(this::onRemoveJellyBean);
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

	private void addJellyBean() {
		// Verify if JellyBean already created (this result is already selected)
		ExperimentResult choice = comboBoxJellyBean.getValue();
		if (choice == null) {
			logger.warn("Choice is null");
			return;
		}
		if (addedEffects.contains(choice.getName())) {
			logger.warn("Choice already added: "+choice.getName());
			return;
		}
		jellyBeanPane.addJellyBean(comboBoxJellyBean.getValue());
		updateJellyBeanChoice();
		addedEffects.add(choice.getName());
	}
	private void onRemoveJellyBean(String effectName) {
		addedEffects.remove(effectName);
		updateJellyBeanChoice();
	}

	/**
	 * Set Combobox entries for already selected sample to 'selectedResult' style, and others to 'notSelectedResult'
	 * (typically, set selected sample entries in a grey color, and let the others black)
	 */
	private void updateJellyBeanChoice() {
		comboBoxJellyBean.setCellFactory(
				new Callback<ListView<ExperimentResult>, ListCell<ExperimentResult>>() {
					@Override
					public ListCell<ExperimentResult> call(ListView<ExperimentResult> param) {
						return new ListCell<ExperimentResult>() {
							@Override
							public void updateItem(ExperimentResult item, boolean empty) {
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

	public void setJellyBeansChoice(ExperimentResultsMap exprMap) {
		this.comboBoxJellyBean.setItems(
				FXCollections.observableArrayList(exprMap.getList()));
	}

	public JellyBeanPane getJellyBeanPane() {
		return jellyBeanPane;
	}
}
