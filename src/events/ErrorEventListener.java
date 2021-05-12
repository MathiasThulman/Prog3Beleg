package events;

import java.util.EventListener;

public interface ErrorEventListener extends EventListener {
    void addEvent(ErrorEvent event);
}
