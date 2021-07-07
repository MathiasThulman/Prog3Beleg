package automat;

import exceptions.AlreadyExistsException;
import exceptions.FullAutomatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;

public class AutomatCapacityObserverTest {

    @Test
    public void automatCapacityObserverTestValid() throws AlreadyExistsException, FullAutomatException {
        Hersteller herst1 = new HerstellerImpl("MOSES");
        Duration dur1 = Duration.ofDays(4);
        LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500),  new Kremsorte("Mascarpone"));

        Automat auto = new Automat(20);
        AutomatCapacityObserver obs = new AutomatCapacityObserver(auto);

        auto.addHersteller(herst1);
        //TODO remove exceptions from signature
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
}
