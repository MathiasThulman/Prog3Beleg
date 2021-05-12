package automat;

import automat.*;
import exceptions.AlreadyExistsException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KremkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;

import static org.mockito.Mockito.*;

public class ObserverTest {
    Hersteller herst1 = new HerstellerImpl("MOSES");
    Duration dur1 = Duration.ofDays(4);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), "Mascarpone");

    @Test
    public void automatCapacityObserverTestValid() throws AlreadyExistsException, FullAutomatException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatCapacityObserver obs = new AutomatCapacityObserver(auto);

        auto.addHersteller(herst1);

        //addkuchen until almost at full capacity
        for(int i = 0; i < 18; i++){
            auto.addKuchen(kuch1);
        }
        final ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos2));
        String testString = "Dieser Automat hat die Kapazität von 90% überschritten" + System.lineSeparator();//line seperator necessary to properly compary

        auto.addKuchen(kuch1);
        Assertions.assertEquals(testString, bos2.toString());

    }

    @Test
    public void AutomatAllergenObserverTestValid() throws AlreadyExistsException, FullAutomatException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatAllergenObserver obs = new AutomatAllergenObserver(auto);

        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos1));

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        String testString = "Die Allergene im Automat haben sich verändert" + System.lineSeparator();

        Assertions.assertEquals(testString , bos1.toString());
    }

    @Test
    public void automatChangeObserverAddKuchenTest() throws AlreadyExistsException, FullAutomatException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatChangeObserver obs = new AutomatChangeObserver(auto);

        auto.addHersteller(herst1);

        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos1));

        auto.addKuchen(kuch1);
        String testString ="KUCHEN HINZUGEFÜGT" + System.lineSeparator();

        Assertions.assertEquals(testString, bos1.toString());

    }

    @Test
    public void removeKuchenChangeObserverTest() throws AlreadyExistsException, FullAutomatException, InvalidInputException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatChangeObserver obs = new AutomatChangeObserver(auto);

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos1));
        auto.removeKuchen(0);

        String testString ="KUCHEN ENTFERNT" + System.lineSeparator();
        Assertions.assertEquals(testString, bos1.toString());

    }
}
