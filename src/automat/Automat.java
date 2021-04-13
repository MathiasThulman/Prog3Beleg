package automat;

import exceptions.AlreadyExistsException;
import exceptions.InvalidInputException;
import kuchen.Allergen;
import kuchen.KuchenVerkaufsObjekt;
import kuchen.KuchenVerkaufsObjektImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public interface Automat {
    /**
     * add a herrsteller
     * @param manufacturer
     * @throws AlreadyExistsException when a herrsteller with the same name exists
     */
    void addManufacturer(Manufacturer manufacturer) throws AlreadyExistsException;
    /**
     * add a Kuchen-Object with the given fachnummer
     * @param fachnummer
     * @throws AlreadyExistsException when the fachnummer is already taken
     */
    void addKuchen(int fachnummer, KuchenVerkaufsObjektImpl kuchen)throws AlreadyExistsException, InvalidInputException;

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
     * @return the list of all available manufacturers
     * @throws NullPointerException when no manufacturers are in the list
     */
    LinkedList<Manufacturer> getManurfacturer() throws NullPointerException;

    /**
     * @return a list of all manufacturers with the number of cakes they have
     * @throws NoSuchElementException when manufacturer doesnt exist
     */
    HashMap checkManufacturers() throws NullPointerException;

    /**
     * checks every cake in the automat
     * @return all available cakes as a list
     */
    LinkedList<KuchenVerkaufsObjekt> checkKuchen() throws NullPointerException;

    /**
     * checks all available cakes
     * @param kuchen cake by which you want to filter
     * @return all cakes of chosen type as a list
     * @throws NoSuchElementException when chosen cake is not in the list
     */
    LinkedList<KuchenVerkaufsObjekt> checkKuchen(KuchenVerkaufsObjekt kuchen) throws NoSuchElementException;

    /**
     * checks the whole automat
     * @return the list if every allergen currently in the automat
     * @throws NullPointerException when the list is empty
     */
    Collection<Allergen> checkAllergen() throws NullPointerException ;
}
