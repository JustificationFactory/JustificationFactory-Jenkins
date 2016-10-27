package fr.axonic.base.format;

import fr.axonic.base.AContinuousNumber;

/**
 * Created by cduffau on 12/08/16.
 */
public class BoundedNumberFormat extends BoundedFormat<Number,String>{

    public BoundedNumberFormat(){
        super(AContinuousNumber.class, Number.class);
    }
    @Override
    public String marshalMin(Number min) {
        return min.toString();
    }

    @Override
    public Number unmarshalMin(String prettyMin) {
        return new Number() {
            @Override
            public int intValue() {
                return Integer.valueOf(prettyMin);
            }

            @Override
            public long longValue() {
                return Long.valueOf(prettyMin);
            }

            @Override
            public float floatValue() {
                return Float.valueOf(prettyMin);
            }

            @Override
            public double doubleValue() {
                return Double.valueOf(prettyMin);
            }
        };
    }

    @Override
    public String marshalMax(Number max) {
        return max.toString();
    }

    @Override
    public Number unmarshalMax(String prettyMax) {
        return new Number() {
            @Override
            public int intValue() {
                return Integer.valueOf(prettyMax);
            }

            @Override
            public long longValue() {
                return Long.valueOf(prettyMax);
            }

            @Override
            public float floatValue() {
                return Float.valueOf(prettyMax);
            }

            @Override
            public double doubleValue() {
                return Double.valueOf(prettyMax);
            }
        };
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
