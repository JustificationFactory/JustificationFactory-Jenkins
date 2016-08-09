package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.IExpParameter;
import fr.axonic.avek.gui.components.parameters.leaves.BoundedParameter;
import fr.axonic.avek.gui.components.parameters.leaves.RangedParameter;
import fr.axonic.avek.gui.components.parameters.leaves.SimpleParameter;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.AString;
import fr.axonic.base.engine.*;
import javafx.scene.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        AList<AEntity> newAList = new AList<>();
        newAList.setLabel(aList.getLabel());
    }

    @Override
    protected void addLeaf(AVar aVar) {
        myAddLeaf(aVar);
    }

    private <T extends AVar & ContinuousAVar, U extends AVar & DiscretAVar>
                void myAddLeaf(AVar aVar) {
        ExpParameterLeaf subLeaf;

        if(aVar instanceof ContinuousAVar) {
            subLeaf = new BoundedParameter(level + 1, (T) aVar);
        } else if(aVar instanceof DiscretAVar) {
            subLeaf = new RangedParameter(level + 1, (U) aVar);
        } else {
            subLeaf = new SimpleParameter(level + 1, aVar);
        }

        addExpParameter(subLeaf);
    }
}
