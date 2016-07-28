package fr.axonic.avek.gui.components.jellyBeans;

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
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {
	private final static Logger logger = Logger.getLogger(JellyBean.class);
	private final static URL FXML
			= JellyBean.class.getClassLoader().getResource("fxml/components/JellyBean.fxml");
	private final static String CSS = "css/results/jellyBean.css";

	@FXML
	private Button jbLabel;
	@FXML
	private Button jbCross;

	private ARangedEnum enumType;
	private Object expEffect;
	private Consumer<JellyBean> onDelete;

	// should be public
	public JellyBean() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		logger.debug("Loading JellyBean... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("JellyBean loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for JellyBean", e);
		}

		this.getStylesheets().add(CSS);
		this.getStylesheets().add("css/results/personalized/levels.css");
		this.getStylesheets().add("css/results/personalized/boolean.css");
		logger.debug("Added css for JellyBean");
	}

	@FXML
	public void initialize() {
		setOnDelete(null);
	}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		onDelete.accept(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		// ReadOnly
		if(onDelete == null)
			return;

		Object beforeEffect = expEffect;

		List list = enumType.getRange();

		int nextIndex = (list.indexOf(expEffect) + 1) % list.size();
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

		refreshColor(expEffect == null ? "" : expEffect, stateType.getValue());

		expEffect = stateType.getValue();
	}

	public void setText(String text) {
		jbLabel.setText(text);
	}

	public String getText() {
		return jbLabel.getText();
	}

	@Override
	public String toString() {
		return "JellyBean=" + getState();
	}

	void setOnDelete(Consumer<JellyBean> onDelete) {
		this.onDelete = onDelete;
		jbCross.setVisible(onDelete != null);
		jbCross.setManaged(onDelete != null);
	}
}
