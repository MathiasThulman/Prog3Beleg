package events;

import java.util.*;

public class CollectionOutputEvent extends Event {
    Collection eventCollection;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CollectionOutputEvent(Object source, Collection eventCollection) {
        super(source, EventType.collectionOutput);
        this.eventCollection = eventCollection;
    }

    public Collection getEventCollection() {
            return eventCollection;

    }
}
