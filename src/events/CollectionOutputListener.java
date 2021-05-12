package events;

import java.util.EventListener;

public interface CollectionOutputListener extends EventListener {
    void addEvent(CollectionOutputEvent event);
}
