package events;

import java.util.LinkedList;
import java.util.List;

public class CollectionOutputHandler {
    private List<CollectionOutputListener> listenerList = new LinkedList<>();

    public void add(CollectionOutputListener listener){
        this.listenerList.add(listener);
    }

    public void remove(ErrorEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(CollectionOutputEvent event){
        for(CollectionOutputListener listener : this.listenerList){
            listener.addEvent(event);
        }
    }
}
