package tests;

import automat.Allergen;
import automat.Hersteller;
import automat.HerstellerImpl;
import kuchen.KremkuchenImpl;
import kuchen.KuchenVerkaufsObjektImpl;
import kuchen.ObstkuchenImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;

class KuchenTest {
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
}