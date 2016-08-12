package fr.axonic.base.format;

import fr.axonic.base.AString;

/**
 * Created by cduffau on 12/08/16.
 */
public class StringFormat extends Format<String, String> {
    public StringFormat() {
        super(AString.class, String.class);
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
