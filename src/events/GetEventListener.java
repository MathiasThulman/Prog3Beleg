package events;

import java.util.EventListener;

public interface GetEventListener extends EventListener {
    void addEvent(GetEvent event);
}
