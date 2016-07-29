package fr.axonic.base;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

/**
 * Created by cduffau on 18/07/16.
 */
public class ADateAdapter extends XmlAdapter<XMLGregorianCalendarImpl, GregorianCalendar>{

    @Override
    public GregorianCalendar unmarshal(XMLGregorianCalendarImpl v) throws Exception {
        return v.toGregorianCalendar();
    }

    @Override
    public XMLGregorianCalendarImpl marshal(GregorianCalendar v) throws Exception {
        return (XMLGregorianCalendarImpl) DatatypeFactory.newInstance().newXMLGregorianCalendar(v);
    }
}
