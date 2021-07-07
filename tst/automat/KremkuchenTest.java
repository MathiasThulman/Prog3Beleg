package automat;

import automat.Allergen;
import automat.HerstellerImpl;
import automat.KremkuchenImpl;
import automat.Kremsorte;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

public class KremkuchenTest {

    @Test
    public void getKremsorteValid(){
        HashSet<Allergen> allergene = new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss));
        KremkuchenImpl kuchen = new KremkuchenImpl(new HerstellerImpl("Guido"), allergene, 500, Duration.ofDays(4), new BigDecimal("4.50"), new Kremsorte("Humus"));

        Assertions.assertEquals("Humus", kuchen.getKremsorte());
    }
}
