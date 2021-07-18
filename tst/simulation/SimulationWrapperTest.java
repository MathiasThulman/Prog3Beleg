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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class SimulationWrapperTest {
    private static final String MASCARPONE = "Mascarpone";
    private final String BENJAMIN = "benjamin";
    private final String BLUEMCHEN = "blümchen";
    private final String MOSES = "moses";
    Duration dur1 = Duration.ofDays(4);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));

    @Test
    public void createRandomCakeValid() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
        Hersteller herst3 = new HerstellerImpl(MOSES);

        //every possible hersteller needs to be added to randomCreate doesnt not throw exception
        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst2);
            auto.addHersteller(herst3);

            wrapper.createRandomCake();
            Assertions.assertEquals(1, auto.getKuchenCounter());
        } catch (AlreadyExistsException e) {
           fail();
        }
    }

    @Test
    public void createRandomCakeSynchronizedValid() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        //every possible hersteller needs to be added to randomCreate doesnt not throw exception
        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst2);
            auto.addHersteller(herst3);

            wrapper.createRandomCakeSynchronized();
            Assertions.assertFalse(auto.checkKuchen().isEmpty());
        } catch (AlreadyExistsException | EmptyListException e) {
            fail();
        }

    }

    @Test
    public void removeRandomCakeValid() {
        Automat auto = new Automat(10);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        wrapper.removeRandomCake(new Random(System.currentTimeMillis()));
        //list should be empty since only one cake can be remove, exceptions should not occur
        Assertions.assertTrue(auto.kuchListEmpty());
    }

    @Test
    public void removeOldestCakeValid() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );
        KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600), new Kremsorte(MASCARPONE));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            wrapper.removeOldestCake();
            //oldest cake should be removed check by Naehrwert if the correct one is still there
            Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void removeOldestCakeSynchronizedValid() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );
        KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600), new Kremsorte(MASCARPONE));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            auto.setInspectionDate(0);
            auto.setInspectionDate(1);

            wrapper.removeOldestCakeSynchronized();
            //oldest cake should be removed check by Naehrwert if the correct one is still there
            Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void causeInspectionValid() throws InvalidInputException {
        //TODO why does this cause nullpointer
        Automat auto = mock(Automat.class);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);


        when(auto.getKuchenCounter()).thenReturn(10);
//        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
//        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );
//        Automat auto = new Automat(10);



//            auto.addHersteller(herst1);
//            auto.addKuchen(kuch1);

            wrapper.causeInspection(new Random(System.currentTimeMillis()));

            verify(auto).setInspectionDate(any());

    }

    @Test
    public void removeMultipleOldestCakeSynchronized() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );
        KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600), new Kremsorte(MASCARPONE));
        KremkuchenImpl kuch3 = new KremkuchenImpl(herst1, allergList1, 400, dur1, new BigDecimal(859), new Kremsorte(MASCARPONE));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        wrapper.removeMultipleOldestCakeSynchronized(new Random(2));
        assertEquals(1, auto.getKuchenCounter());
        //TODO how to i test/assert in a random method?
    }
}
