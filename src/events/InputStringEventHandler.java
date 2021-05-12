package events;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.LinkedList;
import java.util.List;

public class InputStringEventHandler {
    private List<InputStringListener> listenerList = new LinkedList<>();

    public void add(InputStringListener listener){
        this.listenerList.add(listener);
    }

    public void remove(InputStringListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(InputStringEvent e) {
        for(InputStringListener listener : this.listenerList){
            listener.addEvent(e);
        }
    }
}
