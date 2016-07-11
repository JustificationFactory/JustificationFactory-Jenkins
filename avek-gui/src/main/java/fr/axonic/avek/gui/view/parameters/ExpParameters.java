package fr.axonic.avek.gui.view.parameters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ExpParameters extends BorderPane {

	@FXML
	private CheckBox markedUtil;

	@FXML
	private Label paramName;
	@FXML
	private Label paramValue;
	@FXML
	private Label paramUnit;

	@FXML
	private TextField minEquivRange;
	@FXML
	private TextField maxEquivRange;



	public ExpParameters() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/parameters/parameter.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	public void setParamName(String paramName) {
		this.paramName.setText(paramName);
	}

	public void setParamValue(String paramValue) {
		this.paramValue.setText(paramValue);
	}

	public void setParamUnit(String paramUnit) {
		this.paramUnit.setText(paramUnit);
	}

	@FXML
	protected void onClickMarkedUtil(ActionEvent event) {
		boolean b = !markedUtil.isSelected();
		paramName.setDisable(b);
		paramUnit.setDisable(b);
		paramValue.setDisable(b);
	}
}
