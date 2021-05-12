package events;

import java.util.LinkedList;
import java.util.List;

public class ErrorEventHandler {
    private List<ErrorEventListener> listenerList = new LinkedList<>();

    public void add(ErrorEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(ErrorEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(ErrorEvent event){
        for(ErrorEventListener listener : this.listenerList){
            listener.addEvent(event);
        }
    }
}
