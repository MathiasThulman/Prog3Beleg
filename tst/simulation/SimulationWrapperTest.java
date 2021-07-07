package simulation;

import automat.*;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import automat.KremkuchenImpl;
import automat.Kremsorte;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.Mockito.*;

public class SimulationWrapperTest {
    private static final String MASCARPONE = "Mascarpone";
    private final String BENJAMIN = "benjamin";
    private final String BLUEMCHEN = "blümchen";
    private final String MOSES = "moses";
    private final Hersteller herst1 = new HerstellerImpl(BENJAMIN);
    private final Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
    private final Hersteller herst3 = new HerstellerImpl(MOSES);
    Duration dur1 = Duration.ofDays(4);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );
    KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600), new Kremsorte(MASCARPONE));

    @Test
    public void createRandomCakeValid() throws AlreadyExistsException, EmptyListException {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        //every possible hersteller needs to be added to randomCreate doesnt not throw exception
        auto.addHersteller(herst1);
        auto.addHersteller(herst2);
        auto.addHersteller(herst3);

        wrapper.createRandomCake();
        Assertions.assertFalse(auto.checkKuchen().isEmpty());
    }

    @Test
    public void createRandomCakeSynchronizedValid() throws AlreadyExistsException, EmptyListException {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        //every possible hersteller needs to be added to randomCreate doesnt not throw exception
        auto.addHersteller(herst1);
        auto.addHersteller(herst2);
        auto.addHersteller(herst3);

        wrapper.createRandomCakeSynchronized();
        Assertions.assertFalse(auto.checkKuchen().isEmpty());

    }

    @Test
    public void removeRandomCakeValid() throws AlreadyExistsException, FullAutomatException, EmptyListException {
        Automat auto = new Automat(10);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        wrapper.removeRandomCake();
        //list should be empty since only one cake can be remove, exceptions should not occur
        Assertions.assertTrue(auto.kuchListEmpty());
    }

    @Test
    public void removeOldestCakeValid() throws AlreadyExistsException, FullAutomatException, InvalidInputException, EmptyListException {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);

        wrapper.removeOldestCake();
        //oldest cake should be removed check by Naehrwert if the correct one is still there
        Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
    }

    @Test
    public void removeOldestCakeSynchronizedValid() throws AlreadyExistsException, FullAutomatException, InvalidInputException, EmptyListException {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);

        auto.setInspectionDate(0);
        auto.setInspectionDate(1);

        wrapper.removeOldestCakeSynchronized();
        //oldest cake should be removed check by Naehrwert if the correct one is still there
        Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
    }

    @Test
    public void causeInspectionValid() throws InvalidInputException {
        Automat auto = mock(Automat.class);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        wrapper.causeInspection();

        verify(auto).setInspectionDate(0);
    }

    @Test
    public void removeMultipleOldestCakeSynchronized() throws AlreadyExistsException, FullAutomatException{
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);


        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);

        wrapper.removeMultipleOldestCakeSynchronized();
        //TODO how to i test/assert in a random method?
    }
}
