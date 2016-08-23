package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.leaves.*;
import fr.axonic.base.engine.*;

/**
 * Created by Nathaël N on 21/07/16.
 */
class GeneralizedCategory extends ParametersCategory {

    /**
     * @param level Deep level of this parameter grid (= his parent level+1)
     */
    GeneralizedCategory(int level) {
        super(level);
    }

    @Override
    protected GeneralizedCategory getNewCategory(AList aList) {
        GeneralizedCategory subCategory = new GeneralizedCategory(level + 1);
        subCategory.setAList(aList);

        return subCategory;
    }

    @Override
    protected ExpParameterLeaf getNewLeaf(AVar aVar) {
        return myGetLeaf(aVar);
    }

    private <T extends AVar<V> & ContinuousAVar<V>,
             U extends AVar<W> & DiscretAVar<U>,
             W extends AEnumItem,
             V> ExpParameterLeaf myGetLeaf(AVar<V> aVar) {
        if(aVar instanceof ContinuousAVar) {
            return new BoundedParameter(level + 1, (T) aVar);
        } else if(aVar instanceof DiscretAVar) {
            return new RangedParameter(level + 1, (U) aVar);
        } else {
            return new SimpleParameter(level + 1, aVar);
        }
    }

    @Override
    protected ExpParameterLeaf generateTitle(String text) {
        return new GeneralizedCategoryTitle(level, text);
    }
}
