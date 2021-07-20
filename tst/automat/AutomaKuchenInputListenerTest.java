package automat;

import events.EventType;
import events.InputKuchenEvent;
import exceptions.FullAutomatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AutomaKuchenInputListenerTest {

    @Test
    public void addEventValid(){
        KremkuchenImpl kuch = mock(KremkuchenImpl.class);
        Automat auto = mock(Automat.class);
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(auto);

        AutomatInputKuchenListener listener = new AutomatInputKuchenListener();
        listener.setAutomatWrapper(wrapper);
        InputKuchenEvent event = new InputKuchenEvent(this, EventType.addKuchen, kuch);

        listener.addEvent(event);
        try {
            verify(auto).addKuchen(kuch);
        } catch (FullAutomatException e) {
            fail();
        }
    }
}
