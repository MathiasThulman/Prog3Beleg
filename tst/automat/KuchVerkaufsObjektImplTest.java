package automat;

import kuchen.KremkuchenImpl;
import kuchen.ObstkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

class KuchVerkaufsObjektImplTest {

    private final Date date1 = new Date(2020, 4, 9);
    private final int fn1 = 4;
    private static final String AIOLI = "aioli";
    private final String MARACUJA = "Maracuja";
    private final Hersteller herst1 = new HerstellerImpl("Adidas");
    private final int naehr1 = 300;
    private final BigDecimal preis1 = new BigDecimal(500);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    private final Duration dur1 = Duration.ofDays(5);
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, naehr1, dur1, preis1, AIOLI);
    ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst1, allergList1, naehr1, dur1, preis1, MARACUJA);

    @Test
    void getHerstellerValid() {
        Assertions.assertEquals(herst1, kuch1.getHersteller());
    }

    @Test
    void getAllergeneValid() {
        //expected to contain erdnuss and haselnuss
        Assertions.assertTrue(kuch1.getAllergene().contains(Allergen.Erdnuss) && kuch1.getAllergene().contains(Allergen.Haselnuss));
    }

    @Test
    void getNaehrwertValid() {
        Assertions.assertEquals(naehr1, kuch1.getNaehrwert());
    }

    @Test
    void getHaltbarkeitValid() {
        Assertions.assertEquals(dur1, kuch1.getHaltbarkeit());
    }

    @Test
    void getObstsorteValid(){
        Assertions.assertEquals(MARACUJA, kuch2.getObstsorte());
    }

    @Test
    void getKremsortValid(){
        Assertions.assertEquals(AIOLI, kuch1.getKremsorte());
    }

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