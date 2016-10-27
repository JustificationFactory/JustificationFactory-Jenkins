package fr.axonic.base.format;

import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEnumItem;
import fr.axonic.base.engine.AVar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 12/08/16.
 */
public class RangedEnumFormat<T extends Enum<T> & AEnumItem> extends RangedFormat<T,String>{

    public RangedEnumFormat(Class<T> tClass) {
        super(ARangedEnum.class, tClass);
    }

    public RangedEnumFormat() {
        // TODO : handle Enum.class
        this(null);
    }

    @Override
    public List<T> marshalRange(List<String> range) {
        List<T> enums=new ArrayList<>();
        range.forEach(s -> enums.add((T) Enum.valueOf(getFormatType(),s)));
        return enums;
    }

    @Override
    public List<String> unmarshalRange(List<T> prettyRange) {
        List<String> res=new ArrayList<>();
        prettyRange.forEach(s -> res.add(s.name()));
        return res;
    }

    @Override
    public String marshalValue(T value) {
        return value.name();
    }

    @Override
    public T unmarshalValue(String prettyFormat) {
        return (T) Enum.valueOf(getFormatType(),prettyFormat);
    }
}
