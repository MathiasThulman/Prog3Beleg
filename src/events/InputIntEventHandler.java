package events;

import java.util.LinkedList;
import java.util.List;

public class InputIntEventHandler {
    List<InputIntListener> listenerList = new LinkedList<>();

    public void add(InputIntListener listener){
        this.listenerList.add(listener);
    }
    public void remove(InputIntListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(InputIntEvent event){
        for(InputIntListener listener : this.listenerList){
            listener.addEvent(event);
        }
    }
}
