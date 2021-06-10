package automat;

import events.CollectionOutputEvent;
import events.CollectionOutputHandler;
import events.EventType;
import events.InputIntEvent;
import exceptions.AlreadyExistsException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.ObstkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AutomatInputIntListenerTest {


    @Test
    public void addEventRemoveKuchenValid() {
        Automat auto = mock(Automat.class);
        AutomatInputIntListener listener = new AutomatInputIntListener();
        listener.setAutomat(auto);

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
        AutomatInputIntListener listener = new AutomatInputIntListener();
        listener.setAutomat(auto);
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
        Date date = mock(Date.class);
        AutomatInputIntListener listener = new AutomatInputIntListener();
        listener.setAutomat(auto);
        InputIntEvent event = new InputIntEvent(this, EventType.setDate, 1 ,date);
        listener.addEvent(event);

        try {
            verify(auto).setInspectionDate(date,1);
        } catch (InvalidInputException e) {
            fail();
        }
    }
}
