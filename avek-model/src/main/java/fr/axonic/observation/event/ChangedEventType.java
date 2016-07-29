package fr.axonic.observation.event;

/**
 * Created by cboinaud on 13/07/2016.
 */
public enum ChangedEventType {

    // node/leaf/collection element
    CHANGED,

    // collections
    PERMUTED,
    REMOVED,
    ADDED,

    // used for events packets
    MIXED
}
