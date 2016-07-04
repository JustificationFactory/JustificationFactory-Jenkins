package fr.axonic.avek.model.base;


import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

public class ADate extends AVar{

    public ADate() {
        this(null);
    }

    public ADate(Object value) {
        super(new Format(FormatType.DATE),value);
    }
    public ADate(String label, Object value) {
        super(label,new Format(FormatType.DATE), value);
    }

    /**
     * Returns the internal JAVA value in the form of a double.
     *
     * @return internal JAVA value of this ANumber (double)
     **/
    public GregorianCalendar gregorianCalendarValue() {
        GregorianCalendar gregorianCalendar;
        try{
            gregorianCalendar = (GregorianCalendar) this.getValue();
        }
        catch (ClassCastException e){
            XMLGregorianCalendar calendar= (XMLGregorianCalendar) this.getValue();
            gregorianCalendar=calendar.toGregorianCalendar();
        }

        return gregorianCalendar;
    }
}
