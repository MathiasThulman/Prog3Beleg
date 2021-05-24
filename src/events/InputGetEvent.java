package events;

import java.util.EventObject;

public class InputGetEvent extends Event {
    Class kuchenClass;

    public InputGetEvent(Object source, EventType eventType) {
        super(source, eventType);
    }
    public InputGetEvent(Object source, EventType eventType, Class kuchen){
        super(source, eventType);
        this.kuchenClass = kuchen;
    }

    public Class getKuchenClass() {
        return kuchenClass;
    }
}
