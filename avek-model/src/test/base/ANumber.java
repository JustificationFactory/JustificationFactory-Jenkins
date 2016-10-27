package fr.axonic.base;


import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.AVarProperty;
import fr.axonic.base.format.NumberFormat;
import fr.axonic.validation.Verifiable;
import fr.axonic.validation.Verify;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ANumber extends AVar<Number> implements Verifiable {

    private String unit;

    public ANumber() {
        this(null);
    }

    public ANumber(Number value) {
        super(new NumberFormat(), value);
    }
    public ANumber(String label, Number value) {
        super(label,new NumberFormat(), value);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
    }

    @Override
    public String toString() {
        return "ANumber{"+super.toString()+"}";
    }

    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        switch (AVarProperty.valueOf(propertyName)) {
            case UNIT: {
                unit = (String) newPropertyValue;
            }
            break;

            default: {
                super.setPropertyValue(propertyName,newPropertyValue);
            }
        }

    }

    protected Object getPropertyValue(String propertyName) {
        Object result;
        switch (AVarProperty.valueOf(propertyName)) {
            case UNIT: {
                result = unit;
            }
            break;
            default: {
                result = super.getPropertyValue(propertyName);
            }

        }
        return result;
    }
    @Override
    protected boolean isPropertyVerifiable(String propertyName) {
        boolean result;
        switch (AVarProperty.valueOf(propertyName)) {
            case UNIT: {
                result = false;
            }
            break;
            default: {
                result = super.isPropertyVerifiable(propertyName);
            }
        }
        return result;
    }

    @XmlTransient
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) throws VerificationException {
        setProperty(AVarProperty.UNIT.name(),unit);
    }
    @Override
    public AVar clone() throws CloneNotSupportedException {
        ANumber result = (ANumber) super.clone();
        try {
            result.setUnit(getUnit());
        } catch (VerificationException e) {
            e.printStackTrace();
        }
        return result;
    }

}
