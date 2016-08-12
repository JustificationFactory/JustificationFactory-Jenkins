package fr.axonic.base.format;

import fr.axonic.base.ANumber;

/**
 * Created by cduffau on 12/08/16.
 */
public class NumberFormat extends Format<Number, String>{

    public NumberFormat() {
        super(ANumber.class, Number.class);
    }

    @Override
    public String marshalValue(Number value) {
        return value.toString();
    }

    @Override
    public Number unmarshalValue(String prettyFormat) {
        return new Number() {
            @Override
            public int intValue() {
                return Integer.valueOf(prettyFormat);
            }

            @Override
            public long longValue() {
                return Long.valueOf(prettyFormat);
            }

            @Override
            public float floatValue() {
                return Float.valueOf(prettyFormat);
            }

            @Override
            public double doubleValue() {
                return Double.valueOf(prettyFormat);
            }
        };
    }
}
