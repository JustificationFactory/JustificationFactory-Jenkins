package fr.axonic.avek.gui.components.parameters.list;

import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
public abstract class ExpParameterLeaf implements IExpParameter {
	// GUI Component
	protected Label paramTitle;
	protected Label paramValue;

	protected ExpParameterLeaf(AVar paramValue) {
		// Init elements
		this.paramTitle = new Label(paramValue.getLabel());
		this.paramValue = new Label();

		if (!paramValue.getValue().toString().equals(""))
			this.paramValue.setText(" : " + paramValue.getValue().toString());
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elts = new HashSet<>();

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
}
