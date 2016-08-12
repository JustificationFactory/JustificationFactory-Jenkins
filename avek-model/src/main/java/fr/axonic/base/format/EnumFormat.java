package fr.axonic.base.format;

import fr.axonic.base.AEnum;

/**
 * Created by cduffau on 12/08/16.
 */
public class EnumFormat extends Format<Enum, String> {
    public EnumFormat() {
        super(AEnum.class, Enum.class);
    }


    @Override
    public String marshalValue(Enum value) {
        return value.name();
    }

    @Override
    public Enum unmarshalValue(String prettyFormat) {
        return Enum.valueOf(getFormatType(), prettyFormat);
    }
}
