package fr.axonic.avek.gui.view.parameters.list;

import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.FormatType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
class ExpParameter implements IExpParameter {
	// GUI Component
	private CheckBox markedUtil;
	private LevelMark levelMark;
	private Label paramTitle;
	private Label paramValue;
	private TextField minEquivRange;
	private TextField maxEquivRange;

	ExpParameter(AVar paramValue, int level) {
		// Init elements
		markedUtil = new CheckBox();
		markedUtil.setSelected(true);
		markedUtil.setOnAction(this::onClickMarkedUtil);
		GridPane.setColumnIndex(markedUtil, 0);

		levelMark = new LevelMark(level);
		GridPane.setColumnIndex(levelMark, 1);

		this.paramTitle = new Label(paramValue.getLabel());
		GridPane.setColumnIndex(this.paramTitle, 2);


		this.paramValue = new Label(" : "+paramValue.getValue().toString());
		GridPane.setColumnIndex(this.paramValue, 3);

		if(paramValue.getFormat().getType() == FormatType.NUMBER) {
			minEquivRange = new TextField(paramValue.getValue().toString());
			minEquivRange.setMaxWidth(70);
			GridPane.setColumnIndex(minEquivRange, 4);

			maxEquivRange = new TextField(paramValue.getValue().toString());
			maxEquivRange.setMaxWidth(70);
			GridPane.setColumnIndex(maxEquivRange, 5);
		}
	}

	private void onClickMarkedUtil(ActionEvent event) {
		boolean b = markedUtil.isSelected();

		paramTitle.setDisable(!b);
		paramValue.setDisable(!b);
		if(minEquivRange != null)
			minEquivRange.setDisable(!b);
		if(maxEquivRange != null)
			maxEquivRange.setDisable(!b);
	}

	Set<Node> getElements() {
		Set<Node> elts = new HashSet<>();

		elts.add(markedUtil);
		elts.add(paramTitle);
		elts.add(levelMark);
		elts.add(paramValue);
		if(minEquivRange != null)
			elts.add(minEquivRange);
		if(maxEquivRange != null)
			elts.add(maxEquivRange);

		return elts;
	}

	@Override
	public String getName() {
		return paramTitle.getText();
	}
}
