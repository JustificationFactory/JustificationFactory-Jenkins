package fr.axonic.base.format;

import fr.axonic.base.ARangedEnum;
import fr.axonic.base.ARangedString;
import fr.axonic.base.engine.AVar;

import java.util.List;

/**
 * Created by cduffau on 12/08/16.
 */
public class RangedStringFormat extends RangedFormat<String,String>{

    public RangedStringFormat() {
        super(ARangedString.class, String.class);
    }

    @Override
    public List<String> marshalRange(List<String> range) {
        return range;
    }

    @Override
    public List<String> unmarshalRange(List<String> prettyRange) {
        return prettyRange;
    }

    @Override
    public String marshalValue(String value) {
        return value;
    }

    @Override
    public String unmarshalValue(String prettyFormat) {
        return prettyFormat;
    }
}
