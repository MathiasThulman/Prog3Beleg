package events;

import automat.AutomatImpl;
import automat.AutomatInputListener;
import events.InputEvent;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListenerTests {

    @Test
    public void listenerTest1() throws EmptyListException, FullAutomatException, InvalidInputException, AlreadyExistsException {
        AutomatImpl auto = new AutomatImpl(500);
        AutomatInputListener listener = new AutomatInputListener();

        listener.setAutomat(auto);

        InputEvent e = new InputEvent(this, "addHersteller#Benjamin");

        listener.addEvent(e);

        Assertions.assertEquals("Benjamin", auto.getHersteller().get(0).getName());
    }

    @Test
    public void listenerTest2() throws EmptyListException, FullAutomatException, InvalidInputException, AlreadyExistsException {
        AutomatImpl auto = new AutomatImpl(500);
        AutomatInputListener listener = new AutomatInputListener();

        listener.setAutomat(auto);

        InputEvent e = new InputEvent(this, "addKuchen#1"); //TODO kuchen müssen geaddet werden?

        listener.addEvent(e);

        //Assertions.assertEquals("Benjamin", auto.getHersteller().get(0).getName());
    }
}
