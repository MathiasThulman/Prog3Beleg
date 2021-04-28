package events;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.LinkedList;
import java.util.List;

public class InputEventHandler {
    private List<InputEventListener> listenerList = new LinkedList<>();

    public void add(InputEventListener listener){
        this.listenerList.add(listener);
    }

    public void remove(InputEventListener listener){
        this.listenerList.remove(listener);
    }

    public void handle(InputEvent e) throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
        for(InputEventListener listener : this.listenerList){
            listener.addEvent(e);
        }
    }
}
