package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ARangedEnum;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {

	@FXML
	private Button jbLabel;
	@FXML
	private Button jbCross;

	private ARangedEnum enumType;
	private Object expEffect;
	private JellyBeansSelector mainController;

	public JellyBean() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/results/jellyBean.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();

		this.getStylesheets().add("css/results/jellyBean.css");
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

	public void setStateType(ARangedEnum stateType) {
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
