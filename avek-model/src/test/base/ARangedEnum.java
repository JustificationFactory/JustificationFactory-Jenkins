package fr.axonic.base;


import fr.axonic.base.engine.AEnumItem;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVarProperty;
import fr.axonic.base.engine.DiscretAVar;
import fr.axonic.validation.Verify;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
public class ARangedEnum<T extends Enum<T> & AEnumItem> extends AEnum<T> implements DiscretAVar<AEnum<T>> {
    private AList<AEnum<T>> range;

    /**
     * NEVER USE THIS CONSTRUCTOR (except if you set immediatly the format after)
     */
    @Deprecated
    public ARangedEnum() {
        super();
    }

    public ARangedEnum(Class<T> tClass) {
        super(tClass);
    }
    public ARangedEnum(Class<T> tClass, T value) {
        super(tClass,value);
    }


    @Override
    public AList<AEnum<T>> getRange() {
        return range;
    }

    @Override
    public void setRange(AList<AEnum<T>> range) throws VerificationException {
        setProperty(AVarProperty.RANGE.name(), range);
    }
    @Override
    public ARangedEnum clone() throws CloneNotSupportedException {
        ARangedEnum result = (ARangedEnum) super.clone();

        try {

            result.setRange(getRange());

        } catch (VerificationException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected boolean isPropertyVerifiable(String propertyName) {

        boolean result;
        switch (AVarProperty.valueOf(propertyName)) {
            case RANGE: {
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
                range = (AList<AEnum<T>>) newPropertyValue;
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
                result = range;
            }
            break;
            default: {
                result = super.getPropertyValue(propertyName);
            }

        }
        return result;
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-2)+
                ", range=" + range +
                '}';
    }
}
