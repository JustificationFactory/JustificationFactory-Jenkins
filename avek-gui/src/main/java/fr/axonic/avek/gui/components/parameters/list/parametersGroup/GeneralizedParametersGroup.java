package fr.axonic.avek.gui.components.parameters.list.parametersGroup;

import fr.axonic.avek.gui.components.parameters.list.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.list.leaves.BoundedParameter;
import fr.axonic.avek.gui.components.parameters.list.leaves.RangedParameter;
import fr.axonic.avek.gui.components.parameters.list.leaves.SimpleParameter;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;

/**
 * Created by Nathaël N on 13/07/16.
 */
public abstract class GeneralizedParametersGroup extends ParametersGroup {
	/**
	 * @param level Deep level of this parameter grid (= his parent level+1)
	 * @param title Title of the ParametersGrid
	 */
	GeneralizedParametersGroup(final int level, final String title) {
		super(level, title);
		/*
		[ checkbox ][ Levelmark ][ Title Value               ][ Generalization ]
		     []          ↓       Main ParametersGrid's title
			 []          |↓      ParametersGrid's title
			 []          ||      A subelement : value         ...
			 []          ||      A subelement : value         ...
			 []          |       Another element : value      ...
		 */
	}

	@Override
	protected void addCategory(AList<AEntity> aList) {
		GeneralizedParametersGroup subCategory = new GeneralizedParametersCategory(level + 1, aList.getLabel());

		// Adding sub elements
		aList.getEntities().forEach(subCategory::addParameter);

		// Adding to the GUI
		addExpParameter(subCategory);
	}

	@Override
	protected void addLeaf(AVar aVar) {
		ExpParameterLeaf subLeaf;

		switch (aVar.getType()) {
			case RANGED_NUMBER:
			case NUMBER:
			case DATE:
				subLeaf = new BoundedParameter(level + 1, aVar);
				break;
			case RANGED_ENUM:
			case BOOLEAN:
			case ENUM:
				subLeaf = new RangedParameter(level + 1, aVar);
				break;
			case STRING:
			case UNKNOWN:
			default:
				subLeaf = new SimpleParameter(level + 1, aVar);
		}

		addExpParameter(subLeaf);
	}
}
