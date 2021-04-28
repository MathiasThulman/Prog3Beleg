package events;

import events.InputEvent;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.EventListener;

public interface InputEventListener extends EventListener {
    void addEvent(InputEvent event) throws FullAutomatException, AlreadyExistsException, InvalidInputException, EmptyListException;
}

