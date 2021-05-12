package events;

import java.util.EventListener;

public interface InputStringListener extends EventListener {
    void addEvent(InputStringEvent event);
}
