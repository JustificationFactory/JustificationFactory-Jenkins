package fr.axonic.avek.gui.view.jellyBean;

import fr.axonic.avek.gui.model.IResultElement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox{

	@FXML
	private Button jbLabel;

	private JellyBeansSelector mainController;
	private IResultElement resultElement;

	public JellyBean(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jellyBean/jellyBean.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	protected void initialize() {}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		mainController.removeJellyBean(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		// TODO
	}


	public IResultElement getIEffect() {
		return resultElement;
	}


	public void setMainController(JellyBeansSelector mainController) {
		this.mainController = mainController;
	}

	public void setResultElement(IResultElement resultElement) {
		this.resultElement = resultElement;
		System.out.println(jbLabel);
		jbLabel.setText(resultElement.getName());
	}
}
