package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.model.base.AEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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

	private AEnum enumType;
	private Object expEffect;
	private JellyBeansSelector mainController;

	public JellyBean() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/results/jellyBean.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		mainController.removeJellyBean(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		List list = enumType.getEnumsRange();

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
		List enumRange = enumType.getEnumsRange();
		double pct = ((double)enumRange.indexOf(expEffect))/enumRange.size();

		Color cc = Color.ALICEBLUE;

		if(pct >= 0)
			cc = Color.RED.interpolate(Color.GREEN, pct);

		jbLabel.setStyle("-fx-background-color: " + getColorString(cc)
				+ "; -fx-border-color: " + getColorString(cc.darker()));
		jbCross.setStyle("-fx-background-color: " + getColorString(cc)
				+ "; -fx-border-color: " + getColorString(cc.darker()));
	}


	public Object getState() {
		return expEffect;
	}

	public void setStateType(AEnum stateType) {
		enumType = stateType;
		expEffect = stateType.getValue();
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
