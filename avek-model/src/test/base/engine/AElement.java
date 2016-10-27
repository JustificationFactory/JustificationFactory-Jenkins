package fr.axonic.base.engine;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
public abstract class AElement extends AEntity {

    private boolean editable, mandatory;

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

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AElement))
            return false;
        if (!super.equals(o))
            return false;

        AElement aElement = (AElement) o;

        if (isEditable() != aElement.isEditable())
            return false;
        return isMandatory() == aElement.isMandatory();

    }

    @Override public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isEditable() ? 1 : 0);
        result = 31 * result + (isMandatory() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString()+
                ", editable=" + editable +
                ", mandatory=" + mandatory;
    }
}
