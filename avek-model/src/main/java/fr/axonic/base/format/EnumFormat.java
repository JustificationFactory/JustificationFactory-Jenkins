package fr.axonic.base.format;

import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 12/08/16.
 */
public class EnumFormat<T extends Enum<T> & AEnumItem> extends Format<T, String> {
    public EnumFormat() {
        // TODO : handle Enum.class
        this(null);
    }

    public  EnumFormat(Class<T> tClass){
        super(AEnum.class,tClass);
    }


    @Override
    public String marshalValue(T value) {
        return value.name();
    }

    @Override
    public T unmarshalValue(String prettyFormat) {
        return (T) Enum.valueOf(getFormatType(), prettyFormat);
    }
}
