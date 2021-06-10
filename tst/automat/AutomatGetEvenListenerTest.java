package automat;

import events.*;
import exceptions.EmptyListException;
import kuchen.KremkuchenImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class AutomatGetEvenListenerTest {

    @Test
    public void addEventgetHerstellerValid(){
        Automat auto = mock(Automat.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomat(auto);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.getHersteller);
        listener.addEvent(event);

        try {
            verify(auto).checkHersteller();
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    public void addEventGetKuchenValid(){
        Automat auto = mock(Automat.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomat(auto);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.getKuchen);
        listener.addEvent(event);

        try {
            verify(auto).checkKuchen();
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    public void addEventGetKuchenSpecificValid(){
        Automat auto = mock(Automat.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomat(auto);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.getKuchenSpecific, KremkuchenImpl.class);
        listener.addEvent(event);

        try {
            verify(auto).checkKuchen(KremkuchenImpl.class);
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    public void addEventGetAllergeneValid(){
        Automat auto = mock(Automat.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomat(auto);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.getAllergene);
        listener.addEvent(event);

        try {
            verify(auto).checkAllergen();
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    public void addEventGetAbsentAllergeneValid(){
        Automat auto = mock(Automat.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomat(auto);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.getAbsentAllergene);
        listener.addEvent(event);

        try {
            verify(auto).checkAbsentAllergen();
        } catch (EmptyListException e) {
            fail();
        }
    }
}
