package automat;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KuchenVerkaufsObjekt;
import observer.Observable;
import observer.Observer;


import java.io.Serializable;
import java.util.*;

public class Automat implements Observable, Serializable {
    private final LinkedList<Hersteller> herstellerList = new LinkedList<>();
    private final KuchenVerkaufsObjektImpl[] kuchenList;
    private final transient LinkedList<Observer> observerList = new LinkedList<>();
    private volatile int kuchenCounter = 0;

    public Automat(int fachzahl) {
        this.kuchenList = new KuchenVerkaufsObjektImpl[fachzahl];
    }

    synchronized public void addHersteller(Hersteller hersteller) throws AlreadyExistsException {
        //check if manufacturer already exists
        for (Hersteller herst : this.herstellerList) {
            if (herst.getName().equals(hersteller.getName())) {
                throw new AlreadyExistsException();
            }
        }
        this.herstellerList.add(hersteller);
        notifyObservers();
    }

    synchronized public void removeHersteller(String hersteller) throws NoSuchElementException {
        for (Hersteller herst : this.herstellerList) {
            if (herst.getName().equals(hersteller)) {
                this.herstellerList.remove(herst);
                //remove all kuchen of the hersteller from the automat as well
                for (int i = 0; i < this.kuchenList.length; i++) {
                    if (this.kuchenList[i] != null && this.kuchenList[i].getHersteller().getName().equals(hersteller)) {
                        this.kuchenList[i] = null;
                    }
                }
                notifyObservers();
                return;
            }
        }
        throw new NoSuchElementException();
    }

    synchronized public void addKuchen(KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, FullAutomatException {

        //check if there is already a hersteller with boolean flag
        boolean herstFlag = false;
        for (Hersteller herst : this.herstellerList) {
            if (kuchen.getHersteller().getName().equals(herst.getName())) {
                herstFlag = true;
                break;
            }
        }

        //throw exception if no hersteller has been found
        if (!herstFlag) {
            throw new NoSuchElementException();
        }

        //insert the kuchen at the first empty place
        boolean fullFlag = true;
        for (int i = 0; i < this.kuchenList.length; i++) {
            if (this.kuchenList[i] == null) {
                kuchen.setFachNummer(i);    //put this somewhere else?
                kuchen.setInspektionsDatum(Calendar.getInstance().getTime());
                this.kuchenList[i] = kuchen;
                fullFlag = false;
                this.kuchenCounter++;
                notifyObservers();
                break;
            }
        }
        if (fullFlag) {
            throw new FullAutomatException();
        }
    }

    synchronized public KuchenVerkaufsObjektImpl getKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);

