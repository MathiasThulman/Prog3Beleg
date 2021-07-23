package automat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

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
        allergList1 = new HashSet<>(Arrays.asList(Allergen.Erdnuss));
    }

    @Test
    public void getHerstellerValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(BENJAMIN, kuch1.getHersteller().getName());
    }

    @Test
    public void getAllergeneValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        HashSet<Allergen> allergList2 = new HashSet(Arrays.asList(Allergen.Haselnuss));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList2 , boden);

        Assertions.assertTrue(kuch1.getAllergene().contains(Allergen.Erdnuss) && kuch1.getAllergene().contains(Allergen.Haselnuss));
    }

    @Test
    public void getNaehrwertValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(600, kuch1.getNaehrwert());
    }

    @Test
    public void getHaltbarkeitBottomValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, Duration.ofDays(3), new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(5), allergList1 , boden);
        KuchenKomponente kuch2 = new KuchenBelag("Ayran", new BigDecimal(400), 200, Duration.ofDays(4), allergList1, kuch1);
        //multiple layers to see if it can grab the lowest from the bottom

        Assertions.assertEquals(Duration.ofDays(3).getSeconds(), kuch2.getHaltbarkeit().getSeconds());
    }

    @Test
    public void getHaltbarkeitTopValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, Duration.ofDays(5), new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(3), allergList1 , boden);

        //to see if the function fetches the right duration when its at the highest layer
        Assertions.assertEquals(Duration.ofDays(3).getSeconds(), kuch1.getHaltbarkeit().getSeconds());
    }

    @Test
    public void getPreisValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals("1000", kuch1.getPreis().toString());
    }

    @Test
    public void setFachnummerValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        kuch1.setFachNummer(1);
        Assertions.assertEquals(1, kuch1.getFachnummer());

    }

    @Test
    public void getFachnummerValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        boden.setFachNummer(1);
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(1, kuch1.getFachnummer());
    }

    @Test
    public void getNameValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals("Mascarpone Kremkuchen", kuch1.getName());
    }

    @Test
    public void getInspektionDatumValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        Date date = new GregorianCalendar(2020,6,6).getTime();
        boden.setInspektionsDatum(date);
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        System.out.println(kuch1.getInspektionsdatum());
        Assertions.assertEquals(date.getTime(), kuch1.getInspektionsdatum().getTime());
    }

    @Test
    public void setInspeaktionsDatumValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);
        Date date = new GregorianCalendar(2020,6,6).getTime();
        kuch1.setInspektionsDatum(date);

        //to see if the function is properly passed down
        Assertions.assertEquals(date.getTime(), kuch1.getInspektionsdatum().getTime());
    }

    @Test
    public void getEinfuegeDatumValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        Date date = new GregorianCalendar(2020,6,6).getTime();
        boden.setEinfuegeDatum(date);
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        Assertions.assertEquals(date.getTime(), kuch1.getEinfuegeDatum().getTime());
    }

    @Test
    public void setEinfuegeDatum(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        Date date = new Date(2020,6,6);
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);
        kuch1.setEinfuegeDatum(date);

        //test to see if the highest layer passes down the function properly
        Assertions.assertEquals(date.getTime(), kuch1.getEinfuegeDatum().getTime());

    }

    @Test
    public void toStringWithoutDateValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal(500), 300, Duration.ofDays(4), allergList1 , boden);
        kuch1.setFachNummer(1);

        Assertions.assertEquals("1, Mascarpone Kremkuchen, benjamin, [Erdnuss], 3, kein Inspektionsdatum, 1000", kuch1.toString());
    }

    @Test
    public void toStringWithDateValid(){
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        Date date = new GregorianCalendar(2020,6,6).getTime();
        boden.setEinfuegeDatum(date);
        boden.setInspektionsDatum(date);
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        //two assertions since i cannot test remaining duration of toString as that is based on the current time
        Assertions.assertTrue(kuch1.toString().contains("0, Mascarpone Kremkuchen, benjamin, [Erdnuss]"));
        Assertions.assertTrue(kuch1.toString().contains("Mon Jul 06 00:00:00 CEST 2020, 1000"));
    }

}
