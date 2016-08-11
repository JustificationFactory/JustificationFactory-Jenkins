package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.leaves.BoundedParameter;
import fr.axonic.avek.gui.components.parameters.leaves.RangedParameter;
import fr.axonic.avek.gui.components.parameters.leaves.SimpleParameter;
import fr.axonic.base.engine.*;

/**
 * Created by Nathaël N on 13/07/16.
 */
public abstract class GeneralizedGroup extends ParametersGroup {

    /**
     * @param level Deep level of this parameter grid (= his parent level+1)
     */
    GeneralizedGroup(final int level) {
        super(level);

        /*
		[ checkbox ][ Levelmark ][ Title Value               ][ Generalization ]
		     []          ↓       Main ParametersGrid's title
			 []          |↓      ParametersGrid's title
			 []          ||      A subelement : value         ...
			 []          ||      A subelement : value         ...
			 []          |       Another element : value      ...
		 */
    }

    public GeneralizedGroup(int level, SimpleParameter simpleParameter) {
        super(level, simpleParameter);
    }

    @Override
    protected void addCategory(AList<AEntity> aList) {
        GeneralizedGroup subCategory = new GeneralizedCategory(level + 1, aList);

        // Adding to the GUI
        addExpParameter(subCategory);
    }

    @Override
    protected void addLeaf(AVar aVar) {
        myAddLeaf(aVar);
    }

    private <T extends AVar<V> & ContinuousAVar<V>,
             U extends AVar<V> & DiscretAVar<U>,
             V>
            void myAddLeaf(AVar<V> aVar) {
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
