package fr.axonic.base;




import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.Format;
import fr.axonic.base.engine.FormatType;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.GregorianCalendar;

@XmlRootElement
public class ADate extends AVar<GregorianCalendar> {

    public ADate() {
        this(null);
    }

    public ADate(GregorianCalendar value) {
        super(new Format(FormatType.DATE),value);
    }
    public ADate(String label, GregorianCalendar value) {
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

    @Override
    public String toString() {
        return "ADate{"+super.toString()+"}";
    }
}
