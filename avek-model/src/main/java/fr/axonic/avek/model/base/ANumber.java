package fr.axonic.avek.model.base;



import fr.axonic.avek.model.verification.Verifiable;
import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlTransient;

public class ANumber extends AVar implements Verifiable {

    private Number min;

    private Number max;

    private Number defaultValue;

    public ANumber() {
        this(null);
    }

    public ANumber(Object value) {
        super(new Format(FormatType.NUMBER), value);
    }
    public ANumber(String label, Object value) {
        super(label,new Format(FormatType.NUMBER), value);
    }

    /**
     * Returns the internal JAVA value in the form of a double.
     * 
     * @return internal JAVA value of this ANumber (double)
     **/
    public double doubleValue() {
        Number number = (Number) this.getValue();
        if (number == null)
            return 0;

        return number.doubleValue();
    }

    /**
     * Returns the internal JAVA value in the form of a long.
     * 
     * @return internal JAVA value of this ANumber (long)
     **/
    public long longValue() {
        Number number = (Number) this.getValue();
        if (number == null)
            return 0;

        return number.longValue();
    }

    /**
     * Returns the internal JAVA value in the form of a int.
     * 
     * @return internal JAVA value of this ANumber (int)
     **/
    public int intValue() {
        Number number = (Number) this.getValue();
        if (number == null)
            return 0;

        return number.intValue();
    }

    /**
     * Returns the internal JAVA value in the form of a int.
     *
     * @return internal JAVA value of this ANumber (float)
     **/
    public float floatValue() {
        Number number = (Number) this.getValue();
        if (number == null)
            return 0.0f;

        return number.floatValue();
    }

    @Override
    protected boolean isPropertyVerifiable(String propertyName) {

        boolean result;
        switch (AVarProperty.valueOf(propertyName)) {
            case MIN:
            case MAX:
            case DEFAULT_VALUE: {
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
            case DEFAULT_VALUE: {
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
        ANumber result = (ANumber) super.clone();
        
        result.min = min;
        result.max = max;
        result.defaultValue = defaultValue;

        return result;
    }

    @XmlTransient
    public Number getMin() {
        return min;
    }

    public void setMin(Number min) throws VerificationException {
        setProperty(AVarProperty.MIN.name(), min);
    }

    @XmlTransient
    public Number getMax() {
        return max;
    }

    public void setMax(Number max) throws VerificationException {
        setProperty(AVarProperty.MAX.name(), max);
    }

    @XmlTransient
    public Number getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Number defaultValue) throws VerificationException {
        setProperty(AVarProperty.DEFAULT_VALUE.name(), defaultValue);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
    }

    @Override
    public String toString() {
        return "ANumber{" +
                "value=" + getValue() +
                ", min='" + getMin() + '\'' +
                ", max='" + getMax() + '\'' +
                ", code='" + getCode() + '\'' +
                ", path='" + getPath() + '\'' +
                '}';
    }
}
