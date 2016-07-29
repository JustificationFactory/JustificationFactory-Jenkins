package fr.axonic.observation.event;

import fr.axonic.base.engine.AEntity;

/**
 * Created by cboinaud on 15/07/2016.
 */
public class ACollectionChangedEvent<T extends AEntity> extends AEntityEvent {

    private final T changedValue;
    private int indexChanged = -1;

    public ACollectionChangedEvent(ChangedEventType changedEventType, AEntity source, T changedValue) {
        super(source, changedEventType);
        this.changedValue = changedValue;
    }

    public ACollectionChangedEvent(ChangedEventType changedEventType, AEntity source, T changedValue, Integer indexChanged) {
        this(changedEventType, source, changedValue);
        this.indexChanged = indexChanged;
    }

    public T getChangedValue() {
        return changedValue;
    }

    public int getIndexChanged() {
        return indexChanged;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("ACollectionChangedEvent{");
        builder.append("changedType=").append(changedType);
        builder.append(", source=").append(source);
        builder.append(", changedValue=").append(changedValue);

        if(indexChanged != -1){
            builder.append(", indexChanged=").append(indexChanged);
        }

        builder.append("}");

        return builder.toString();
    }
}
