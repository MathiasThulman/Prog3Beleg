package events;

import java.util.EventObject;

public class Event extends EventObject {
    private EventType type;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public Event(Object source, EventType type) {
        super(source);
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
