package automat;

import events.EventType;
import events.InputStringEvent;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class AutomatInputStringListenerTest {

    @Test
    public void addHerstellerListenerTest() {
        Automat auto = new Automat(20);
        AutomatInputStringListener listener = new AutomatInputStringListener();

        listener.setAutomat(auto);

        InputStringEvent event = new InputStringEvent(this, EventType.addHersteller,"Guy Fiery");
        listener.addEvent(event);

        try {
            Assertions.assertEquals("Guy Fiery" ,auto.getHersteller().get(0).getName());
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    public void removeHerstellerListenerTest() {
        Automat auto = new Automat(20);
        AutomatInputStringListener listener = new AutomatInputStringListener();

        listener.setAutomat(auto);

        HerstellerImpl gf = new HerstellerImpl("Guy Fiery");
        try {
            auto.addHersteller(gf);
        } catch (AlreadyExistsException e) {
            fail();
        }
        InputStringEvent event = new InputStringEvent(this, EventType.remHersteller,"Guy Fiery");
        listener.addEvent(event);

        //empty list exception should be thrown since the only hersteller has been removed
        Assertions.assertThrows(EmptyListException.class, () -> auto.getHersteller().contains(gf));
    }
}