        if (kuchenList[fachnummer] == null) {
            throw new NoSuchElementException();
        }
        return this.kuchenList[fachnummer];
    }

    synchronized public void removeKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);

        //check the list if the fachnummer exists, first
        if (this.kuchenList[fachnummer] == null) {
            throw new NoSuchElementException();
        }

        this.kuchenList[fachnummer] = null;
        this.kuchenCounter--;
        notifyObservers();
    }

    synchronized public void changeKuchen(int fachnummer, KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);
        kuchen.setFachNummer(fachnummer);

        //exception removed for easier swap and the ability to inserti kuchen
        this.kuchenList[fachnummer] = kuchen;
    }

    synchronized public void swapKuchen(int fachNummer1, int fachNummer2) throws InvalidInputException {
        checkNumber(fachNummer1);
        checkNumber(fachNummer2);

        if (this.kuchenList[fachNummer1] == null || this.kuchenList[fachNummer2] == null) {
            throw new NoSuchElementException();
        }

        KuchenVerkaufsObjektImpl tempKuchen = getKuchen(fachNummer1);

        changeKuchen(fachNummer1, getKuchen(fachNummer2));
        changeKuchen(fachNummer2, tempKuchen);
    }

    synchronized public LinkedList<Hersteller> getHersteller() throws EmptyListException {
        if (this.herstellerList.isEmpty()) {
            throw new EmptyListException();
        }
        return this.herstellerList;
    }

    synchronized public HashMap<String, Integer> checkHersteller() throws NoSuchElementException, EmptyListException {
        if (this.herstellerList.isEmpty()) {
            throw new EmptyListException();
        }
        HashMap<String, Integer> manufacturerHashmap = new HashMap<>();

        for (Hersteller manu : this.herstellerList) {
            if (!manufacturerHashmap.containsKey(manu)) {
                manufacturerHashmap.put(manu.getName(), 0);
            }
        }

        for (KuchenVerkaufsObjekt kuch : this.kuchenList) {
            if (kuch == null) {
                break; // dont go into objekt if null to avoid NullPointerException
            }
            if (manufacturerHashmap.containsKey(kuch.getHersteller().getName())) {
                manufacturerHashmap.put(kuch.getHersteller().getName(), manufacturerHashmap.get(kuch.getHersteller().getName()) + 1);
            } else {
                manufacturerHashmap.put(kuch.getHersteller().getName(), 1);
            }
        }
        return manufacturerHashmap;
    }

    synchronized public List<KuchenVerkaufsObjektImpl > checkKuchen() throws EmptyListException {
        if(kuchListEmpty()) throw new EmptyListException();

        LinkedList<KuchenVerkaufsObjektImpl> res = new LinkedList<>();

        for(int i = 0; i < this.kuchenList.length; i++) {
            if (this.kuchenList[i] != null) {
                res.add(this.kuchenList[i]);
            }
        }

        return res;
    }

    synchronized public LinkedList<KuchenVerkaufsObjektImpl> checkKuchen(Class kuchen) throws NoSuchElementException, EmptyListException {
        if(kuchListEmpty()) throw new EmptyListException();

        LinkedList<KuchenVerkaufsObjektImpl> res = new LinkedList<>();

        for (int i = 0; i < this.kuchenList.length; i++) {
            if (this.kuchenList[i] != null && this.kuchenList[i].getClass().equals(kuchen)) {
                res.add(this.kuchenList[i]);
            }
        }

        if (res.isEmpty()) {
            throw new NoSuchElementException();
        }

        return res;
    }

    synchronized public Set<Allergen> checkAllergen() throws EmptyListException {
        if(kuchListEmpty()) throw new EmptyListException();

        HashSet<Allergen> res = new HashSet<Allergen>();

        for (KuchenVerkaufsObjekt kuch : kuchenList) {
            if (kuch != null) {
                res.addAll(kuch.getAllergene());
            }
        }
        return res;
    }

    synchronized public Set<Allergen> checkAbsentAllergen() throws EmptyListException {
        if(kuchListEmpty()) throw new EmptyListException();

        HashSet<Allergen> res = new HashSet<>();
        res.add(Allergen.Erdnuss);
        res.add(Allergen.Haselnuss);
        res.add(Allergen.Gluten);
        res.add(Allergen.Sesamsamen);

        for (KuchenVerkaufsObjekt kuch : kuchenList) {
            if (kuch != null) {
                res.removeAll(kuch.getAllergene());
            }
        }

        return res;
    }

    synchronized public void setInspectionDate(Date date, int fachnummer) throws InvalidInputException {
        checkNumber(fachnummer);

        this.kuchenList[fachnummer].setInspektionsDatum(date);
        notifyObservers();
    }

    //function to check if the fachnummer is negative
    synchronized public void checkNumber(int num) throws InvalidInputException {
        if (num < 0 || num > this.kuchenList.length) {
            throw new InvalidInputException();
        }
    }

    synchronized public boolean kuchListEmpty() {
        boolean flag = false;
        for (KuchenVerkaufsObjektImpl kuchenVerkaufsObjekt : this.kuchenList) {
            if (kuchenVerkaufsObjekt != null) {
                flag = true;
                break;
            }
        }
        return !flag;
    }

    public int getSize() {
        return this.kuchenList.length;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if(observerList!=null)
        for (Observer observer : this.observerList) {
            try {
                observer.update();//why does this throw every exception under the sun ??
            } catch (EmptyListException | NullPointerException | AlreadyExistsException | InvalidInputException | FullAutomatException e) {
            }
        }
    }

    public int getKuchenCounter() {
        return kuchenCounter;
    }

    public int getFachzahl(){
        return this.kuchenList.length;
    }
}
