package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verifiable;
import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by cduffau on 08/07/16.
 */
public class AEnum<T extends Enum<T>> extends AVar implements Verifiable {

    private List<T> enumsRange;
    private T defaultValue;

    public AEnum(){
        this(null);
    }
    public AEnum(T value) {
        super(new Format(FormatType.ENUM), value);
    }

    @XmlTransient
    public List<T> getEnumsRange() {
        return enumsRange;
    }

    public void setEnumsRange(T ... enums) throws VerificationException {
        List<T> l = new ArrayList<T>();
        Collections.addAll(l, enums);
        setEnumsRange(l);
    }
    public void setEnumsRange(List<T> enums) throws VerificationException {
        setProperty(AVarProperty.RANGE.name(), enums);
    }

    @Override
    public AEnum clone() throws CloneNotSupportedException {
        AEnum result = (AEnum) super.clone();

        result.enumsRange = enumsRange;


        return result;
    }

    @Override
    protected boolean isPropertyVerifiable(String propertyName) {

        boolean result;
        switch (AVarProperty.valueOf(propertyName)) {
            case RANGE:
            case DEFAULT_VALUE:{
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
            case RANGE: {
                enumsRange = (List<T>) newPropertyValue;
            }
            break;
            case DEFAULT_VALUE: {
                defaultValue = (T) newPropertyValue;
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
            case RANGE: {
                result = enumsRange;
            }
            break;
            case DEFAULT_VALUE: {
                result = defaultValue;
            }
            break;
            default: {
                result = super.getPropertyValue(propertyName);
            }

        }
        return result;
    }

    public T enumValue(){
        if(getValue()==null){
            return null;
        }
        return (T) getValue();
    }

    @XmlTransient
    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) throws VerificationException {
        setProperty(AVarProperty.DEFAULT_VALUE.name(), defaultValue);
        if(getPropertyValue(AVarProperty.VALUE.name())==null){
            setProperty(AVarProperty.VALUE.name(), defaultValue);
        }
    }
    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }

    @Override
    public String toString() {
        return "AEnum{" +
                "code='" + getCode() + '\'' +
                ", path='" + getPath() + '\'' +
                ", value=" + getValue()+ '\'' +
                ", range=" + enumsRange + '\'' +
                ", defaultValue=" + defaultValue + '\'' +
                '}';
    }
}
