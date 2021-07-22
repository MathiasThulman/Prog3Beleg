package automat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

class KuchVerkaufsObjektImplTest {


    private Date date1;
    private int fn1;
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    @BeforeEach
    public void setUp(){
        date1 = new Date(2020, 4, 9);
        fn1 = 4;
        allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss));
    }

    @Test
    void getHerstellerValid() {
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("Adidas", kuch1.getHersteller().getName());
    }

    @Test
    void getAllergeneValid() {
        HashSet<Allergen> allergList2 = new HashSet(Arrays.asList(Allergen.Haselnuss, Allergen.Sesamsamen));
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList2, 300, Duration.ofDays(3), new BigDecimal(20));
        //expected to contain Sesamsamen and haselnuss
        Assertions.assertTrue(kuch1.getAllergene().contains(Allergen.Sesamsamen) && kuch1.getAllergene().contains(Allergen.Haselnuss));
    }

    @Test
    void getNaehrwertValid() {
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals(300, kuch1.getNaehrwert());
    }

    @Test
    void getHaltbarkeitValid() {
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals(Duration.ofDays(3), kuch1.getHaltbarkeit());
    }


    @Test
    public void getPreisTestValid(){
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("20", kuch1.getPreis().toString());
    }

    @Test
    public void getInspectionsDatumValid(){
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        kuch1.setInspektionsDatum(date1);

        Assertions.assertEquals(date1, kuch1.getInspektionsdatum());
    }

    @Test
    public void getFachnummerValid(){
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        kuch1.setFachNummer(fn1);

        Assertions.assertEquals(fn1, kuch1.getFachnummer());
    }

    @Test
    public void getNameValid(){
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("Langweiliger Kuchenboden", kuch1.getName());
    }

    @Test
    public void toStringWithDatumValid(){
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));
        kuch1.setInspektionsDatum(new Date(2020,6,6));
        kuch1.setEinfuegeDatum(new Date(2021,07,22));

        Assertions.assertEquals("Langweiliger Kuchenboden, Adidas, [Erdnuss], 3, Tue Jul 06 00:00:00 CEST 3920, 20", kuch1.toString());
    }

    @Test
    public void toStringWithoutDatumValid(){
        KuchenVerkaufsObjektImpl kuch1 = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("Langweiliger Kuchenboden, Adidas, [Erdnuss], 3, kein Inspektionsdatum, 20", kuch1.toString());
    }
}