package automat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

class BasisKuchenImplTest {


    private Date date1;
    private int fn1;
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    @BeforeEach
    public void setUp(){
        date1 = new GregorianCalendar(2020, 4, 9).getTime();
        fn1 = 4;
        allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss));
    }

    @Test
    void getHerstellerValid() {
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("Adidas", kuch1.getHersteller().getName());
    }

    @Test
    void getAllergeneValid() {
        HashSet<Allergen> allergList2 = new HashSet(Arrays.asList(Allergen.Haselnuss, Allergen.Sesamsamen));
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList2, 300, Duration.ofDays(3), new BigDecimal(20));
        //expected to contain Sesamsamen and haselnuss
        Assertions.assertTrue(kuch1.getAllergene().contains(Allergen.Sesamsamen) && kuch1.getAllergene().contains(Allergen.Haselnuss));
    }

    @Test
    void getNaehrwertValid() {
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals(300, kuch1.getNaehrwert());
    }

    @Test
    void getHaltbarkeitValid() {
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals(Duration.ofDays(3), kuch1.getHaltbarkeit());
    }


    @Test
    public void getPreisTestValid(){
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("20", kuch1.getPreis().toString());
    }

    @Test
    public void getInspectionsDatumValid(){
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        kuch1.setInspektionsDatum(date1);

        Assertions.assertEquals(date1.getTime(), kuch1.getInspektionsdatum().getTime());
    }

    @Test
    public void getFachnummerValid(){
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        kuch1.setFachNummer(fn1);

        Assertions.assertEquals(fn1, kuch1.getFachnummer());
    }

    @Test
    public void getNameValid(){
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("Langweiliger Kuchenboden", kuch1.getName());
    }

    @Test
    public void toStringWithDatumValid(){
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));
        Date date = new GregorianCalendar(2021, 7, 21).getTime();
        kuch1.setInspektionsDatum(date);
        kuch1.setEinfuegeDatum(date);

        //2 seperate assertions as i cannot test the given remaining duration of the kuchen since that is based on the current date
        Assertions.assertTrue(kuch1.toString().contains("0, Langweiliger Kuchenboden, Adidas, [Erdnuss]"));
        Assertions.assertTrue(kuch1.toString().contains("Sat Aug 21 00:00:00 CEST 2021"));
    }

    @Test
    public void toStringWithoutDatumValid(){
        BasisKuchenImpl kuch1 = new BasisKuchenImpl(new HerstellerImpl("Adidas"), allergList1, 300, Duration.ofDays(3), new BigDecimal(20));

        Assertions.assertEquals("0, Langweiliger Kuchenboden, Adidas, [Erdnuss], 3, kein Inspektionsdatum, 20", kuch1.toString());
    }
}