package control;

import automat.Automat;
import automat.KremkuchenImpl;
import events.*;
import exceptions.EmptyListException;
import org.junit.jupiter.api.Test;
import persistence.JoSSerializer;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class AutomatGetEvenListenerTest {

    @Test
    public void addEventgetHerstellerValid(){
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomatWrapper(wrapper);
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
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomatWrapper(wrapper);
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
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);


        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomatWrapper(wrapper);
        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.getKuchenSpecific, "Kremkuchen");
        listener.addEvent(event);

        try {
            verify(auto).checkKuchen("Kremkuchen");
        } catch (EmptyListException e) {
            fail();
        }
    }

    @Test
    public void addEventGetAllergeneValid(){
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomatWrapper(wrapper);
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
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setAutomatWrapper(wrapper);
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

    @Test
    public void addEventSaveJOSValid(){
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);
        JoSSerializer serializer = mock(JoSSerializer.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setSerializer(serializer);
        listener.setAutomatWrapper(wrapper);

        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.saveAutomat);
        listener.addEvent(event);

        verify(serializer).serialize("CLISaveFile", auto);
    }

    @Test
    public void addLoadJoSValid(){
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);
        JoSSerializer serializer = mock(JoSSerializer.class);

        AutomatGetEventListener listener = new AutomatGetEventListener();
        listener.setSerializer(serializer);
        listener.setAutomatWrapper(wrapper);

        CollectionOutputHandler<CollectionOutputEvent> handler = new CollectionOutputHandler<>(); //needed to avoid nullpointer since it gets called with automat
        listener.setCollectionHandler(handler);
        InputGetEvent event = new InputGetEvent(this, EventType.loadAutomat);
        listener.addEvent(event);

        verify(serializer).deserialize("CLISaveFile");
    }
}
