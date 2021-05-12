package events;

import java.util.EventObject;

public class InputStringEvent extends EventObject {
    private final String message;
    private final EventType type;

    public InputStringEvent(Object source, EventType type, String message) {
        super(source);
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public EventType getType() {
        return type;
    }
}
