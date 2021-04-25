package automat;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KuchenVerkaufsObjekt;
import kuchen.KuchenVerkaufsObjektImpl;

import java.util.*;

public interface Automat {

    /**
     * add a new kuchen to the automat
     * @throws AlreadyExistsException when the fachnummer is already taken
     */
    void addKuchen(KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, FullAutomatException;

    /**
     * @param fachnummer place in the automat
     * @return the kuchen-object
     */
    KuchenVerkaufsObjekt getKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException;

    /**
     * remove cake with the given fachnummer
     * @param fachnummer
     * @throws NoSuchElementException when no Cake with the given fachnummer exists
     */
    void removeKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException;

    /**
     * change/replace the kuchen-object at given fachnummer
     * @param fachnummer
     * @param kuchen
     * @throws NoSuchElementException when the fachnummer does not exist
     */
    void changeKuchen(int fachnummer, KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, InvalidInputException;

    /**
     * add a herrsteller
     * @param hersteller
     * @throws AlreadyExistsException when a herrsteller with the same name exists
     */
    void addHersteller(Hersteller hersteller) throws AlreadyExistsException;

    /**
     * remove a hersteller from the automat
     * @param hersteller name
     * @throws NoSuchElementException when the name could not be found
     */
    void removeHersteller(String hersteller) throws NoSuchElementException;

    /**
     * @return the list of all available manufacturers
     * @throws NullPointerException when no manufacturers are in the list
     */
    LinkedList<Hersteller> getHersteller() throws EmptyListException;

    /**
     * @return a list of all manufacturers with the number of cakes they have
     * @throws NoSuchElementException when manufacturer doesnt exist
     */
    HashMap<String, Integer> checkHersteller() throws EmptyListException;

    /**
     * checks every cake in the automat
     * @return all available cakes as a list
     */
    KuchenVerkaufsObjektImpl[] checkKuchen() throws EmptyListException;

    /**
     * checks all available cakes
     * @param kuchen cake by which you want to filter
     * @return all cakes of chosen type as a list
     * @throws NoSuchElementException when chosen cake is not in the list
     */
    LinkedList<KuchenVerkaufsObjektImpl> checkKuchen(KuchenVerkaufsObjekt kuchen) throws NoSuchElementException, EmptyListException;

    /**
     * checks the whole automat
     * @return the list if every allergen currently in the automat, returns an emtpylist when no allergen have been found
     * @throws EmptyListException there are no kuchen in the automate
     */
    Collection<Allergen> checkAllergen() throws EmptyListException;

    /**
     * changes the inspection date of all cakes in the automat
     * @param date
     */
    void setInspectionDate(Date date);
}
