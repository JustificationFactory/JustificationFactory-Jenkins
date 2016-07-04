package fr.axonic.avek.gui.view.expEffects;

import fr.axonic.avek.gui.model.IExpEffect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {

	@FXML
	private Button jbLabel;
	@FXML
	private Button jbCross;

	private IExpEffect expEffect;
	private JellyBeansSelector mainController;

	public JellyBean() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/expEffects/jellyBean.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		mainController.removeJellyBean(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		expEffect.onClick();
		refreshColor();
	}
	private String getColorString(Color c) {
		return String.format("#%02X%02X%02X",
				(int)(c.getRed()*255),
				(int)(c.getGreen()*255),
				(int)(c.getBlue()*255));
	}
	private void refreshColor() {
		jbLabel.setStyle("-fx-background-color: " + getColorString(expEffect.getColor())
				+ "; -fx-border-color: " + getColorString(expEffect.getColor().darker()));
		jbCross.setStyle("-fx-background-color: " + getColorString(expEffect.getColor())
				+ "; -fx-border-color: " + getColorString(expEffect.getColor().darker()));
	}


	public IExpEffect getExpEffect() {
		return expEffect;
	}

	public void setExpEffect(IExpEffect expEffect) {
		this.expEffect = expEffect;
		jbLabel.setText(expEffect.getName());
	}

	public void setMainController(JellyBeansSelector mainController) {
		this.mainController = mainController;
	}
}
