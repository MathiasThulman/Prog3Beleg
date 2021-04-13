package junitTests;

import automat.Automat;
import automat.AutomatImpl;
import automat.Manufacturer;
import automat.ManufacturerImpl;
import exceptions.AlreadyExistsException;
import exceptions.InvalidInputException;
import kuchen.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.LinkedList;
import java.util.NoSuchElementException;

class AutomatTest {
    private final int fn1 = 2468;
    private final int fn2 = 3579;
    private final int fn3 = 1122;
    private final int fn4 = 8000;
    private final int negFn = -444;
    private final String MASCARPONE = "Mascarpone";
    private final String SENF = "Senf";
    private final String KIRSCHE = "Kirsche";
    private final String ERDBEERE = "Erdbeere";
    private final String BENJAMIN = "benjamin";
    private final String BLÜMCHEN = "blümchen";
    private final String MOSES = "moses";
    Manufacturer manu1 = new ManufacturerImpl(BENJAMIN);
    Manufacturer manu2 = new ManufacturerImpl(BLÜMCHEN);
    Manufacturer manu3 = new ManufacturerImpl(MOSES);
    Duration dur1 = Duration.ofDays(4);// = new Duration(22,45);//TODO how does duration work?
    LinkedList<Allergen> allCol1 = new LinkedList<>();

    {allCol1. add(Allergen.Erdnuss); allCol1.add(Allergen.Haselnuss);}
    KuchenVerkaufsObjektImpl kuch1 = new KremkuchenImpl(manu1, allCol1, 300, dur1, new BigDecimal(500), MASCARPONE);
    KuchenVerkaufsObjektImpl kuch2 = new ObstkuchenImpl(manu3, allCol1, 300, dur1, new BigDecimal(250), KIRSCHE);
    KuchenVerkaufsObjektImpl kuch3 = new ObsttorteImpl(manu1, allCol1, 300, dur1, new BigDecimal(300), MASCARPONE, ERDBEERE);
    KuchenVerkaufsObjektImpl kuch4 = new KremkuchenImpl(manu3, allCol1,250 , dur1, new BigDecimal(400), SENF);

    public Automat getGL(){
        return new AutomatImpl();
    }

    //addManufacturer tests
    @Test
    public void addManufacturerValid() throws AlreadyExistsException {
        Automat gl = getGL();

        gl.addManufacturer(manu1);

        Assertions.assertEquals(BENJAMIN, gl.getManurfacturer().get(0));
    }

    @Test //(expected = AlreadyExistsException.class)// why doesnt this work?
    public void addManufacturererNameAlreadyExists() throws AlreadyExistsException {
        Automat gl = getGL();

        gl.addManufacturer(manu1);
        Manufacturer manu11 = new ManufacturerImpl("benjamin");

        Assertions.assertThrows(AlreadyExistsException.class, () -> gl.addManufacturer(manu11));
    }

    //addKuchen tests
    @Test
    public void addKuchenValid() throws AlreadyExistsException, InvalidInputException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch1);

        Assertions.assertEquals(fn1, gl.getKuchen(fn1).getFachnummer());
    }

    @Test
    public void addKuchenFachnummerAlreadyExists() throws AlreadyExistsException, InvalidInputException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch1);
        Assertions.assertThrows(AlreadyExistsException.class, () -> gl.addKuchen(fn1, kuch2));
    }

    @Test
    public void addKuchenNegativeNumbers() throws AlreadyExistsException, InvalidInputException {
        Automat gl = getGL();

        Assertions.assertThrows(InvalidInputException.class, () -> gl.addKuchen(negFn, kuch1));
    }

    //getKuchen tests

    //removeKuchen tests
    @Test
    public void removeKuchenNoSuchElement() throws InvalidInputException, NoSuchElementException {
       Automat gl = getGL();

       gl.removeKuchen(2);
       Assertions.assertThrows(NoSuchElementException.class, () -> gl.removeKuchen(2));
    }

    @Test
    public void removeKuchenValid() throws AlreadyExistsException, InvalidInputException, NoSuchElementException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch1);
        gl.addKuchen(fn2, kuch2);

        gl.removeKuchen(fn1);
        //after removing check if the kuchen is still there
        Assertions.assertThrows(NoSuchElementException.class, () -> gl.getKuchen(fn1));
    }

    //changeKuchen tests
    @Test
    public void changeKuchenValid() throws AlreadyExistsException, InvalidInputException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch1);
        gl.addKuchen(fn2, kuch2);

        gl.changeKuchen(fn2, kuch3);//TODO assert?
        Assertions.assertEquals(ObsttorteImpl.class, gl.getKuchen(fn2).getClass());
    }

    @Test
    public void changeKuchenNoSuchElement() throws AlreadyExistsException, InvalidInputException, NoSuchElementException {
        Automat gl = getGL();

        gl.addKuchen(fn3, kuch1);
        gl.addKuchen(fn2, kuch3);


        Assertions.assertThrows(NoSuchElementException.class, () -> gl.changeKuchen(fn1, kuch2));
    }

    //checkManufacturer tests
    @Test
    public void checkManufacturerValid() throws AlreadyExistsException, InvalidInputException, NoSuchElementException {
        Automat gl = getGL();

        gl.addManufacturer(manu1);
        gl.addManufacturer(manu2);
        gl.addManufacturer(manu3);

        gl.checkManufacturers();
        //TODO assert?
    }

    @Test
    public void checkManufacturerNullPointer() throws AlreadyExistsException, InvalidInputException, NoSuchElementException {
        Automat gl = getGL();

        Assertions.assertThrows(NullPointerException.class, () -> gl.checkManufacturers());
        //TODO assert throws NoSuchElementException
    }

    //checkKuchen tests
    @Test
    public void checkKuchenAllValid() throws AlreadyExistsException, InvalidInputException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch3);
        gl.addKuchen(fn2, kuch1);
        gl.addKuchen(fn3, kuch2);

        gl.checkKuchen();
        //TODO assert?
    }

    @Test
    public void checkKuchenSpecificValid() throws AlreadyExistsException, InvalidInputException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch3);
        gl.addKuchen(fn2, kuch1);
        gl.addKuchen(fn3, kuch2);
        gl.addKuchen(fn4, kuch4);

        Assertions.assertEquals(fn1, gl.checkKuchen(kuch3).get(0).getFachnummer());
    }

    @Test
    public void checkKuchenEmptyAutomat() throws NullPointerException{
        Automat gl = getGL();

        Assertions.assertThrows(NullPointerException.class, () -> gl.checkKuchen());
    }

    @Test
    public void checkKuchenNoSuchElement() throws AlreadyExistsException, InvalidInputException, NoSuchElementException {
        Automat gl = getGL();

        gl.addKuchen(fn1, kuch2);
        gl.addKuchen(fn2, kuch1);
        gl.addKuchen(fn4, kuch4);

        Assertions.assertThrows(NoSuchElementException.class, () -> gl.checkKuchen(kuch3));
        //TODO assert throws NoSuchElementException
    }
}