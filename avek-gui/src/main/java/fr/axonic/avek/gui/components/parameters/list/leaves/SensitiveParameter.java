package fr.axonic.avek.gui.components.parameters.list.leaves;

import fr.axonic.avek.gui.components.parameters.list.ExpParameterLeaf;
import fr.axonic.avek.gui.util.MultiLevelMark;
import fr.axonic.avek.model.base.engine.AVar;
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
	private final MultiLevelMark levelMark;

	SensitiveParameter(int level, AVar paramValue) {
		super(paramValue);

		markedUtil = new CheckBox();
		markedUtil.setSelected(true);
		markedUtil.setOnAction(this::onClickMarkedUtil);

		levelMark = new MultiLevelMark(level);

		GridPane.setColumnIndex(markedUtil, 0);
		GridPane.setColumnIndex(levelMark, 1);
		GridPane.setColumnIndex(this.paramTitle, 2);
		GridPane.setColumnIndex(this.paramValue, 3);
	}

	protected void onClickMarkedUtil(ActionEvent event) {
		boolean b = markedUtil.isSelected();

		paramTitle.setDisable(!b);
		paramValue.setDisable(!b);
		levelMark.setDisable(!b);
	}

	boolean isMarkedUtil() {
		return markedUtil.isSelected();
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elements = super.getElements();
		elements.add(markedUtil);
		elements.add(levelMark);
		return elements;
	}

	void setExpandable(Consumer<Boolean> onClickExpand) {
		levelMark.setExpandable(onClickExpand);
	}

	void setExpanded(boolean expanded) {
		levelMark.setExpanded(expanded);
	}
}
