package simulation;

import automat.Allergen;
import automat.Automat;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KremkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class SimulationWrapperTests {
    private static final String MASCARPONE = "Mascarpone";
    private final String BENJAMIN = "benjamin";
    private final String BLUEMCHEN = "blümchen";
    private final String MOSES = "moses";
    private final Hersteller herst1 = new HerstellerImpl(BENJAMIN);
    private final Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
    private final Hersteller herst3 = new HerstellerImpl(MOSES);
    Duration dur1 = Duration.ofDays(4);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), MASCARPONE);
    KremkuchenImpl kuch2 = new KremkuchenImpl(herst1, allergList1, 500, dur1, new BigDecimal(600), MASCARPONE);

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

        Date date1 = new Date(1980, Calendar.JANUARY, 1);
        Date date2 = new Date(2000, Calendar.JANUARY, 3);

        auto.setInspectionDate(date1, 0);
        auto.setInspectionDate(date2, 1);

        wrapper.removeOldestCake();
        //oldest cake should be removed check by Naehrwert if the correct one is still there
        Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
    }
}
