package automat;

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

public class AutomatChangeObserverTest {
    Hersteller herst1 = new HerstellerImpl("MOSES");
    Duration dur1 = Duration.ofDays(4);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), "Mascarpone");

    @Test
    public void removeKuchenChangeObserverTest() throws AlreadyExistsException, FullAutomatException, InvalidInputException {
        Automat auto = new Automat(20);
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
