package control;

import automat.Automat;
import events.CollectionOutputEvent;
import events.CollectionOutputHandler;
import events.EventType;
import events.InputIntEvent;
import exceptions.InvalidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AutomatInputIntListenerTest {


    @Test
    public void addEventRemoveKuchenValid() {
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);
        AutomatInputIntListener listener = new AutomatInputIntListener();
        listener.setAutomatWrapper(wrapper);

        InputIntEvent event = new InputIntEvent(this, EventType.removeKuchen, 1);

        listener.addEvent(event);

        try {
            verify(auto).removeKuchen(1);
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void getOneKuchenListenerTest(){
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);
        AutomatInputIntListener listener = new AutomatInputIntListener();
        listener.setAutomatWrapper(wrapper);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>();//necessary to avoid nullpointer since its called with automat
        listener.setCollectionHandler(handler);

        InputIntEvent event = new InputIntEvent(this, EventType.getOneKuchen, 1);
        listener.addEvent(event);

        try {
            verify(auto).getKuchen(1);
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void setInspectionDateListenerTest()  {
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);
        AutomatInputIntListener listener = new AutomatInputIntListener();
        listener.setAutomatWrapper(wrapper);
        InputIntEvent event = new InputIntEvent(this, EventType.setDate, 1);
        listener.addEvent(event);

        try {
            verify(auto).setInspectionDate(1);
        } catch (InvalidInputException e) {
            fail();
        }
    }
}
