package fr.axonic.avek.gui.components.parameters.list.leaves;

import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class BoundedParameter extends SensitiveParameter {
	private final TextField minEquivRange;
	private final TextField maxEquivRange;

	public BoundedParameter(int level, AVar paramValue) {
		super(level, paramValue);

		minEquivRange = new TextField(paramValue.getValue().toString());
		minEquivRange.setMaxWidth(70);

		maxEquivRange = new TextField(paramValue.getValue().toString());
		maxEquivRange.setMaxWidth(70);

		GridPane.setColumnIndex(minEquivRange, 4);
		GridPane.setColumnIndex(maxEquivRange, 5);
	}

	@Override
	protected void onClickMarkedUtil(ActionEvent event) {
		super.onClickMarkedUtil(event);

		boolean b = markedUtil.isSelected();
		minEquivRange.setDisable(!b);
		maxEquivRange.setDisable(!b);
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elts = super.getElements();
		elts.add(minEquivRange);
		elts.add(maxEquivRange);

		return elts;
	}
}
