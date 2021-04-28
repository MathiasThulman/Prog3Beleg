package events;

import java.util.EventListener;

public interface OutputEvenListener extends EventListener {
    void addEvent(OutputStringEvent event);
}
