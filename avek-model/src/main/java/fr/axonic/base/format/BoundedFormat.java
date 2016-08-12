package fr.axonic.base.format;

import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.ContinuousAVar;

/**
 * Created by cduffau on 12/08/16.
 */
public abstract class BoundedFormat<U extends Object, S extends Object> extends Format<U,S>{

    public <T extends AVar<U> & ContinuousAVar<U>> BoundedFormat(Class<T> avarType,Class<U> formatType) {
        super(avarType,formatType);
    }

    public abstract S marshalMin(U min);

    public abstract U unmarshalMin(S prettyMin);

    public abstract S marshalMax(U max);

    public abstract U unmarshalMax(S prettyMax);

}
