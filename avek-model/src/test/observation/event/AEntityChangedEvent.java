package fr.axonic.observation.event;

import fr.axonic.base.engine.AEntity;

/**
 * Created by cboinaud on 13/07/2016.
 */
public class AEntityChangedEvent<T> extends AEntityEvent {

    private final   T       oldValue;
    private final   T       newValue;
    private final String  propertyName;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AEntityChangedEvent(ChangedEventType changedType, AEntity source, String propertyName,
                               T oldValue, T newValue) {

        super(source, changedType);

        this.oldValue = oldValue;
        this.newValue = newValue;
        this.propertyName = propertyName;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String toString() {
        return "AEntityChangedEvent{" +
                "changedType=" + changedType +
                ", source=" + source +
                ", propertyName='" + propertyName + '\'' +
                ", oldValue=" + oldValue +
                ", newValue=" + newValue +
                '}';
    }
}
