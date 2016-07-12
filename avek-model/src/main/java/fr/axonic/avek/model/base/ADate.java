package fr.axonic.avek.model.base;


import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.Format;
import fr.axonic.avek.model.base.engine.FormatType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class ADate extends AVar<Date> {

    public ADate() {
        this(null);
    }

    public ADate(Date value) {
        super(new Format(FormatType.DATE),value);
    }
    public ADate(String label, Date value) {
        super(label,new Format(FormatType.DATE), value);
    }

    /**
     * Returns the internal JAVA value in the form of a double.
     *
     * @return internal JAVA value of this ANumber (double)
     **/
    /**public GregorianCalendar gregorianCalendarValue() {
        GregorianCalendar gregorianCalendar;
        try{
            gregorianCalendar = (GregorianCalendar) this.getValue();
            return gregorianCalendar;
        }
        catch (ClassCastException e){
            // TODO : handle that
            //XMLGregorianCalendar calendar= (XMLGregorianCalendar) this.getValue();
            //gregorianCalendar=calendar.toGregorianCalendar();
        }

    }*/
}
