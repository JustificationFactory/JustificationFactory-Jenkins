package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 04/07/16.
 */
class ExpParameterGroup implements IExpParameter, IExpParameterContainer {
	// Graphical elements
	private CheckBox markedUtil;
	private Accordion accordion;

	// Util
	private List<IExpParameter> expParameters;
	private ParametersGridPane grid;

	public ExpParameterGroup(AList<AVar> paramValue) {
		// Init variables
		this.expParameters = new ArrayList<>();

		// Init elements
		markedUtil = new CheckBox();
		markedUtil.setSelected(true);

		accordion = new Accordion();
		grid = new ParametersGridPane();
		TitledPane tp = new TitledPane(paramValue.getLabel(), grid);
		accordion.getPanes().add(tp);

		// Dispose on line
		GridPane.setColumnIndex(markedUtil, 0);
		GridPane.setColumnIndex(accordion, 1);
		GridPane.setColumnSpan(accordion, 4);

		// Linking action listeners
		markedUtil.setOnAction(this::onClickMarkedUtil);

		paramValue.getEntities().forEach(this::addExpParameter);
	}

	private boolean isMarkedUtil() {
		return !markedUtil.isIndeterminate() && markedUtil.isSelected();
	}
	private void onClickMarkedUtil(ActionEvent event) {
		boolean newValue = isMarkedUtil();
		markedUtil.setSelected(newValue);
		accordion.setDisable(!newValue);

		if(!newValue && accordion.getExpandedPane() != null)
			accordion.getExpandedPane().setExpanded(false);
	}

	public void addExpParameter(AVar avar) {
		grid.addExpParameter(avar);
	}


	@Override
	public Set<Control> getElements() {
		Set<Control> elts = new HashSet<>();
		elts.add(accordion);
		elts.add(markedUtil);
		return elts;
	}

	@Override
	public String getName() {
		return accordion.getPanes().get(0).getText();
	}
}
