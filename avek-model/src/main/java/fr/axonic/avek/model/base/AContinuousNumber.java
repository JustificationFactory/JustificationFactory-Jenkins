package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by cduffau on 11/07/16.
 */
public class AContinuousNumber extends ANumber implements ContinuousAVar<Number> {

    private Number min;

    private Number max;

    @Override
    protected boolean isPropertyVerifiable(String propertyName) {

        boolean result;
        switch (AVarProperty.valueOf(propertyName)) {
            case MIN:
            case MAX: {
                result = true;
            }
            break;
            default: {
                result = super.isPropertyVerifiable(propertyName);
            }
        }
        return result;
    }

    @Override
    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        switch (AVarProperty.valueOf(propertyName)) {
            case MIN: {
                min = (Number) newPropertyValue;
            }
            break;
            case MAX: {
                max = (Number) newPropertyValue;
            }
            break;
            default: {
                super.setPropertyValue(propertyName, newPropertyValue);
            }
        }
    }

    @Override
    protected  Object getPropertyValue(String propertyName){
        Object result;
        switch (AVarProperty.valueOf(propertyName)) {
            case MIN: {
                result = min;
            }
            break;
            case MAX: {
                result = max;
            }
            break;

            default: {
                result = super.getPropertyValue(propertyName);
            }

        }
        return result;
    }

    @Override
    public ANumber clone() throws CloneNotSupportedException {
        AContinuousNumber result = (AContinuousNumber) super.clone();

        result.min = min;
        result.max = max;


        return result;
    }

    @XmlTransient
    @Override
    public Number getMin() {
        return min;
    }

    @Override
    public void setMin(Number min) throws VerificationException {
        setProperty(AVarProperty.MIN.name(), min);
    }

    @XmlTransient
    @Override
    public Number getMax() {
        return max;
    }

    @Override
    public void setMax(Number max) throws VerificationException {
        setProperty(AVarProperty.MAX.name(), max);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }
}
