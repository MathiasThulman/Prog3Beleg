package automat;

import automat.Allergen;
import automat.Hersteller;
import automat.HerstellerImpl;
import kuchen.KremkuchenImpl;
import kuchen.ObstkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class VerkaubsOjbektTests {
    private final Date date1 = new Date(2020, 4, 9);
    private final int fn1 = 4;
    private static final String AIOLI = "aioli";
    private Hersteller herst1 = new HerstellerImpl("Adidas");
    private final int naehr1 = 300;
    private final BigDecimal preis1 = new BigDecimal(500);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    private Duration dur1 = Duration.ofDays(5);
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, naehr1, dur1, preis1, AIOLI);

    @Test
    public void getPreisTestValid(){
        Assertions.assertEquals(preis1, kuch1.getPreis());
    }

    @Test
    public void getInspectionsDatumValid(){
        kuch1.setInspektionsDatum(date1);

        Assertions.assertEquals(date1, kuch1.getInspektionsdatum());
    }

    @Test
    public void getFachnummerValid(){
        kuch1.setFachNummer(fn1);

        Assertions.assertEquals(fn1, kuch1.getFachnummer());
    }
}
