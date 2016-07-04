package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.model.IExpEffect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBeanController {
	@FXML
	private HBox jellyBean;

	@FXML
	private Button jbLabel;

	private MainController mainController;
	private IExpEffect expEffect;

	@FXML
	protected void initialize() {}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		mainController.removeEffectNode(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		// TODO
	}


	public IExpEffect getExpEffect() {
		return expEffect;
	}


	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void setExpEffect(IExpEffect expEffect) {
		this.expEffect = expEffect;
		jbLabel.setText(expEffect.getName());
	}

	public HBox getNode() {
		return jellyBean;
	}
}
