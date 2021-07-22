package simulation;

import automat.*;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import automat.KremkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
        Automat auto = mock(Automat.class); //new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);


        try {


            wrapper.createRandomCake();
            verify(auto, times(1)).addKuchen(any());
//            Assertions.assertEquals(1, auto.getKuchenCounter());
        } catch (FullAutomatException e) {
           fail();
        }
    }

    @Test
    public void createRandomCakeSynchronizedValid() {
        Automat auto = mock(Automat.class);//new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);
        when(auto.getKuchenCounter()).thenReturn(10);

        //every possible hersteller needs to be added to randomCreate doesnt not throw exception
        try {
//            auto.addHersteller(herst1);
//            auto.addHersteller(herst2);
//            auto.addHersteller(herst3);

            wrapper.createRandomCakeSynchronized();
            verify(auto, times(1)).addKuchen(any());

        } catch (FullAutomatException e) {
            fail();
        }

    }

    @Test
    public void removeRandomCakeValid() throws EmptyListException {
        Automat auto = mock(Automat.class);//new Automat(10);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        List<KuchenKomponente> mockList = new LinkedList<>();
        mockList.add(mock(KuchenBelag.class));

        when(auto.getSize()).thenReturn(1);
        when(auto.checkKuchen()).thenReturn(mockList);
//        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
//        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));

//        try {
//            auto.addHersteller(herst1);
//            auto.addKuchen(kuch1);
//        } catch (AlreadyExistsException | FullAutomatException e) {
//            fail();
//        }

        wrapper.removeRandomCake();
        //list should be empty since only one cake can be remove, exceptions should not occur
//        Assertions.assertTrue(auto.kuchListEmpty());
        try {
            verify(auto).removeKuchen(any());
        } catch (InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void removeOldestCakeValid() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600));

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
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            wrapper.removeOldestCakeSynchronized();
            //oldest cake should be removed check by Naehrwert if the correct one is still there
            Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void causeInspectionValid() throws InvalidInputException {
        //TODO why does this cause nullpointer
        Automat auto = mock(Automat.class);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);


        try {
            List<KuchenKomponente> mockList = new LinkedList<>();
            mockList.add(mock(KuchenBelag.class));

            when(auto.getKuchenCounter()).thenReturn(1);
            when(auto.checkKuchen()).thenReturn(mockList);
        } catch (EmptyListException e) {
            e.printStackTrace();
        }
//        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
//        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE) );
//        Automat auto = new Automat(10);



//            auto.addHersteller(herst1);
//            auto.addKuchen(kuch1);

            wrapper.causeInspection();

            verify(auto).setInspectionDate(any());
    }

    @Test
    public void removeMultipleOldestCakeSynchronized() {
        Automat auto = new Automat(20);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(auto);

        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        wrapper.removeMultipleOldestCakeSynchronized(new Random(2));
        assertEquals(1, auto.getKuchenCounter());
    }
}
