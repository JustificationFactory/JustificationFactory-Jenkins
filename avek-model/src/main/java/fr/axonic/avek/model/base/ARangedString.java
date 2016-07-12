package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import java.util.List;

/**
 * Created by cduffau on 11/07/16.
 */
public class ARangedString extends AString implements DiscretAVar<String> {


    private List<String> range;
    @Override
    public void setRange(List<String> range) throws VerificationException {
        setProperty(AVarProperty.RANGE.name(), range);
    }
    @Override
    public List<String> getRange(){
        return range;
    }

    @Override
    public ARangedString clone() throws CloneNotSupportedException {
        ARangedString result = (ARangedString) super.clone();

        result.range = range;


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
                range = (List<String>) newPropertyValue;
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
}
