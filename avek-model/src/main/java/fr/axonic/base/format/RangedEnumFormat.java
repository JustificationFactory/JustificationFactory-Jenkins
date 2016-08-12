package fr.axonic.base.format;

import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 12/08/16.
 */
public class RangedEnumFormat extends RangedFormat<Enum,String>{
    public RangedEnumFormat() {
        super(ARangedEnum.class, Enum.class);
    }

    @Override
    public List<Enum> marshalRange(List<String> range) {
        List<Enum> enums=new ArrayList<>();
        range.forEach(s -> enums.add(Enum.valueOf(getFormatType(),s)));
        return enums;
    }

    @Override
    public List<String> unmarshalRange(List<Enum> prettyRange) {
        List<String> res=new ArrayList<>();
        prettyRange.forEach(s -> res.add(s.name()));
        return res;
    }

    @Override
    public String marshalValue(Enum value) {
        return value.name();
    }

    @Override
    public Enum unmarshalValue(String prettyFormat) {
        return Enum.valueOf(getFormatType(),prettyFormat);
    }
}
