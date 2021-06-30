package automat;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

class AutomatTest {
    private final int fn1 = 246;
    private final int negFn = -444;
    private final String MASCARPONE = "Mascarpone";
    private final String SENF = "Senf";
    private final String KIRSCHE = "Kirsche";
    private final String ERDBEERE = "Erdbeere";
    private final String BENJAMIN = "benjamin";
    private final String BLÜMCHEN = "blümchen";
    private final String MOSES = "moses";
    Hersteller herst1 = new HerstellerImpl(BENJAMIN);
    Hersteller herst2 = new HerstellerImpl(BLÜMCHEN);
    Hersteller herst3 = new HerstellerImpl(MOSES);
    Duration dur1 = Duration.ofDays(4);
    LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), MASCARPONE);
    ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250), KIRSCHE);
    ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300), MASCARPONE, ERDBEERE);
    KremkuchenImpl kuch4 = new KremkuchenImpl(herst3, allergList1, 250, dur1, new BigDecimal(400), SENF);

    public Automat getAutomat() {
        return new Automat(500);
    }

    //addKuchen tests
    @Test
    public void addKuchenValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);

            //check if the kuchen was added at first fachnummer 0
            Assertions.assertEquals(0, auto.getKuchen(0).getFachnummer());
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void addKuchenFullAutomat() {
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
        Automat auto = new Automat(3); 

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch3);
            auto.addKuchen(kuch2);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

    }

    @Test
    public void AddKuchenNoHerstellerTest() {
        Automat auto = getAutomat();

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.addKuchen(kuch1));
    }

    @Test
    public void addKuchenHerstellerNotFound() {
        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        Assertions.assertThrows(InvalidInputException.class, () -> auto.getKuchen(negFn));
    }

    //removeKuchen tests
    @Test
    public void removeKuchenNoSuchElement() {
        Automat auto = getAutomat();

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.removeKuchen(2));
    }

    @Test
    public void removeKuchenValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst3);
            auto.addKuchen(kuch4);
            auto.addKuchen(kuch2);

            auto.removeKuchen(1);
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
        //after removing check if the kuchen is still there
        Assertions.assertThrows(NoSuchElementException.class, () -> auto.getKuchen(1));
    }

    @Test
    public void removeKucheInvalidInput1() {
        Automat auto = getAutomat();

        //test to check if invalid negativ numbers throw exceptions before processing anything
        Assertions.assertThrows(InvalidInputException.class, () -> auto.removeKuchen(-4));
    }

    @Test
    public void removeKucheInvalidInput2() {
        Automat auto = getAutomat();

        //test to check if invalid too big numbers throw exceptions before processing anything
        Assertions.assertThrows(InvalidInputException.class, () -> auto.removeKuchen(700));
    }

    //changeKuchen tests
    @Test
    public void changeKuchenValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            auto.changeKuchen(1, kuch3);
            Assertions.assertEquals(ObsttorteImpl.class, auto.getKuchen(1).getClass());
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
    }

    @Test
    public void changeKuchenInvalidInput(){
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addKuchen(kuch1);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        Assertions.assertThrows(InvalidInputException.class, () -> auto.changeKuchen(-1, kuch2));
    }

    //checkKuchen tests
    @Test
    public void checkKuchenAllValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            //check if all kuchen are returned in in the list
            Assertions.assertEquals(500, auto.checkKuchen().get(0).getNaehrwert());
            Assertions.assertEquals(300, auto.checkKuchen().get(1).getNaehrwert());
            Assertions.assertEquals(400, auto.checkKuchen().get(2).getNaehrwert());
        } catch (AlreadyExistsException | EmptyListException | FullAutomatException e) {
            fail();
        }
    }

    @Test
    public void checkKuchenSpecificValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch4);

            //multiple adds and asserts to see if it can extract multiple kuchen objects properly
            Assertions.assertEquals(KremkuchenImpl.class, auto.checkKuchen(KremkuchenImpl.class).get(0).getClass());
            Assertions.assertEquals(KremkuchenImpl.class, auto.checkKuchen(KremkuchenImpl.class).get(1).getClass());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void checkKuchenEmptyAutomat() {
        Automat auto = getAutomat();

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkKuchen());
    }

    @Test
    public void checkKuchenNoSuchElement() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst3);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch4);
        } catch (AlreadyExistsException | FullAutomatException e) {
            fail();
        }

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.checkKuchen(ObsttorteImpl.class));
    }

    //addHersteller tests
    @Test
    public void addHerstellerValid() {
        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst2);

            auto.removeHersteller(herst1.getName());

            Assertions.assertFalse(auto.getHersteller().contains(herst1));
        } catch (AlreadyExistsException | EmptyListException e) {
           fail();
        }
    }

    @Test
    public void removeHerstellerNoSuchElement() {
        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

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

    //getManufacturer tests
    @Test
    public void getHerstellerValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);

            auto.addHersteller(herst3);

            Assertions.assertEquals(BENJAMIN, auto.getHersteller().get(0).getName());
            Assertions.assertEquals(MOSES, auto.getHersteller().get(1).getName());
        } catch (AlreadyExistsException | EmptyListException e) {
            fail();
        }
    }

    @Test
    public void getHerstellerEmptyList(){
        Automat auto = getAutomat();

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkHersteller());
    }

    //checkHerrsteller Tests
    @Test
    void checkHerstellerValid() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);
            auto.addKuchen(kuch2);

            //multiple adds and assert to check if the hashmap adds and increments properly
            Assertions.assertEquals(1, auto.checkHersteller().get(BENJAMIN));
            Assertions.assertEquals(2, auto.checkHersteller().get(MOSES));
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            fail();
        }
    }

    @Test
    void checkHerstellerEmptyList() {
        Automat auto = getAutomat();

        //Exception should be thrown if there are no kuchen in the automat
        Assertions.assertThrows(EmptyListException.class, () -> auto.checkHersteller());
    }

    //swapKuchen
    @Test
    void swapKuchenValidTest() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);
            auto.addHersteller(herst3);
            auto.addKuchen(kuch1);
            auto.addKuchen(kuch2);

            auto.swapKuchen(0, 1);
            Assertions.assertEquals(300, auto.getKuchen(1).getNaehrwert());
            Assertions.assertEquals(400, auto.getKuchen(0).getNaehrwert());
        } catch (AlreadyExistsException | FullAutomatException | InvalidInputException e) {
            fail();
        }
    }

    @Test
    void swapKuchenInvalidInput() {
        Automat auto = getAutomat();

        Assertions.assertThrows(InvalidInputException.class, () -> auto.swapKuchen(0, 600));
    }

    @Test
    void swapKuchenNoSuchElement()  {
        Automat automat = getAutomat();

        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

        //create new allergene lists
        LinkedList<Allergen> allerList2 = new LinkedList<>(Arrays.asList(Allergen.Haselnuss, Allergen.Erdnuss));
        LinkedList<Allergen> allerList3 = new LinkedList<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss));

        KuchenVerkaufsObjektImpl kuch6 = mock(KremkuchenImpl.class);
        when(kuch6.getAllergene()).thenReturn(allerList2);
        KuchenVerkaufsObjektImpl kuch7 = mock(KremkuchenImpl.class);
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
        Automat auto = getAutomat();

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkAllergen());
    }

    @Test
    public void checkAllergenNoneFound() {
        Automat auto = getAutomat();

        try {
            auto.addHersteller(herst1);

            LinkedList<Allergen> allerList0 = new LinkedList<>();

            KuchenVerkaufsObjektImpl kuch6 = mock(KremkuchenImpl.class);
            when(kuch6.getAllergene()).thenReturn(allerList0);
            KuchenVerkaufsObjektImpl kuch7 = mock(KremkuchenImpl.class);
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
        Automat auto = getAutomat();

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
        Automat auto = getAutomat();

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
}