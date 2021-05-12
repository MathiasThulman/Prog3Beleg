package events;

import java.util.*;

public class CollectionOutputEvent extends EventObject {
    Collection eventColletion;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CollectionOutputEvent(Object source, Collection eventColletion) {
        super(source);
        this.eventColletion = eventColletion;
    }

    public Iterable getEventColletion() {
            return eventColletion;

    }
}
