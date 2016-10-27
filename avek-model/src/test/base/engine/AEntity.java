package fr.axonic.base.engine;

import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.binding.BindingParametersException;
import fr.axonic.observation.binding.BindingTypesException;
import fr.axonic.observation.event.*;
import fr.axonic.validation.Verifiable;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 11/07/16.
 */
@XmlRootElement
public abstract class AEntity{

    protected static final Logger LOGGER = LoggerFactory.getLogger(AEntity.class);

    private String path, code, label;
    private transient PropertyChangeSupport changeSupport;

    private transient AEntityListener bindingListener;
    private transient AEntity bindElement;
    private transient final List<AEntityListener> listeners = new ArrayList<>();

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
    /**
     * Set the value of a property with a given property name. There are to
     * cases:
     * <ol>
     * <li>The property is verifiable - the modification is performed in the
     * following steps: the property value is changed internally, the method
     * {@link Verifiable#verify(boolean)} is called. If the the modification is
     * correct, the appropriate listeners are notified. Otherwise modification
     * is reverted and the fr.axonic.validation exception is passed.</li>
     * <li>The property is unverifiable</li>
     * </ol>
     *
     * @param propertyName
     *            A valid property of this AVar
     * @param newValue
     *            A new value of the property
     * @throws VerificationException
     *             The exception is thrown only for the verifiable properties in
     *             case when their modification does not satisfy the
     *             fr.axonic.validation procedure of this AVar (see
     *             {@link Verifiable#verify(boolean)}).
     */
    public void setProperty(String propertyName, Object newValue){

        Object oldValue = getPropertyValue(propertyName);

        setPropertyValue(propertyName, newValue);

        this.firePropertyChange(propertyName, oldValue, newValue);
        this.fireEvent(new AEntityChangedEvent(ChangedEventType.CHANGED, this, propertyName, oldValue, newValue));

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

    public synchronized void addListener(AEntityListener listener){
        if(listener != null){
            listeners.add(listener);
        }
    }

    public synchronized void removeListener(AEntityListener listener){
        if(listener != null){
            listeners.remove(listener);
        }
    }

    public synchronized void removeListeners(){
        listeners.clear();
    }

    protected void fireEvent(AEntityChanged events){

        listeners.forEach(listener -> {

            long changed = events.getEvents().stream()
                    .filter(event -> listener.acceptChangedType(event.getChangedType()) && (
                            !listener.listenOnlyEffectiveChanges() || (
                                    (event instanceof AEntityChangedEvent && isEffectiveChange((AEntityChangedEvent) event))
                                    || (event instanceof ACollectionChangedEvent && isEffectiveChange((ACollectionChangedEvent) event))
                            )
                    )).count();

            if(changed != 0) {
                listener.changed(events);
            }
        });
    }

    protected void fireEvent(AEntityEvent... events){
        fireEvent(new AEntityChanged(events));
    }

    protected void fireEvent(ChangedEventType type, AEntityEvent... events){
        fireEvent(new AEntityChanged(type, events));
    }

    /**
     * Bind this entity with the given one.
     * @param entity to bind with current one.
     * @param <S> entity type.
     */
    public abstract <S extends AEntity> void bind(S entity) throws BindingTypesException;

    /**
     * Unbind current entity and binded entity.
     */
    public abstract void unbind();

    public abstract <S extends AEntity> void bindBiDirectional(S entity)
            throws BindingTypesException, VerificationException, BindingParametersException;

    public boolean isBindWith(AEntity entity){
        return getBindElement() != null && entity != null && getBindElement().equals(entity);
    }

    public abstract <S extends AEntity> void setValues(S entity) throws VerificationException, BindingTypesException;

    /**
     * Checks if value changed effectively or not.
     * @param event
     * @return
     */
    private boolean isEffectiveChange(AEntityChangedEvent event){
        return (event.getOldValue() == null && event.getNewValue() != null)
                || (event.getOldValue() != null && !event.getOldValue().equals(event.getNewValue()));
    }

    /**
     * Only permutations are defined as non effective-changes in AList.
     * @param event
     * @return
     */
    private boolean isEffectiveChange(ACollectionChangedEvent event){
        return true;
    }

    protected void finalize() throws Throwable {
        removeListeners();
        super.finalize();
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AEntity))
            return false;

        AEntity aEntity = (AEntity) o;

        if (getPath() != null ? !getPath().equals(aEntity.getPath()) : aEntity.getPath() != null)
            return false;
        if (getCode() != null ? !getCode().equals(aEntity.getCode()) : aEntity.getCode() != null)
            return false;
        return getLabel() != null ? getLabel().equals(aEntity.getLabel()) : aEntity.getLabel() == null;

    }

    @Override public int hashCode() {
        int result = getPath() != null ? getPath().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getLabel() != null ? getLabel().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "path='" + path + '\'' +
                ", code='" + code + '\'' +
                ", label='" + label + '\'';
    }

    @XmlTransient
    public AEntityListener getBindingListener() {
        return bindingListener;
    }

    public void setBindingListener(AEntityListener bindingListener) {
        this.bindingListener = bindingListener;
    }

    @XmlTransient
    public AEntity getBindElement() {
        return bindElement;
    }

    public void setBindElement(AEntity bindElement) {
        this.bindElement = bindElement;
    }
}
