package fr.axonic.avek.gui.components.parameters.list.leaves;

import fr.axonic.avek.gui.components.parameters.list.ExpParameterLeaf;
import fr.axonic.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 21/07/16.
 */
abstract class SensitiveParameter extends ExpParameterLeaf {
	CheckBox markedUtil;
	private Consumer<Boolean> onClickMarkedUtil;

	SensitiveParameter(int level, AVar paramValue) {
		super(level, paramValue);

		markedUtil = new CheckBox();
		markedUtil.setSelected(true);
		markedUtil.setOnAction(this::onClickMarkedUtil);

		//GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
		GridPane.setColumnIndex(levelMark, 1);
		GridPane.setColumnIndex(this.paramTitle, 2);
		GridPane.setColumnIndex(this.paramValue, 3);
	}

	protected void onClickMarkedUtil(ActionEvent event) {
		boolean b = markedUtil.isSelected();

		paramTitle.setDisable(!b);
		paramValue.setDisable(!b);
		levelMark.setDisable(!b);
		if(onClickMarkedUtil != null)
		onClickMarkedUtil.accept(b);
	}
	public void setOnClickMarkedUtil(Consumer<Boolean> onClickMarkedUtil) {
		this.onClickMarkedUtil = onClickMarkedUtil;
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elements = super.getElements();
		elements.add(markedUtil);
		return elements;
	}
}
