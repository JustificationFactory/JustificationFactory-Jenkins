package fr.axonic.observation.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cboinaud on 18/07/2016.
 */
public class AEntityChanged {

    private List<AEntityEvent> events;
    private ChangedEventType type = ChangedEventType.MIXED;

    public AEntityChanged(AEntityEvent... entityEvents){
        this.events = new ArrayList<>(Arrays.asList(entityEvents));
    }

    public AEntityChanged(List<AEntityEvent> events){
        this.events = new ArrayList<>(events);
    }

    public AEntityChanged(ChangedEventType type, AEntityEvent... entityEvents){
        this(entityEvents);
        this.type = type;
    }

    public AEntityChanged(ChangedEventType type, List<AEntityEvent> events){
        this(events);
        this.type = type;
    }

    public List<AEntityEvent> getEvents(){
        return events;
    }

    public ChangedEventType getType() {
        return type;
    }
}
