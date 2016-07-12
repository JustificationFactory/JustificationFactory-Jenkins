package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.gui.model.results.ExpEffect;
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

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cduffau on 02/07/16.
 */
public class JellyBeansSelector extends VBox {
	public static final URL JBSELECTOR_FXML
			= JellyBeansSelector.class.getClassLoader().getResource("fxml/results/jellyBeansSelector.fxml");
	public static final String JBSELECTOR_CSS = "css/results/jellyBeanSelector.css";

	@FXML private Button newExpEffectButton;
	@FXML private FlowPane jellyBeansPane;
	@FXML private ComboBox<ExpEffect> comboBoxJellyBean;

	private Set<String> addedEffects;

	public JellyBeansSelector() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(JBSELECTOR_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		this.getStylesheets().add(JBSELECTOR_CSS);
	}

	@FXML
	protected void initialize() {
		addedEffects = new HashSet<>();
		updateJellyBeanChoice();
	}

	@FXML
	void onSelectorHidding(Event event) throws IOException {
		addJellyBean();
	}

	@FXML
	void onNewExpEffectClicked(ActionEvent event) {
		newExpEffectButton.setDisable(true);
	}

	/**
	 * Set Combobox entries for already selected results to 'selectedResult' style, and others to 'notSelectedResult'
	 * (typically, set selected results entries in a grey color, and let the others black)
	 */
	private void updateJellyBeanChoice() {
		comboBoxJellyBean.setCellFactory(
				new Callback<ListView<ExpEffect>, ListCell<ExpEffect>>() {
					@Override
					public ListCell<ExpEffect> call(ListView<ExpEffect> param) {
						final ListCell<ExpEffect> cell = new ListCell<ExpEffect>() {
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
						return cell;
					}
				});
	}

	private void addJellyBean() throws IOException {
		// Verify if JellyBean already created (this result is already selected)
		ExpEffect choice = comboBoxJellyBean.getValue();
		if (addedEffects.contains(choice.getName()))
			return;

		JellyBean jb2 = new JellyBean();
		jb2.setStateType(choice.getStateClass());
		jb2.setText(choice.getName());
		jb2.setMainController(this);
		jellyBeansPane.getChildren().add(jb2);
		addedEffects.add(choice.getName());
		updateJellyBeanChoice();
	}

	public void removeJellyBean(JellyBean jbc) {
		addedEffects.remove(jbc.getText());
		jellyBeansPane.getChildren().remove(jbc);
		updateJellyBeanChoice();
	}

	public void setJellyBeansChoice(ObservableList<ExpEffect> items) {
		this.comboBoxJellyBean.setItems(items);
	}
}
