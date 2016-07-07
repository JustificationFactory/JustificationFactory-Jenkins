package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.gui.model.results.IColorState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {

	@FXML
	private Button jbLabel;
	@FXML
	private Button jbCross;

	private Class<? extends IColorState> enumType;
	private IColorState expEffect;
	private JellyBeansSelector mainController;

	public JellyBean() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/results/jellyBean.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {}
	}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		mainController.removeJellyBean(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		IColorState[] constants = enumType.getEnumConstants();

		ArrayList<IColorState> list = new ArrayList<>();
		Collections.addAll(list, constants);

		int nextIndex = ( list.indexOf(expEffect) + 1 )%list.size();
		expEffect = list.get(nextIndex);

		refreshColor();
	}

	private String getColorString(Color c) {
		return String.format("#%02X%02X%02X",
				(int) (c.getRed() * 255),
				(int) (c.getGreen() * 255),
				(int) (c.getBlue() * 255));
	}

	private void refreshColor() {
		jbLabel.setStyle("-fx-background-color: " + getColorString(expEffect.getColor())
				+ "; -fx-border-color: " + getColorString(expEffect.getColor().darker()));
		jbCross.setStyle("-fx-background-color: " + getColorString(expEffect.getColor())
				+ "; -fx-border-color: " + getColorString(expEffect.getColor().darker()));
	}


	public IColorState getState() {
		return expEffect;
	}

	public void setStateType(Class<? extends IColorState> stateType) {
		enumType = stateType;
		expEffect = enumType.getEnumConstants()[0];
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
