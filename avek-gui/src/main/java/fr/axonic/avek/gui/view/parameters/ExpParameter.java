package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.FormatType;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ExpParameter {

	private CheckBox markedUtil;

	private Label paramName;
	private Label paramValue;

	private TextField minEquivRange;
	private TextField maxEquivRange;

	public ExpParameter(AVar paramValue) {
		// Init elements
		markedUtil = new CheckBox();
		markedUtil.setSelected(true);

		this.paramName = new Label(paramValue.getLabel());
		this.paramValue = new Label(" : "+paramValue.getValue().toString());
		if(paramValue.getFormat().getType() == FormatType.NUMBER) {
			minEquivRange = new TextField(paramValue.getValue().toString());
			maxEquivRange = new TextField(paramValue.getValue().toString());
		}

		// Dispose on line
		GridPane.setColumnIndex(markedUtil, 0);
		GridPane.setColumnIndex(this.paramName, 1);
		GridPane.setColumnIndex(this.paramValue, 2);
		if(minEquivRange != null)
			GridPane.setColumnIndex(minEquivRange, 3);
		if(maxEquivRange != null)
			GridPane.setColumnIndex(maxEquivRange, 4);


		// Linking action listeners
		markedUtil.setOnAction(this::onClickMarkedUtil);
	}

	public void setParamName(String paramName) {
		this.paramName.setText(paramName);
	}

	public void setParamValue(String paramValue) {
		this.paramValue.setText(paramValue);
	}

	public boolean isMakedUtil() {
		return !markedUtil.isIndeterminate() && markedUtil.isSelected();
	}

	protected void onClickMarkedUtil(ActionEvent event) {
		boolean b = !markedUtil.isSelected();
		paramName.setDisable(b);
		paramValue.setDisable(b);
	}


	public Set<Control> getElements() {
		Set<Control> elts = new HashSet<>();
		elts.add(paramName);
		elts.add(paramValue);

		if(minEquivRange != null)
			elts.add(minEquivRange);

		if(maxEquivRange != null)
			elts.add(maxEquivRange);

		elts.add(markedUtil);

		return elts;
	}

	public String getName() {
		return paramName.getText();
	}
}
