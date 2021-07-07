package cli;

import events.*;
import automat.KremkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InputReaderTest {

    @Test
    public void readInsertAddHerstellerValid(){
        InputReader reader = new InputReader();
        InputStringEventHandler<InputStringEvent> handler = mock(InputStringEventHandler.class);
        reader.setStringHandler(handler);

        reader.readInsert("Gunther");
        ArgumentCaptor<InputStringEvent> argument = ArgumentCaptor.forClass(InputStringEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals("Gunther", argument.getValue().getMessage());
    }

    @Test
    public void readInsertKuchenValid(){
        InputReader reader = new InputReader();
        InputKuchenEventHandler<InputKuchenEvent> handler = mock(InputKuchenEventHandler.class);
        reader.setKuchenHandler(handler);

        reader.readInsert("Kremkuchen Friedrich 4 400 20 Gluten,Erdnuss Senf");
        ArgumentCaptor<InputKuchenEvent> argument = ArgumentCaptor.forClass(InputKuchenEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(KremkuchenImpl.class, argument.getValue().getKuchenObjekt().getClass());
    }

    @Test
    public void readDeleteKuchenTestValid(){
        InputReader reader = new InputReader();
        InputIntEventHandler<InputIntEvent> handler = mock(InputIntEventHandler.class);
        reader.setIntHandler(handler);

        reader.readDelete("5");
        ArgumentCaptor<InputIntEvent> argument = ArgumentCaptor.forClass(InputIntEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(5, argument.getValue().getInputInt());
    }

    @Test
    public void readDeleteHerstellerTestValid(){
        InputReader reader = new InputReader();
        InputStringEventHandler<InputStringEvent> handler = mock(InputStringEventHandler.class);
        reader.setStringHandler(handler);

        reader.readDelete("Heinrich");
        ArgumentCaptor<InputStringEvent> argument = ArgumentCaptor.forClass(InputStringEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals("Heinrich", argument.getValue().getMessage());
    }

    @Test
    public void readDisplayHerstellerValid(){
        InputReader reader = new InputReader();
        GetEventHandler<InputGetEvent> handler = mock(GetEventHandler.class);
        reader.setGetHandler(handler);

        reader.readDisplay("hersteller");
        ArgumentCaptor<InputGetEvent> argument = ArgumentCaptor.forClass(InputGetEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(EventType.getHersteller, argument.getValue().getType());
    }

    @Test
    public void readDisplayKuchenValid(){
        InputReader reader = new InputReader();
        GetEventHandler<InputGetEvent> handler = mock(GetEventHandler.class);
        reader.setGetHandler(handler);

        reader.readDisplay("kuchen");
        ArgumentCaptor<InputGetEvent> argument = ArgumentCaptor.forClass(InputGetEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(EventType.getKuchen, argument.getValue().getType());
    }

    @Test
    public void readDisplayKuchenSpecificValid(){
        InputReader reader = new InputReader();
        GetEventHandler<InputGetEvent> handler = mock(GetEventHandler.class);
        reader.setGetHandler(handler);

        reader.readDisplay("kuchen Obstkuchen");
        ArgumentCaptor<InputGetEvent> argument = ArgumentCaptor.forClass(InputGetEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(EventType.getKuchenSpecific, argument.getValue().getType());
    }

    @Test
    public void readDisplayAllergeneValid(){
        InputReader reader = new InputReader();
        GetEventHandler<InputGetEvent> handler = mock(GetEventHandler.class);
        reader.setGetHandler(handler);

        reader.readDisplay("allergene i");
        ArgumentCaptor<InputGetEvent> argument = ArgumentCaptor.forClass(InputGetEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(EventType.getAllergene, argument.getValue().getType());
    }

    @Test
    public void readDisplayAbsentAllergeneValid(){
        InputReader reader = new InputReader();
        GetEventHandler<InputGetEvent> handler = mock(GetEventHandler.class);
        reader.setGetHandler(handler);

        reader.readDisplay("allergene e");
        ArgumentCaptor<InputGetEvent> argument = ArgumentCaptor.forClass(InputGetEvent.class);
        verify(handler).handle(argument.capture());

        Assertions.assertEquals(EventType.getAbsentAllergene, argument.getValue().getType());
    }

}
