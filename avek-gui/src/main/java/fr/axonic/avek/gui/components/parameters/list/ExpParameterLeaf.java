package fr.axonic.avek.gui.components.parameters.list;

import fr.axonic.avek.gui.util.MultiLevelMark;
import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ExpParameterLeaf implements IExpParameter {
	// GUI Component
	protected Label paramTitle;
	protected Label paramValue;
	protected final MultiLevelMark levelMark;

	public ExpParameterLeaf(int level, AVar paramValue) {
		// Init elements
		this.paramTitle = new Label(paramValue.getLabel());
		this.paramValue = new Label();

		levelMark = new MultiLevelMark(level);

		if (!paramValue.getValue().toString().equals(""))
			this.paramValue.setText(" : " + paramValue.getValue().toString());

		GridPane.setColumnIndex(this.levelMark, 0);
		GridPane.setColumnIndex(this.paramTitle, 1);
		GridPane.setColumnIndex(this.paramValue, 2);
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elts = new HashSet<>();

		elts.add(levelMark);
		elts.add(paramTitle);
		elts.add(paramValue);

		return elts;
	}

	@Override
	public String getName() {
		return paramTitle.getText();
	}

	protected void onClickMarkedUtil(ActionEvent event) {
		paramTitle.setDisable(true);
		paramValue.setDisable(true);
	}

	public void setExpandable(Consumer<Boolean> onClickExpand) {
		levelMark.setExpandable(onClickExpand);
	}

	protected void setExpanded(boolean expanded) {
		levelMark.setExpanded(expanded);
	}
}
