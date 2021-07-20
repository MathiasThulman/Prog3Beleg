package automat;

import automat.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

public class ObstkuchenTest {

    @Test
    public void getObstsorteValidTest(){
        HashSet<Allergen> allergene = new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss));
        ObstkuchenImpl kuchen = new ObstkuchenImpl(new HerstellerImpl("Guido"), allergene, 500, Duration.ofDays(4), new BigDecimal("4.50"));

        Assertions.assertEquals("Obstkuchen", kuchen.getName());
    }
}
