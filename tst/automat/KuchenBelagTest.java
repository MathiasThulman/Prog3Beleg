package automat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class KuchenBelagTest {
    private String BENJAMIN;
    private String MASCARPONE;
    private Duration dur1;
    private HashSet<Allergen> allergList1;

    @BeforeEach
    public void setUp(){
        MASCARPONE = "Mascarpone";
        BENJAMIN = "benjamin";
        dur1 = Duration.ofDays(3);
        allergList1 = new HashSet<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    }

    @Test
    public void getHerstellerValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(BENJAMIN, kuch1.getHersteller().getName());
    }

    @Test
    public void getAllergeneValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertTrue(kuch1.getAllergene().contains(Allergen.Erdnuss) && kuch1.getAllergene().contains(Allergen.Haselnuss));
    }

    @Test
    public void getNaehrwertValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(600, kuch1.getNaehrwert());
    }

    @Test
    public void getHaltbarkeitValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, Duration.ofDays(3), new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(5), allergList1 , boden);
        KuchenDekorator kuch2 = new KuchenBelag("Ayran", new BigDecimal(400), 200, Duration.ofDays(4), allergList1, kuch1);
        //multiple layers to see if it can grab the lowest from the bottom

        Assertions.assertEquals(Duration.ofDays(3).getSeconds(), kuch2.getHaltbarkeit().getSeconds());
    }

    @Test
    public void getPreisValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals("1000", kuch1.getPreis().toString());
    }

    @Test
    public void setFachnummerValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        kuch1.setFachNummer(1);
        Assertions.assertEquals(1, kuch1.getFachnummer());

    }

    @Test
    public void getFachnummerValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        boden.setFachNummer(1);
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(1, kuch1.getFachnummer());
    }

    @Test
    public void getNameValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenDekorator kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals("Kremkuchen Mascarpone", kuch1.getName());
    }



}
