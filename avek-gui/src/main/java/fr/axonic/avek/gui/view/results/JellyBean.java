package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.gui.model.structure.AState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {
	public static final URL JELLYBEAN_FXML
			= JellyBean.class.getClassLoader().getResource("fxml/results/jellyBean.fxml");
	public static final String JELLYBEAN_CSS = "css/results/jellyBean.css";

	@FXML private Button jbLabel;
	@FXML private Button jbCross;

	private AState enumType;
	private Object expEffect;
	private JellyBeansSelector mainController;

	public JellyBean() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(JELLYBEAN_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		this.getStylesheets().add(JELLYBEAN_CSS);
		this.getStylesheets().add("css/results/personalized.css");
	}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		mainController.removeJellyBean(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		Object beforeEffect = expEffect;

		List list = enumType.getRange();

		int nextIndex = ( list.indexOf(expEffect) + 1 )%list.size();
		expEffect = list.get(nextIndex);

		refreshColor(beforeEffect.toString(), expEffect.toString());
	}

	private void refreshColor(String before, String after) {
		Platform.runLater(() -> {
			jbLabel.getStyleClass().remove(before);
			jbCross.getStyleClass().remove(before);
			jbLabel.getStyleClass().add(after);
			jbCross.getStyleClass().add(after);
		});
	}


	public Object getState() {
		return expEffect;
	}

	public void setStateType(AState stateType) {
		enumType = stateType;
		expEffect = stateType.getValue();
		refreshColor("", expEffect.toString());
	}
	public void setText(String text) {
		jbLabel.setText(text);
	}
	public String getText() { return jbLabel.getText(); }

	public void setMainController(JellyBeansSelector mainController) {
		this.mainController = mainController;
	}

	@Override
	public String toString() {
		return "JellyBean=" + getState();
	}
}
