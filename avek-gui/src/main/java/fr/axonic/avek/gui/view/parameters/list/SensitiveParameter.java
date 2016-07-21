package fr.axonic.avek.gui.view.parameters.list;

import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public abstract class SensitiveParameter extends ExpParameterLeaf {
	protected CheckBox markedUtil;

	protected SensitiveParameter(int level, AVar paramValue) {
		super(level, paramValue);

		markedUtil = new CheckBox();
		markedUtil.setSelected(true);
		markedUtil.setOnAction(this::onClickMarkedUtil);

		GridPane.setColumnIndex(markedUtil, 0);
		GridPane.setColumnIndex(levelMark, 1);
		GridPane.setColumnIndex(this.paramTitle, 2);
		GridPane.setColumnIndex(this.paramValue, 3);
	}

	protected void onClickMarkedUtil(ActionEvent event) {
		boolean b = markedUtil.isSelected();

		paramTitle.setDisable(!b);
		paramValue.setDisable(!b);
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elts = super.getElements();
		elts.add(markedUtil);
		return elts;
	}

}
