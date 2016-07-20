package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.model.base.ARangedEnum;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Nathaël N on 28/06/16.
 */
class JellyBean extends HBox {
	private final static Logger logger = Logger.getLogger(JellyBean.class);
	private final static URL JELLYBEAN_FXML
			= JellyBean.class.getClassLoader().getResource("fxml/results/jellyBean.fxml");
	private final static String JELLYBEAN_CSS = "css/results/jellyBean.css";

	@FXML private Button jbLabel;
	@FXML private Button jbCross;

	private ARangedEnum enumType;
	private Object expEffect;
	private JellyBeansSelector mainController;

	JellyBean() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(JELLYBEAN_FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.info("Loading jellyBean.fxml");
		fxmlLoader.load();

		logger.info("Adding jellybean.css");
		this.getStylesheets().add(JELLYBEAN_CSS);
		logger.info("Adding jellybeans personalized status css");
		this.getStylesheets().add("css/results/personalized/levels.css");
		this.getStylesheets().add("css/results/personalized/boolean.css");
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

		refreshColor(beforeEffect, expEffect);
	}

	private void refreshColor(Object bef, Object aft) {
		String before = bef.toString().toLowerCase();
		String after = aft.toString().toLowerCase();

		Platform.runLater(() -> {
			jbLabel.getStyleClass().remove(before);
			jbCross.getStyleClass().remove(before);
			jbLabel.getStyleClass().add(after);
			jbCross.getStyleClass().add(after);
		});
	}


	Object getState() {
		return expEffect;
	}

	void setStateType(ARangedEnum stateType) {
		enumType = stateType;

		refreshColor(expEffect==null?"":expEffect, stateType.getValue());

		expEffect = stateType.getValue();
	}
	public void setText(String text) {
		jbLabel.setText(text);
	}
	public String getText() { return jbLabel.getText(); }

	void setMainController(JellyBeansSelector mainController) {
		this.mainController = mainController;
	}

	@Override
	public String toString() {
		return "JellyBean=" + getState();
	}
}
