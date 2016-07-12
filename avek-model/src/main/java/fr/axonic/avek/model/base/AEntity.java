package fr.axonic.avek.model.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by cduffau on 11/07/16.
 */
public abstract class AEntity{

    protected String path, code, label;
    protected transient PropertyChangeSupport changeSupport;

    @XmlElement
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        setUnverifiableProperty(AVarProperty.CODE.name(), code);
    }

    @XmlElement
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        setUnverifiableProperty(AVarProperty.PATH.name(), path);
    }

    @XmlTransient
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        setUnverifiableProperty(AVarProperty.LABEL.name(), label);
    }

    protected void setUnverifiableProperty(String propertyName, Object newValue) {

        if (isPropertyVerifiable(propertyName)) {
            throw new IllegalArgumentException("The property " + propertyName + " is verifiable");
        }

        Object oldValue = getPropertyValue(propertyName);

        setPropertyValue(propertyName, newValue);

        this.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected boolean isPropertyVerifiable(String propertyName) {
        return false;
    }
    protected Object getPropertyValue(String propertyName) {
        Object result;
        switch (AVarProperty.valueOf(propertyName)) {
            case CODE: {
                result = code;
            }
            break;
            case LABEL: {
                result = label;
            }
            break;
            case PATH: {
                result = path;
            }
            break;
            default: {
                throw new UnknownPropertyException();
            }

        }
        return result;
    }
    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        switch (AVarProperty.valueOf(propertyName)) {
            case CODE: {
                code = (String) newPropertyValue;
            }
            break;
            case LABEL: {
                label = (String) newPropertyValue;
            }
            break;
            case PATH: {
                path = (String) newPropertyValue;
            }
            break;
            default: {
                throw new UnknownPropertyException();
            }
        }

    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null)
            return;
        if (changeSupport == null)
            changeSupport = new PropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null || changeSupport == null)
            return;
        changeSupport.removePropertyChangeListener(listener);
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null)
            return new PropertyChangeListener[0];
        return changeSupport.getPropertyChangeListeners();
    }

    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (listener == null)
            return;
        if (changeSupport == null)
            changeSupport = new PropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (listener == null || changeSupport == null)
            return;
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    protected void firePropertyChange(Enum<?> propertyEnum, Object oldValue, Object newValue) {
        firePropertyChange(propertyEnum.name(), oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {

        if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
            return;
        }

        PropertyChangeSupport changeSupport = this.changeSupport;
        if (changeSupport == null) {
            return;
        }
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

}
