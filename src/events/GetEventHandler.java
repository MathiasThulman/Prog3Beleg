package events;

import java.util.LinkedList;
import java.util.List;

public class GetEventHandler {
    private List<GetEventListener> listenerList = new LinkedList<>();

    public void add(GetEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(GetEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(GetEvent event){
        for(GetEventListener listener : this.listenerList){
            listener.addEvent(event);
        }
    }
}
