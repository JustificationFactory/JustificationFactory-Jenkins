package fr.axonic.base.format;

import fr.axonic.base.ABoolean;
import fr.axonic.base.engine.AVar;

/**
 * Created by cduffau on 12/08/16.
 */
public class BooleanFormat extends Format<Boolean, String>{

    public BooleanFormat() {
        super(ABoolean.class, Boolean.class);
    }

    @Override
    public String marshalValue(Boolean value) {
        return value.toString();
    }

    @Override
    public Boolean unmarshalValue(String prettyFormat) {
        return Boolean.valueOf(prettyFormat);
    }
}
