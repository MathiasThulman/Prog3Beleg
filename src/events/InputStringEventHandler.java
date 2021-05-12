package events;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.LinkedList;
import java.util.List;

public class InputStringEventHandler<T extends Event> {

    private final List<EventListener<T>> listenerList = new LinkedList<>();

    public void add(EventListener<T> listener){
        this.listenerList.add(listener);
    }

    public void remove(EventListener<T> listener){
        this.listenerList.remove(listener);
    }

    public void handle(T event) {
        for(EventListener<T> listener : this.listenerList){
            listener.addEvent(event);
        }
    }
}
