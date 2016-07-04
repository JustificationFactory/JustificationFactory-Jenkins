package fr.axonic.avek.gui.view.expEffects;

import fr.axonic.avek.gui.model.IExpEffect;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cduffau on 02/07/16.
 */
public class JellyBeansSelector extends HBox {

	@FXML
	private Button addJellyBeanButton;
	@FXML
	private FlowPane jellyBeansPane;
	@FXML
	private ComboBox<IExpEffect> comboBoxJellyBean;

	private Set<IExpEffect> addedEffects;

	public JellyBeansSelector() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/expEffects/jellyBeansSelector.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	protected void initialize() {
		addedEffects = new HashSet<>();
		updateJellyBeanChoice();
	}

	@FXML
	void onAddJellyBeanClicked(ActionEvent event) {

		// Verify if JellyBean already created (this result is already selected)
		IExpEffect choice = comboBoxJellyBean.getValue();
		if (addedEffects.contains(choice))
			return;

		try {

			JellyBean jb2 = new JellyBean();
			jb2.setExpEffect(choice);
			jb2.setMainController(this);
			jellyBeansPane.getChildren().add(jb2);
			addedEffects.add(jb2.getExpEffect());
			updateJellyBeanChoice();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onNewExpEffectClicked(ActionEvent event) {
		addJellyBeanButton.setDisable(true);
	}

	/**
	 * Set Combobox entries for already selected effects to 'selectedResult' style, and others to 'notSelectedResult'
	 * (typically, set selected results entries in a grey color, and let the others black)
	 */
	private void updateJellyBeanChoice() {
		comboBoxJellyBean.setCellFactory(
				new Callback<ListView<IExpEffect>, ListCell<IExpEffect>>() {
					@Override
					public ListCell<IExpEffect> call(ListView<IExpEffect> param) {
						final ListCell<IExpEffect> cell = new ListCell<IExpEffect>() {
							@Override
							public void updateItem(IExpEffect item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null) {
									setText(item.getName());
									getStyleClass().remove("selectedResult");
									getStyleClass().remove("notSelectedResult");
									getStyleClass().add(addedEffects.contains(item) ? "selectedResult" : "notSelectedResult");
								}
							}
						};
						return cell;
					}
				});
	}

	public void removeJellyBean(JellyBean jbc) {
		addedEffects.remove(jbc.getExpEffect());
		jellyBeansPane.getChildren().remove(jbc);
		updateJellyBeanChoice();
	}

	public void setJellyBeansChoice(ObservableList<IExpEffect> items) {
		this.comboBoxJellyBean.setItems(items);
	}
}
