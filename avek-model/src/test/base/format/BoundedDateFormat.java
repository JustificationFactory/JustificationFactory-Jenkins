package fr.axonic.base.format;

import fr.axonic.base.AContinuousDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by cduffau on 12/08/16.
 */
public class BoundedDateFormat extends BoundedFormat<GregorianCalendar,String>{

    private static SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public BoundedDateFormat() {
        super(AContinuousDate.class, GregorianCalendar.class);
    }

    @Override
    public String marshalMin(GregorianCalendar min) {
        return sdf.format(min.getTime());
    }

    @Override
    public GregorianCalendar unmarshalMin(String prettyMin) {
        try {
            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(sdf.parse(prettyMin));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String marshalMax(GregorianCalendar max) {
        return sdf.format(max.getTime());
    }

    @Override
    public GregorianCalendar unmarshalMax(String prettyMax) {
        try {
            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(sdf.parse(prettyMax));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String marshalValue(GregorianCalendar value) {
        return sdf.format(value.getTime());
    }

    @Override
    public GregorianCalendar unmarshalValue(String prettyValue) {
        try {
            GregorianCalendar calendar=new GregorianCalendar();
            calendar.setTime(sdf.parse(prettyValue));
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
