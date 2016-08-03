package fr.axonic.avek.gui.components.jellyBeans;

import fr.axonic.base.ARangedEnum;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cduffau on 02/07/16.
 */
public class JellyBeanSelector extends VBox {
	private static final Logger logger = Logger.getLogger(JellyBeanSelector.class);
	private static final URL FXML
			= JellyBeanSelector.class.getClassLoader().getResource("fxml/components/JellyBeansSelector.fxml");
	private static final String CSS = "css/components/jellyBeanSelector.css";

	@FXML
	private Button newExpEffectButton;
	@FXML
	private JellyBeanPane jellyBeanPane;
	@FXML
	private ComboBox<Map.Entry<String, List<String>>> comboBoxJellyBean;

	// should be public
	public JellyBeanSelector() {
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
		logger.info("Added css for JellyBeanSelector");
	}

	@FXML
	protected void initialize() {
		jellyBeanPane.onRemoveJellyBean(this::onRemoveJellyBean);
		jellyBeanPane.setJellyBeansStateEditable(true);
		updateJellyBeanChoice();
	}

	@FXML
	void onSelectorHidding(Event event) {
		addJellyBean();
	}

	private void addJellyBean() {
		// Verify if JellyBean already created (this result is already selected)
		Map.Entry<String, List<String>> choice = comboBoxJellyBean.getValue();
		if (choice == null) {
			logger.warn("Choice is null");
			return;
		}
		if (jellyBeanPane.contains(choice.getKey())) {
			logger.warn("Choice already added: "+choice.getKey());
			return;
		}
		jellyBeanPane.addJellyBean(choice.getKey(), choice.getValue());
		updateJellyBeanChoice();
	}
	private void onRemoveJellyBean(String effectName) {
		jellyBeanPane.remove(effectName);
		updateJellyBeanChoice();
	}

	/**
	 * Set Combobox entries for already selected sample to 'selectedResult' style, and others to 'notSelectedResult'
	 * (typically, set selected sample entries in a grey color, and let the others black)
	 */
	private void updateJellyBeanChoice() {
		comboBoxJellyBean.setCellFactory(
				new Callback<ListView<Map.Entry<String, List<String>>>, ListCell<Map.Entry<String, List<String>>>>() {
					@Override
					public ListCell<Map.Entry<String, List<String>>> call(ListView<Map.Entry<String, List<String>>> param) {
						return new ListCell<Map.Entry<String, List<String>>>() {
							@Override
							public void updateItem(Map.Entry<String, List<String>> item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null) {
									setText(item.getKey());
									getStyleClass().remove("selectedResult");
									getStyleClass().remove("notSelectedResult");
									getStyleClass().add(jellyBeanPane.contains(item.getKey()) ? "selectedResult" : "notSelectedResult");
								}
							}
						};
					}
				});
	}

	public void setJellyBeansChoice(Map<String, List<String>> exprMap) {
		this.comboBoxJellyBean.setItems(
				FXCollections.observableArrayList(new ArrayList<>(exprMap.entrySet())));
	}

	public JellyBeanPane getJellyBeanPane() {
		return jellyBeanPane;
	}
}
