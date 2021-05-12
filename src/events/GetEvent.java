package events;

import java.util.EventObject;

public class GetEvent extends Event {

    public GetEvent(Object source, EventType eventType) {
        super(source, eventType);
    }

}
