package automat;

import automat.*;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KremkuchenImpl;
import org.junit.jupiter.api.Test;

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
    public void automatCapacityObserverTestValid() throws AlreadyExistsException, FullAutomatException, InvalidInputException, EmptyListException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatCapacityObserver obs = new AutomatCapacityObserver(auto);

        auto.addHersteller(herst1);

        //addkuchen until almost at full capacity
        for(int i = 0; i < 18; i++){
            auto.addKuchen(kuch1);
        }
        auto.addKuchen(kuch1);
        verify(obs).update(); //geht nur mit mock
    }

    @Test
    public void AutomatAllergenObserverTestValid() throws AlreadyExistsException, FullAutomatException {
        AutomatImpl auto = new AutomatImpl(20);
        AutomatAllergenObserver obs = new AutomatAllergenObserver(auto);

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);
        //TODO was wird von dem Observer erwartet?
    }
}
