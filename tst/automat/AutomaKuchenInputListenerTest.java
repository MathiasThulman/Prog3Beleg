package automat;

import events.EventType;
import events.InputKuchenEvent;
import exceptions.FullAutomatException;
import kuchen.KremkuchenImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AutomaKuchenInputListenerTest {

    @Test
    public void addEventValid(){
        KremkuchenImpl kuch = mock(KremkuchenImpl.class);
        Automat auto = mock(Automat.class);

        AutomatInputKuchenListener listener = new AutomatInputKuchenListener();
        listener.setAutomat(auto);
        InputKuchenEvent<KremkuchenImpl> event = new InputKuchenEvent(this, EventType.addKuchen, kuch);

        listener.addEvent(event);
        try {
            verify(auto).addKuchen(kuch);
        } catch (FullAutomatException e) {
            fail();
        }
    }
}
