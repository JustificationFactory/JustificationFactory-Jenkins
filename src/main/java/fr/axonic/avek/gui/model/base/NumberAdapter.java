package fr.axonic.avek.gui.model.base;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by cduffau on 21/07/15.
 */
public class NumberAdapter extends XmlAdapter<Object, Object> {
    @Override
    public Object unmarshal(Object v) throws Exception {
        return Double.parseDouble(v.toString());
    }

    @Override
    public Object marshal(Object v) throws Exception {
        return new Integer(3);
    }

}
