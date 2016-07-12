package fr.axonic.avek.model.base.engine;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
public abstract class AElement extends AEntity{

    protected boolean editable, mandatory;

    @XmlTransient
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        setUnverifiableProperty(AVarProperty.EDITABLE.name(), editable);
    }

    @XmlTransient
    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        setUnverifiableProperty(AVarProperty.MANDATORY.name(), mandatory);
    }

    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        switch (AVarProperty.valueOf(propertyName)) {
            case EDITABLE: {
                editable = (boolean) newPropertyValue;
            }
            break;
            case MANDATORY: {
                mandatory = (boolean) newPropertyValue;
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
            case EDITABLE: {
                result = editable;
            }
            break;
            case MANDATORY: {
                result = mandatory;
            }
            break;
            default: {
                result = super.getPropertyValue(propertyName);
            }

        }
        return result;
    }
}
