package fr.axonic.base.format;

import fr.axonic.base.ADate;
import fr.axonic.base.engine.AVar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by cduffau on 12/08/16.
 */
public class DateFormat extends Format<GregorianCalendar,String>{

    private static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public DateFormat() {
        super(ADate.class, GregorianCalendar.class);
    }

    @Override
    public String marshalValue(GregorianCalendar value) {
        return sdf.format(value.getTime());
    }

    @Override
    public GregorianCalendar unmarshalValue(String prettyFormat) {
        try {
            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(sdf.parse(prettyFormat));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
