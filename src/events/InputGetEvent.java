package events;

import java.util.EventObject;

public class InputGetEvent extends Event {
    String kuchenClass;

    public InputGetEvent(Object source, EventType eventType) {
        super(source, eventType);
    }
    public InputGetEvent(Object source, EventType eventType, String kuchen){
        super(source, eventType);
        this.kuchenClass = kuchen;
    }

    public String getKuchenClass() {
        return kuchenClass;
    }
}
