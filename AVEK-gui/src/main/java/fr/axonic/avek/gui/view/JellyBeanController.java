package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.model.IResultElement;
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

	private Controller mainController;
	private IResultElement resultElement;

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


	public IResultElement getIEffect() {
		return resultElement;
	}


	public void setMainController(Controller mainController) {
		this.mainController = mainController;
	}

	public void setResultElement(IResultElement resultElement) {
		this.resultElement = resultElement;
		System.out.println(jbLabel);
		jbLabel.setText(resultElement.getName());
	}

	public HBox getNode() {
		return jellyBean;
	}
}
