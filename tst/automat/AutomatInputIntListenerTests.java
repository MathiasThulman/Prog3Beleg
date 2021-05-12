package automat;

import events.EventType;
import events.InputIntEvent;
import events.InputIntEventHandler;
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

public class AutomatInputIntListenerTests {

    @Test
    public void addKuchenListenerTest() throws InvalidInputException, AlreadyExistsException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatInputIntListener listener = new AutomatInputIntListener();

        listener.setAutomat(auto);

        auto.addHersteller(new HerstellerImpl("faustulus"));
        InputIntEvent event = new InputIntEvent(this, EventType.addKuchen, 1);
        listener.addEvent(event);

        Assertions.assertEquals( 500,auto.getKuchen(0).getNaehrwert());
    }

    @Test
    public void removeKuchenListenerTest() throws AlreadyExistsException, FullAutomatException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatInputIntListener listener = new AutomatInputIntListener();

        listener.setAutomat(auto);
        auto.addHersteller(new HerstellerImpl("faustulus"));
        auto.addKuchen(new ObstkuchenImpl(new HerstellerImpl("faustulus"), new LinkedList<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(7), new BigDecimal(500), "Erdbeere"));

        InputIntEvent event = new InputIntEvent(this, EventType.removeKuchen, 0);
        listener.addEvent(event);

        //check for exception to see if the cake at fachnummer 0 has been removed
        Assertions.assertThrows(NoSuchElementException.class, () -> auto.getKuchen(0));
    }

    @Test
    public void getOneKuchenListenerTest(){
        AutomatImpl auto = new AutomatImpl(20);
        AutomatInputIntListener listener = new AutomatInputIntListener();

        //TODO
    }

    @Test
    public void setInspectionDateListenerTest() throws AlreadyExistsException, FullAutomatException, InvalidInputException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatInputIntListener listener = new AutomatInputIntListener();

        listener.setAutomat(auto);
        auto.addHersteller(new HerstellerImpl("faustulus"));
        auto.addKuchen(new ObstkuchenImpl(new HerstellerImpl("faustulus"), new LinkedList<>(Arrays.asList(Allergen.Gluten)), 500, Duration.ofDays(7), new BigDecimal(500), "Erdbeere"));

        Date date = new Date(2020, 6,6);
        InputIntEvent event = new InputIntEvent(this, EventType.setDate, 0,date);
        listener.addEvent(event);

        Assertions.assertEquals(date, auto.getKuchen(0).getInspektionsdatum());

    }
}
