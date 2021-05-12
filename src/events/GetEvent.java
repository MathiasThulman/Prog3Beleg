package events;

import java.util.EventObject;

public class GetEvent extends EventObject {
    EventType type;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GetEvent(Object source, EventType eventType) {
        super(source);
        this.type = eventType;
    }

    public EventType getType() {
        return type;
    }
}
