package automat;

import automat.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

public class ObsttorteTest {

    @Test
    public void getNameValid() {
        HashSet<Allergen> allergene = new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss));
        ObsttorteImpl kuchen = new ObsttorteImpl(new HerstellerImpl("Guido"), allergene, 500, Duration.ofDays(4),
                new BigDecimal("4.50"));

        Assertions.assertEquals("Obsttorte", kuchen.getName());
    }

}
