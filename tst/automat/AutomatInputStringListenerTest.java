package automat;

import events.EventType;
import events.InputStringEvent;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AutomatInputStringListenerTest {

    @Test
    public void addHerstellerListenerTest() throws EmptyListException {
        Automat auto = new Automat(20);
        AutomatInputStringListener listener = new AutomatInputStringListener();

        listener.setAutomat(auto);

        InputStringEvent event = new InputStringEvent(this, EventType.addHersteller,"Guy Fiery");
        listener.addEvent(event);

        Assertions.assertEquals("Guy Fiery" ,auto.getHersteller().get(0).getName());
    }

    @Test
    public void removeHerstellerListenerTest() throws AlreadyExistsException {
        Automat auto = new Automat(20);
        AutomatInputStringListener listener = new AutomatInputStringListener();

        listener.setAutomat(auto);

        HerstellerImpl gf = new HerstellerImpl("Guy Fiery");
        auto.addHersteller(gf);
        InputStringEvent event = new InputStringEvent(this, EventType.remHersteller,"Guy Fiery");
        listener.addEvent(event);

        //empty list exception should be thrown since the only hersteller has been removed
        Assertions.assertThrows(EmptyListException.class, () -> auto.getHersteller().contains(gf));
    }
}
