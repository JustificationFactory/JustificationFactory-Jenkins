package fr.axonic.base.format;

import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.DiscretAVar;

import java.util.List;

/**
 * Created by cduffau on 12/08/16.
 */
public abstract class RangedFormat<U extends Object, S extends Object> extends Format<U,S>{


    public <T extends AVar<U> & DiscretAVar> RangedFormat(Class<T> aVarType, Class<U> formatType) {
        super(aVarType,formatType);
    }

    public abstract List<U> marshalRange(List<S> range);

    public abstract List<S> unmarshalRange(List<U> prettyRange);

}
