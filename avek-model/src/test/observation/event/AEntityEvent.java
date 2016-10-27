package fr.axonic.observation.event;

import fr.axonic.base.engine.AEntity;

import java.util.EventObject;

/**
 * Created by cboinaud on 15/07/2016.
 */
public class AEntityEvent extends EventObject {

    protected final ChangedEventType changedType;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public AEntityEvent(AEntity source, ChangedEventType changedEventType) {
        super(source);

        this.changedType = changedEventType;
    }

    public ChangedEventType getChangedType() {
        return changedType;
    }

}
