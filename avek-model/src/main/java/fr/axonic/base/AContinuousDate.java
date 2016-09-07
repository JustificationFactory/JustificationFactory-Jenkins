package fr.axonic.base;

import fr.axonic.base.engine.ContinuousAVar;
import fr.axonic.validation.exception.VerificationException;

import java.util.GregorianCalendar;

/**
 * Created by cduffau on 09/08/16.
 */
public class AContinuousDate extends ADate implements ContinuousAVar<GregorianCalendar> {

    private GregorianCalendar min, max;

    @Override
    public GregorianCalendar getMin() {
        return min;
    }

    @Override
    public void setMin(GregorianCalendar min) throws VerificationException {
        this.min=min;
    }

    @Override
    public GregorianCalendar getMax() {
        return max;
    }

    @Override
    public void setMax(GregorianCalendar max) throws VerificationException {
        this.max=max;
    }
}
