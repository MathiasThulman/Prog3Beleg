package tests;

import automat.*;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

class AutomatTests {
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
    KuchenVerkaufsObjektImpl kuch2 = new ObstkuchenImpl(herst3, allergList1, 400, dur1, new BigDecimal(250), KIRSCHE);
    KuchenVerkaufsObjektImpl kuch3 = new ObsttorteImpl(herst1, allergList1, 500, dur1, new BigDecimal(300), MASCARPONE, ERDBEERE);
    KuchenVerkaufsObjektImpl kuch4 = new KremkuchenImpl(herst3, allergList1,250 , dur1, new BigDecimal(400), SENF);

    public Automat getAutomat(){
        return new AutomatImpl(500);
    }

    //addKuchen tests
    @Test
    public void addKuchenValid() throws InvalidInputException, AlreadyExistsException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        //check if the kuchen was added at first fachnummer 0
        Assertions.assertEquals(0, auto.getKuchen(0).getFachnummer());
    }

    @Test
    public void addKuchenFullAutomat() throws AlreadyExistsException, FullAutomatException {
        Automat auto = new AutomatImpl(2); //create automate with fewer slots so just 3 have to be added

        auto.addHersteller(herst3);
        auto.addKuchen(kuch2);
        auto.addKuchen(kuch4);
        Assertions.assertThrows(FullAutomatException.class, () -> auto.addKuchen(kuch2));
    }

    @Test
    public void addKuchenFullAutomatFringe() throws AlreadyExistsException, FullAutomatException {
        Automat auto = getAutomat(); //size is 500

        auto.addHersteller(herst1);

        //adding any 500 cakes by loop just to see if they are all added properly without throwing an exception
        for(int i = 0; i < 500; i++){
            auto.addKuchen(kuch1);
        }
    }

    @Test
    public void AddKuchenNoHerstellerTest(){
        Automat auto = getAutomat();

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.addKuchen(kuch1));
    }

    @Test
    public void addKuchenHerstellerNotFound() throws AlreadyExistsException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.addKuchen(kuch2));
    }

    //getKuchen tests
    @Test
    public void getKuchenTestValid() throws AlreadyExistsException, InvalidInputException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addKuchen(kuch3);

        //test correct kuchen by naehrwert in this  case 500
        Assertions.assertEquals(500, auto.getKuchen(0).getNaehrwert());
    }

   
    @Test
    public void getKuchenNegativeNumbers() throws AlreadyExistsException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        Assertions.assertThrows(InvalidInputException.class, () -> auto.getKuchen(negFn));
    }

    //removeKuchen tests
    @Test
    public void removeKuchenNoSuchElement() throws NoSuchElementException {
       Automat auto = getAutomat();

       Assertions.assertThrows(NoSuchElementException.class, () -> auto.removeKuchen(2));
    }

    @Test
    public void removeKuchenValid() throws AlreadyExistsException, InvalidInputException, NoSuchElementException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst3);
        auto.addKuchen(kuch4);
        auto.addKuchen(kuch2);

        auto.removeKuchen(1);
        //after removing check if the kuchen is still there
        Assertions.assertThrows(NoSuchElementException.class, () -> auto.getKuchen(1));
    }

    @Test
    public void removeKucheInvalidInput1(){
        Automat auto = getAutomat();

        //test to check if invalid negativ numbers throw exceptions before processing anything
        Assertions.assertThrows(InvalidInputException.class, () -> auto.removeKuchen(-4));
    }

    @Test
    public void removeKucheInvalidInput2(){
        Automat auto = getAutomat();

        //test to check if invalid too big numbers throw exceptions before processing anything
        Assertions.assertThrows(InvalidInputException.class, () -> auto.removeKuchen(700));
    }

    //changeKuchen tests
    @Test
    public void changeKuchenValid() throws AlreadyExistsException, InvalidInputException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst3);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);

        auto.changeKuchen(1, kuch3);
        Assertions.assertEquals(ObsttorteImpl.class, auto.getKuchen(1).getClass());
    }

    @Test
    public void changeKuchenNoSuchElement() throws AlreadyExistsException, NoSuchElementException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.changeKuchen(fn1, kuch2));
    }

    @Test
    public void changeKuchenInvalidInput() throws AlreadyExistsException, NoSuchElementException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        Assertions.assertThrows(InvalidInputException.class, () -> auto.changeKuchen(-1, kuch2));
    }

    //checkKuchen tests
    @Test
    public void checkKuchenAllValid() throws AlreadyExistsException, InvalidInputException, EmptyListException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst3);
        auto.addKuchen(kuch3);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);

        //check if all kuchen are returned in in the list
        Assertions.assertEquals(500, auto.checkKuchen()[0].getNaehrwert());
        Assertions.assertEquals(300, auto.checkKuchen()[1].getNaehrwert());
        Assertions.assertEquals(400, auto.checkKuchen()[2].getNaehrwert());
    }

    @Test
    public void checkKuchenSpecificValid() throws AlreadyExistsException, InvalidInputException, EmptyListException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst3);
        auto.addKuchen(kuch3);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);
        auto.addKuchen(kuch4);

        //multiple adds and asserts to see if it can extract multiple kuchen objects properly
        Assertions.assertEquals(KremkuchenImpl.class, auto.checkKuchen(kuch1).get(0).getClass());
        Assertions.assertEquals(KremkuchenImpl.class, auto.checkKuchen(kuch1).get(1).getClass());
    }

    @Test
    public void checkKuchenEmptyAutomat() throws EmptyListException{
        Automat auto = getAutomat();

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkKuchen());
    }

    @Test
    public void checkKuchenNoSuchElement() throws AlreadyExistsException, NoSuchElementException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst3);
        auto.addKuchen(kuch2);
        auto.addKuchen(kuch4);

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.checkKuchen(kuch3));
    }

    //addHersteller tests
    @Test
    public void addHerstellerValid() throws AlreadyExistsException, EmptyListException {
        Automat auto = getAutomat();

        Hersteller manu4 = mock(Hersteller.class);
        when(manu4.getName()).thenReturn("Obama");

        auto.addHersteller(manu4);

        Assertions.assertEquals("Obama", auto.getHersteller().get(0).getName());
    }

    @Test
    public void addHerstellerNameAlreadyExists() throws AlreadyExistsException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        Hersteller manu11 = new HerstellerImpl("benjamin");

        Assertions.assertThrows(AlreadyExistsException.class, () -> auto.addHersteller(manu11));
    }

    //removeHerstellerTest
    @Test
    public void removeHerstellervalid() throws AlreadyExistsException, EmptyListException {
        Automat auto =getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst2);

        auto.removeHersteller(herst1.getName());

        Assertions.assertFalse(auto.getHersteller().contains(herst1));
    }

    @Test
    public void removeHerstellerNoSuchElement() throws NoSuchElementException, AlreadyExistsException {
        Automat auto =getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst2);

        Assertions.assertThrows(NoSuchElementException.class, () -> auto.removeHersteller(MOSES));
    }

    @Test
    public void removeHerstellerWithKuchen() throws AlreadyExistsException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addKuchen(kuch1);

        auto.removeHersteller(BENJAMIN);
        //cake with hersteller should now have been removed as well
        Assertions.assertThrows(NoSuchElementException.class, () -> auto.getKuchen(0));
    }

    //getManufacturer tests
    @Test
    public void getHerstellerValid() throws AlreadyExistsException, NoSuchElementException, EmptyListException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);;
        auto.addHersteller(herst3);

        Assertions.assertEquals(BENJAMIN, auto.getHersteller().get(0).getName());
        Assertions.assertEquals(MOSES, auto.getHersteller().get(1).getName());

    }

    @Test
    public void getHerstellerEmptyList() throws EmptyListException {
        Automat auto = getAutomat();

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkHersteller());
    }

    //checkHerrsteller Tests
    @Test
    void checkHerstellerValid() throws AlreadyExistsException, InvalidInputException, EmptyListException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst3);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);
        auto.addKuchen(kuch2);

        //multiple adds and assert to check if the hashmap adds and increments properly
        Assertions.assertEquals(1,auto.checkHersteller().get(BENJAMIN));
        Assertions.assertEquals(2, auto.checkHersteller().get(MOSES));
    }
    @Test
    void checkHerstellerEmptyList(){
        Automat auto = getAutomat();

        //Exception should be thrown if there are no kuchen in the automat
        Assertions.assertThrows(EmptyListException.class, () -> auto.checkHersteller());
    }


    //check Allergen Tests
    @Test
    public void checkAllergenValid() throws EmptyListException, AlreadyExistsException, FullAutomatException {
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

        auto.addHersteller(herst1);
        auto.addKuchen(kuch6);
        auto.addKuchen(kuch7);

        //System.out.println(auto.checkAllergen());//Haselnuss Erdnuss Gluten Expected
        Assertions.assertTrue(auto.checkAllergen().contains(Allergen.Erdnuss) && auto.checkAllergen().contains(Allergen.Haselnuss) && auto.checkAllergen().contains(Allergen.Gluten));
    }

    @Test
    public void checkAllergenEmptyList() throws EmptyListException{
        Automat auto = getAutomat();

        Assertions.assertThrows(EmptyListException.class, () -> auto.checkAllergen());
    }

    @Test
    public void voidCheckAllergenNoneFound() throws EmptyListException, AlreadyExistsException, FullAutomatException {
        Automat auto = getAutomat();

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

        //list is supposed to be empty when kuchen exist but none of them have allergene
        Assertions.assertTrue(auto.checkAllergen().isEmpty());
    }


    //setInspectionDate Test
    @Test
    public void setInspectionDateValid() throws AlreadyExistsException, InvalidInputException, FullAutomatException {
        Automat auto = getAutomat();

        auto.addHersteller(herst1);
        auto.addHersteller(herst3);
        auto.addKuchen(kuch1);
        auto.addKuchen(kuch2);
        auto.addKuchen(kuch3);

        Date date = new Date(2069, 6,9);

        auto.setInspectionDate(date);

        //check if the date in all the objects has haven changed
        Assertions.assertEquals(date, auto.getKuchen(0).getInspektionsdatum());
        Assertions.assertEquals(date, auto.getKuchen(1).getInspektionsdatum());
        Assertions.assertEquals(date, auto.getKuchen(2).getInspektionsdatum());
    }
}