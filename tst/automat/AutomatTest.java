package automat;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import observer.AutomatChangeObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

class AutomatTest {

    private int negFn = -444;
    private String MASCARPONE;
    private String BENJAMIN;
    private String BLÜMCHEN;
    private String MOSES;
    Duration dur1;
    private HashSet<Allergen> allergList1;

    @BeforeEach
    public void setUp(){
        negFn = -444;
        MASCARPONE = "Mascarpone";
        BENJAMIN = "benjamin";
        BLÜMCHEN = "blümchen";
        MOSES = "moses";
        dur1 = Duration.ofDays(4);
        allergList1 = new HashSet<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    }


    //addKuchen tests
    @Test
    public void addKuchenValid() {
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl boden = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        KuchenKomponente kuch1 = new KuchenBelag(MASCARPONE, new BigDecimal("500"), 300, Duration.ofDays(4), allergList1 , boden);

        Automat auto = new Automat(10);

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);

            //check if the kuchen was added at first fachnummer 0
            Assertions.assertEquals(1, auto.getKuchenCounter());
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }
    }

    @Test
    public void addKuchenFullAutomat() {
        Hersteller herst3 = new HerstellerImpl(MOSES);
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));
        KremkuchenImpl kuch4 = new KremkuchenImpl(herst3, allergList1, 250, dur1, new BigDecimal(400));

        Automat auto = new Automat(2); //create automate with fewer slots so just 3 have to be added

        try {
            auto.addHersteller(herst3);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch4);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }
        Assertions.assertThrows(FullAutomatException.class, () -> auto.addKuchen(kuch2));
    }

    @Test
    public void addKuchenFullAutomatFringe() {
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300));

        Automat auto = new Automat(2);

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch3);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

    }

    @Test
    public void AddKuchenNoHerstellerTest() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.addKuchen(kuch1));
    }

    @Test
    public void addKuchenHerstellerNotFound() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));

        try {
            auto.addHersteller(herst1);
        } catch (AlreadyExistsException e) {
            fail();
        }

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.addKuchen(kuch2));
    }

    //getKuchen tests
    @Test
    public void getKuchenTestValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch3);

            //test correct kuchen by naehrwert in this  case 500
            Assertions.assertEquals(500, auto.getKuchen(0).getNaehrwert());
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
    }


    @Test
    public void getKuchenNegativeNumbers() {
        Automat auto = new Automat(10);

        Assertions.assertThrows(InvalidInputException.class, () -> auto.getKuchen(negFn));
    }

    //removeKuchen tests
    @Test
    public void removeKuchenNoSuchElement() {
        Automat auto = new Automat(10);

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.removeKuchen(2));
    }

    @Test
    public void removeKuchenValid() {
        Automat auto = new Automat(10);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        KremkuchenImpl kuch4 = new KremkuchenImpl(herst3, allergList1, 250, dur1, new BigDecimal(400));

        try {
            auto.addHersteller(herst3);
            auto.addKuchen(kuch4);

            auto.removeKuchen(0);
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
        //kuchencounter should be 0 after removen the kuchen
        Assertions.assertEquals(0, auto.getKuchenCounter());
    }

    @Test
    public void removeKucheInvalidInput1() {
        Automat auto = new Automat(10);

        //test to check if invalid negativ numbers throw exceptions before processing anything
        Assertions.assertThrows(InvalidInputException.class, () -> auto.removeKuchen(-4));
    }

    @Test
    public void removeKucheInvalidInput2() {
        Automat auto = new Automat(10);

        //test to check if invalid too big numbers throw exceptions before processing anything
        Assertions.assertThrows(InvalidInputException.class, () -> auto.removeKuchen(700));
    }

    //not necessary if change Kuchen is on private
//    @Test
//    public void changeKuchenValid() {
//        Automat auto = new Automat(10);
//        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
//        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE));
//        ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300), new Kremsorte(MASCARPONE), new Obstsorte(KIRSCHE));
//
//
//        try {
//            auto.addHersteller(herst1);
//            auto.addKuchen(kuch1);
//
//            auto.changeKuchen(0, kuch3);
//            Assertions.assertEquals(ObsttorteImpl.class, auto.getKuchen(0).getClass());
//        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
//            fail();
//        }
//    }

//    @Test
//    public void changeKuchenInvalidInput(){
//        Automat auto = new Automat(10);
//        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
//        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), new Kremsorte(MASCARPONE));
//        Hersteller herst3 = new HerstellerImpl(MOSES);;
//        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250), new Obstsorte(KIRSCHE));
//
//        try {
//            auto.addHersteller(herst1);
//            auto.addKuchen(kuch1);
//        } catch (AlreadyExistsException | FullAutomatException e) {
//            fail();
//        }
//
//        Assertions.assertThrows(InvalidInputException.class, () -> auto.changeKuchen(-1, kuch2));
//    }

    //checkKuchen tests
    @Test
    public void checkKuchenAllValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));
        ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300));

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            //multiple asserts to check if all kuchen are properly returned in in the list
            Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
            Assertions.assertEquals(300, auto.checkKuchen().get(1).getNaehrwert());
            Assertions.assertEquals(400, auto.checkKuchen().get(2).getNaehrwert());
        } catch (AlreadyExistsException | EmptyListException | FullAutomatException e) {
            fail();
        }
    }

    @Test
    public void checkKuchenSpecificValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst1, allergList1, 400, dur1, new BigDecimal(250));
        KremkuchenImpl kuch4 = new KremkuchenImpl(herst1, allergList1, 250, dur1, new BigDecimal(400));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch4);

            //multiple adds and asserts to see if it can extract multiple kuchen objects properly
            Assertions.assertEquals(KremkuchenImpl.class, auto.checkKuchen("Kremkuchen").get(0).getClass());
            Assertions.assertEquals(KremkuchenImpl.class, auto.checkKuchen("Kremkuchen").get(1).getClass());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void checkKuchenEmptyAutomat() {
        Automat auto = new Automat(10);

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkKuchen());
    }

    @Test
    public void checkKuchenNoSuchElement() {
        Automat auto = new Automat(10);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));
        KremkuchenImpl kuch4 = new KremkuchenImpl(herst3, allergList1, 250, dur1, new BigDecimal(400));

        try {
            auto.addHersteller(herst3);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch4);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.checkKuchen("ObsttorteImpl"));
    }

    //addHersteller tests
    @Test
    public void addHerstellerValid() {
        Automat auto = new Automat(10);

        Hersteller manu4 = mock(Hersteller.class);
        when(manu4.getName()).thenReturn("Obama");

        try {
            auto.addHersteller(manu4);

            Assertions.assertEquals("Obama", auto.getHersteller().get(0).getName());
        } catch (AlreadyExistsException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void addHerstellerNameAlreadyExists()  {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);

        try {
            auto.addHersteller(herst1);
        } catch (AlreadyExistsException e) {
            fail();
        }
        Hersteller manu11 = new HerstellerImpl("benjamin");

        Assertions.assertThrows(AlreadyExistsException.class, () -> auto.addHersteller(manu11));
    }

    //removeHerstellerTest
    @Test
    public void removeHerstellervalid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLÜMCHEN);;

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst2);

            auto.removeHersteller(BENJAMIN);

            Assertions.assertFalse(auto.getHersteller().contains(herst1));
        } catch (AlreadyExistsException | EmptyListException e) {
           fail();
        }
    }

    @Test
    public void removeHerstellerNoSuchElement() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLÜMCHEN);

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst2);
        } catch (AlreadyExistsException e) {
            fail();
        }

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.removeHersteller(MOSES));
    }

    @Test
    public void removeHerstellerWithKuchen() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
        } catch (AlreadyExistsException | FullAutomatException e) {
           fail();
        }

        auto.removeHersteller(BENJAMIN);
        //cake with hersteller should now have been removed as well
        Assertions.assertThrows(NoSuchElementException.class, () -> auto.getKuchen(0));
    }

    //getHersteller tests
    @Test
    public void getHerstellerValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);

        try {
            auto.addHersteller(herst1);

            Assertions.assertEquals(BENJAMIN, auto.getHersteller().get(0).getName());
        } catch (AlreadyExistsException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void getHerstellerEmptyList(){
        Automat auto = new Automat(10);

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkHersteller());
    }

    //checkHerrsteller Tests
    @Test
    void checkHerstellerValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst3 = new HerstellerImpl(MOSES);;
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));
        ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300));

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch3);

            //multiple adds and assert to check if the hashmap adds and increments properly
            Assertions.assertEquals(2, auto.checkHersteller().get(BENJAMIN));
            Assertions.assertEquals(1, auto.checkHersteller().get(MOSES));
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    void checkHerstellerEmptyList() {
        Automat auto = new Automat(10);

        //Exception should be thrown if there are no kuchen in the automat
        Assertions.assertThrows(EmptyListException.class, () -> auto.checkHersteller());
    }

    //swapKuchen
    @Test
    void swapKuchenValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst3 = new HerstellerImpl(MOSES);;
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            auto.swapKuchen(0, 1);
            //2 Asserts to check if both kuchen have been swapped properly
            Assertions.assertEquals(300, auto.getKuchen(1).getNaehrwert());
            Assertions.assertEquals(400, auto.getKuchen(0).getNaehrwert());
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
    }

    @Test
    void swapKuchenInvalidInput() {
        Automat auto = new Automat(10);

        Assertions.assertThrows(InvalidInputException.class, () -> auto.swapKuchen(0, 600));
    }

    @Test
    void swapKuchenNoSuchElement()  {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.swapKuchen(0, 3));
    }


    //check Allergen Tests
    @Test
    public void checkAllergenValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);

        //create new allergene lists
        LinkedList<Allergen> allerList2 = new LinkedList<>(Arrays.asList(Allergen.Haselnuss, Allergen.Erdnuss));
        LinkedList<Allergen> allerList3 = new LinkedList<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss));

        BasisKuchenImpl kuch6 = mock(KremkuchenImpl.class);
        when(kuch6.getAllergene()).thenReturn(allerList2);
        BasisKuchenImpl kuch7 = mock(KremkuchenImpl.class);
        when(kuch7.getAllergene()).thenReturn(allerList3);

        when(kuch6.getHersteller()).thenReturn(herst1);//mock hersteller so we can actually add them to the automat
        when(kuch7.getHersteller()).thenReturn(herst1);

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch6);
            auto.addKuchen(kuch7);

            //System.out.println(auto.checkAllergen());//Haselnuss Erdnuss Gluten Expected
            Assertions.assertTrue(auto.checkAllergen().contains(Allergen.Erdnuss) && auto.checkAllergen().contains(Allergen.Haselnuss) && auto.checkAllergen().contains(Allergen.Gluten));
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void checkAllergenEmptyList() {
        Automat auto = new Automat(10);

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkAllergen());
    }

    @Test
    public void checkAllergenNoneFound() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);

        try {
            auto.addHersteller(herst1);

            LinkedList<Allergen> allerList0 = new LinkedList<>();

            BasisKuchenImpl kuch6 = mock(KremkuchenImpl.class);
            when(kuch6.getAllergene()).thenReturn(allerList0);
            BasisKuchenImpl kuch7 = mock(KremkuchenImpl.class);
            when(kuch7.getAllergene()).thenReturn(allerList0);

            when(kuch6.getHersteller()).thenReturn(herst1);//mock hersteller so we can actually add them to the automat
            when(kuch7.getHersteller()).thenReturn(herst1);

            auto.addKuchen(kuch6);
            auto.addKuchen(kuch7);

            //list is supposed to be empty when kuchen exist but none of them have allergens
            Assertions.assertTrue(auto.checkAllergen().isEmpty());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void checkAbsentAllergenValid() {
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);

            Assertions.assertTrue(auto.checkAbsentAllergen().contains(Allergen.Gluten) && auto.checkAbsentAllergen().contains(Allergen.Sesamsamen));
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }


    //setInspectionDate Test
    @Test
    public void setInspectionDateValid(){
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);

        try {
            auto.addHersteller(herst1);
            KremkuchenImpl kuchenMock = mock(KremkuchenImpl.class);
            when(kuchenMock.getHersteller()).thenReturn(herst1);

            auto.addKuchen(kuchenMock);
            auto.setInspectionDate(0);

            //called 2 times since it is also called on adding the cake
           verify(kuchenMock, times(2)).setInspektionsDatum(any());
        } catch (AlreadyExistsException | InvalidInputException | FullAutomatException e) {
            fail();
        }
    }

    @Test
    public void setInspectionDateNoSuchElement(){
        Automat auto = new Automat(10);

        assertThrows(NoSuchElementException.class, () -> auto.setInspectionDate(2));
    }

    @Test
    public void getSizeValid(){
        Automat auto = new Automat(10);

        assertEquals(10, auto.getSize());
    }

    @Test
    public void getKuchenCounterValid(){
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));


        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }
        assertEquals(1, auto.getKuchenCounter());
    }

    @Test
    public void addObserverValid(){
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);

        AutomatChangeObserver obs = mock(AutomatChangeObserver.class);
        auto.addObserver(obs);

        try {
            auto.addHersteller(herst1);
        } catch (AlreadyExistsException e) {
            fail();
        }
        //check if observer calls update on adding a hersteller to see if it has been succesfully added
        verify(obs).update();
    }

    @Test
    public void removeObserverValid(){
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        AutomatChangeObserver obs = new AutomatChangeObserver(auto); //observer automatically adds itself to automat when called in constructor

        auto.removeObserver(obs);
        try {
            final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos1));
            auto.addHersteller(herst1);
            //String is expected to be empty as observer should print into System.out after being removed
            Assertions.assertTrue(bos1.toString().isEmpty());
        } catch (AlreadyExistsException e) {
            fail();
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    public void notifyObserversValid(){
        Automat auto = new Automat(10);
        AutomatChangeObserver obs = mock(AutomatChangeObserver.class);
        auto.addObserver(obs);

        auto.notifyObservers();

        verify(obs).update();
    }

    @Test
    public void kapselungTest(){
        Automat auto = new Automat(10);
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst3 = new HerstellerImpl(MOSES);;
        KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500));
        ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250));

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            List<KuchenKomponente> tempList = auto.checkKuchen();
            tempList.clear();

            assertFalse(auto.checkKuchen().isEmpty());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            e.printStackTrace();
        }
    }
}