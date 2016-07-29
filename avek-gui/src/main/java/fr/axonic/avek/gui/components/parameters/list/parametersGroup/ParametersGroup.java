package fr.axonic.avek.gui.components.parameters.list.parametersGroup;

import fr.axonic.avek.gui.components.parameters.list.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.list.IExpParameter;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathaël N on 13/07/16.
 */
public abstract class ParametersGroup extends GridPane implements IExpParameter {
	private static final Logger logger = Logger.getLogger(ParametersGroup.class);
	private static final String CSS = "css/Parameters.css";

	protected final int level;
	private final String title;
	private final List<IExpParameter> subElements;

	/**
	 * @param level Deep level of this parameter grid (= his parent level+1)
	 * @param title Title of the ParametersGrid
	 */
	ParametersGroup(final int level, final String title) {
		subElements = new ArrayList<>();
		this.title = title;
		this.level = level;

		/*
		[ Levelmark ][ Title & value            ]
		     ↓       Main ParametersGrid's title
			 |↓      ParametersGrid's title
			 ||      A subelement : value
			 ||      A subelement : value
			 |       Another element : value
		 */

		logger.info("Adding Parameters.css");
		this.getStylesheets().add(CSS);
		this.setVgap(2);
		this.setHgap(2);
	}

	/**
	 * @param aEntity The AVar to add as a Experiment parameter,
	 *                or the AList to add as a Experiment parameter sub group
	 * @throws ClassCastException is the parameter is not a AVar
	 *                            nor a AList (of AList and AVar)
	 */
	public final void addParameter(AEntity aEntity) {
		/*
		if(aEntity instanceof AList<AEntity>)
			addCategory(aEntity);
		else if(aEntity instanceof AVar)
			addLeaf(aEntity);*/

		try {
			addCategory((AList<AEntity>) aEntity);
		} catch (ClassCastException cce) {
			addLeaf((AVar) aEntity); // throws ClassCastException if not a AVar
		}
	}

	protected void addCategory(AList<AEntity> aList) {
		ParametersGroup subCategory = new ParametersCategory(level + 1, aList.getLabel());

		// Adding sub elements
		aList.getList().forEach(subCategory::addParameter);

		// Adding to the GUI
		addExpParameter(subCategory);
	}

	protected void addLeaf(AVar aVar) {
		addExpParameter(new ExpParameterLeaf(level + 1, aVar));
	}

	protected void addExpParameter(IExpParameter subParam) {
		int rowIndex = subElements.size();

		// Adding graphical elements to the GUI
		for (Node n : subParam.getElements()) {
			GridPane.setRowIndex(n, rowIndex);
			this.getChildren().add(n);
		}

		// Adding to the list
		subElements.add(subParam);
	}

	public synchronized IExpParameter rmParameter(String name) {
		// searching parameter index
		int indexToRm = -1;

		// int j=0+1 because of title line at index 0
		for (int j = 1; j < subElements.size(); j++)
			if (subElements.get(j).getName().equals(name)) {
				indexToRm = j;
				break;
			}

		if (indexToRm == -1)
			return null;

		// Removing from view & shift view
		for (Node n : new ArrayList<>(getChildren())) {
			int index = GridPane.getRowIndex(n);

			if (indexToRm == index)
				getChildren().remove(n);
			else if (index > indexToRm)
				GridPane.setRowIndex(n, index - 1);
		}

		// Removing parameter from list
		return subElements.remove(indexToRm);
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> s = new HashSet<>();
		s.add(this);
		return s;
	}
}
