package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.leaves.BoundedParameter;
import fr.axonic.avek.gui.components.parameters.leaves.RangedParameter;
import fr.axonic.avek.gui.components.parameters.leaves.SimpleParameter;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nathaël N on 13/07/16.
 */
public abstract class GeneralizedGroup extends ParametersGroup {
    /**
     * @param level Deep level of this parameter grid (= his parent level+1)
     * @param title Title of the ParametersGrid
     */
    GeneralizedGroup(final int level, final String title) {
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
        GeneralizedGroup subCategory = new GeneralizedCategory(level + 1, aList.getLabel());

        // Adding sub elements
        aList.getList().forEach(subCategory::addParameter);

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
                subLeaf = new RangedParameter(level + 1, aVar, ((ARangedEnum) aVar).getRange());
                break;
            case BOOLEAN:
                List<AVar> values = Arrays.asList(
                        new AString("unknown", "unknown"),
                        new AString("true", "true"),
                        new AString("false", "false"));

                subLeaf = new RangedParameter(level + 1, aVar, values);
                break;
            case ENUM:
            case STRING:
            case UNKNOWN:
            default:
                subLeaf = new SimpleParameter(level + 1, aVar);
        }

        addExpParameter(subLeaf);
    }
}
