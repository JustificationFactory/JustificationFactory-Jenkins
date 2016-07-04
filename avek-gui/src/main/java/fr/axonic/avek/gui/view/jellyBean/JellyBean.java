package fr.axonic.avek.gui.view.jellyBean;

import fr.axonic.avek.gui.model.IExpEffect;
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

	private IExpEffect expEffect;
	private JellyBeansSelector mainController;

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


	public IExpEffect getExpEffect() {
		return expEffect;
	}


	public void setMainController(JellyBeansSelector mainController) {
		this.mainController = mainController;
	}

	public void setExpEffect(IExpEffect expEffect) {
		this.expEffect = expEffect;
		jbLabel.setText(expEffect.getName());
	}
}
