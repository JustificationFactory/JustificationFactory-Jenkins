package fr.axonic.avek.gui.components.jellyBeans;

import fr.axonic.base.ARangedEnum;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {
	private final static Logger logger = Logger.getLogger(JellyBean.class);
	private final static URL FXML
			= JellyBean.class.getClassLoader().getResource("fxml/components/JellyBean.fxml");

	@FXML
	private Button jbLabel;
	@FXML
	private Button jbCross;

	private ARangedEnum enumType;
	private AVar expEffect;
	private Consumer<JellyBean> onDelete;

	private boolean isRemovable, isEditable;

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

		this.getStylesheets().add("css/components/results/levels.css");
		this.getStylesheets().add("css/components/results/boolean.css");
		logger.debug("Added css for JellyBean");
	}

	@FXML
	public void initialize() {
		jbLabel.getStyleClass().remove("button");
		jbCross.getStyleClass().remove("button");
		setEditable(false);
		setRemovable(false);
	}

	@FXML
	public void onClickOnCross(ActionEvent actionEvent) {
		if(isRemovable)
			onDelete.accept(this);
	}

	@FXML
	public void onClickOnLabel(ActionEvent actionEvent) {
		// ReadOnly
		if(!isEditable)
			return;

		AVar beforeEffect = expEffect;

		AList list = enumType.getRange();

		int nextIndex = (list.indexOf(expEffect) + 1) % list.size();
		expEffect = ((AVar)list.get(nextIndex));

		refreshColor(beforeEffect, expEffect);
	}

	private void refreshColor(AVar bef, AVar aft) {
		String before = bef.getValue().toString().toLowerCase();
		String after = aft.getValue().toString().toLowerCase();

		Platform.runLater(() -> {
			jbLabel.getStyleClass().remove(before);
			jbCross.getStyleClass().remove(before);
			jbLabel.getStyleClass().add(after);
			jbCross.getStyleClass().add(after);
		});
	}


	AVar getState() {
		return expEffect;
	}

	void setStateType(ARangedEnum stateType) {
		enumType = stateType;

		refreshColor(expEffect == null ? new AString("") : expEffect, stateType);

		expEffect = stateType;
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
		setEditable(onDelete != null);
		setRemovable(onDelete != null);
	}

	private void setRemovable(boolean removable) {
		isRemovable = removable;
		jbCross.setVisible(removable);
		jbCross.setManaged(removable);
	}
	private void setEditable(boolean editable) {
		isEditable = editable;

		getStyleClass().remove("jellyBeanEditable");
		if(editable)
			getStyleClass().add("jellyBeanEditable");
	}
}
