package fr.axonic.avek.gui.components.parameters.list.leaves;

import fr.axonic.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class BoundedParameter extends SensitiveParameter {
	private final HBox generalizationPane;
	private final TextField minEquivRange;
	private final TextField maxEquivRange;

	public BoundedParameter(int level, AVar paramValue) {
		super(level, paramValue);

		generalizationPane = new HBox();

		minEquivRange = new TextField(paramValue.getValue().toString());
		minEquivRange.setMaxWidth(70);

		maxEquivRange = new TextField(paramValue.getValue().toString());
		maxEquivRange.setMaxWidth(70);

		generalizationPane.getChildren().add(minEquivRange);
		generalizationPane.getChildren().add(new Label(" - "));
		generalizationPane.getChildren().add(maxEquivRange);

		// GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
		// GridPane.setColumnIndex(levelMark, 1);
		// GridPane.setColumnIndex(this.paramTitle, 2);
		// GridPane.setColumnIndex(this.paramValue, 3);
		GridPane.setColumnIndex(generalizationPane, 4);
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
		elts.add(generalizationPane);

		return elts;
	}
}
