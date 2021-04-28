package automat;

import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import kuchen.KuchenVerkaufsObjekt;
import kuchen.KuchenVerkaufsObjektImpl;
import observer.Observable;
import observer.Observer;


import java.util.*;

public class AutomatImpl implements Automat, Observable {
    private final LinkedList<Hersteller> herstellerList = new LinkedList<>();
    private final KuchenVerkaufsObjektImpl[] kuchenList;
    private final LinkedList<Observer> oberverList = new LinkedList<>();
    private int kuchenCounter = 0;

    public AutomatImpl(int fachzahl) {
        this.kuchenList = new KuchenVerkaufsObjektImpl[fachzahl];
    }

    @Override
    public void addHersteller(Hersteller hersteller) throws AlreadyExistsException {
        //check if manufacturer already exists
        for (Hersteller herst : this.herstellerList) {
            if (herst.getName().equals(hersteller.getName())) {
                throw new AlreadyExistsException();
            }
        }
        this.herstellerList.add(hersteller);
    }

    @Override
    public void removeHersteller(String hersteller) throws NoSuchElementException {
        for (Hersteller herst : this.herstellerList) {
            if (herst.getName().equals(hersteller)) {
                this.herstellerList.remove(herst);
                //remove all kuchen of the hersteller from the automat as well
                for(int i = 0; i < this.kuchenList.length; i++){
                    if(this.kuchenList[i] != null && this.kuchenList[i].getHersteller().getName().equals(hersteller)){
                        this.kuchenList[i] = null;
                    }
                }
                return;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void addKuchen(KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, FullAutomatException {

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
                    break;
                    //TODO notify
                }
            }
        if(fullFlag){
            throw new FullAutomatException();
        }
    }

    @Override
    public KuchenVerkaufsObjekt getKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);

        if (kuchenList[fachnummer] == null) {
            throw new NoSuchElementException();
        }
        return this.kuchenList[fachnummer];
    }

    @Override
    public void removeKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);

        //check the list if the fachnummer exists, first
        if (this.kuchenList[fachnummer] == null) {
            throw new NoSuchElementException();
        }

        this.kuchenList[fachnummer] = null;
        this.kuchenCounter--;
    }

    @Override
    public void changeKuchen(int fachnummer, KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);
        kuchen.setFachNummer(fachnummer);

        for (int i = 0; i < this.kuchenList.length; i++) {
            if(this.kuchenList[i] == null){
                break; //break when no objekt is at the list place to avoid null pointer exception
            }
            if (kuchenList[i].getFachnummer() == fachnummer) {
                kuchenList[i] = kuchen;
                return;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public LinkedList<Hersteller> getHersteller() throws EmptyListException {
        if (this.herstellerList.isEmpty()) {
            throw new EmptyListException();
        }
        return this.herstellerList;
    }

    @Override
    public HashMap<String, Integer> checkHersteller() throws NoSuchElementException, EmptyListException {
        if (this.herstellerList.isEmpty()) {
            throw new EmptyListException();
        }
        HashMap<String, Integer> manufacturerHashmap = new HashMap<>();

        for (Hersteller manu : this.herstellerList) {
            if (!manufacturerHashmap.containsKey(manu)) ;
            manufacturerHashmap.put(manu.getName(), 0);
        }

        for (KuchenVerkaufsObjekt kuch : this.kuchenList) {
            if(kuch == null){
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

    @Override
    public KuchenVerkaufsObjektImpl[] checkKuchen() throws EmptyListException {
        kuchListEmpty();

        return this.kuchenList;
    }

    @Override
    public LinkedList<KuchenVerkaufsObjektImpl> checkKuchen(KuchenVerkaufsObjekt kuchen) throws NoSuchElementException, EmptyListException {
        kuchListEmpty();

        LinkedList<KuchenVerkaufsObjektImpl> res = new LinkedList<>();

        for(int i = 0; i < this.kuchenList.length; i++) {
            if (this.kuchenList[i] != null && this.kuchenList[i].getClass().equals(kuchen.getClass())) {
                res.add(this.kuchenList[i]);
            }
        }

        if (res.isEmpty()) {
            throw new NoSuchElementException();
        }

        return res;
    }

    @Override
    public Set<Allergen> checkAllergen() throws EmptyListException {
        kuchListEmpty();

       HashSet<Allergen> res = new HashSet<Allergen>();

        for (KuchenVerkaufsObjekt kuch : kuchenList) {
            if(kuch != null){
                for (Allergen allergen : kuch.getAllergene()) {
                    //if (!res.contains(allergen)) { does hashet work?
                        res.add(allergen);
                    //}
                }
            }
        }
        return res;
    }

    @Override
    public void setInspectionDate(Date date, int fachnummer) throws InvalidInputException {
        checkNumber(fachnummer);

        this.kuchenList[fachnummer].setInspektionsDatum(date);
    }

    //function to check if the fachnummer is negative
    private void checkNumber(int num) throws InvalidInputException {
        if (num < 0 || num > this.kuchenList.length) {
            throw new InvalidInputException();
        }
    }

    private void kuchListEmpty() throws EmptyListException {
        boolean flag = false;
        for (int i = 0; i < this.kuchenList.length; i++) {
            if (this.kuchenList[i] != null) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new EmptyListException();
        }
    }

    public int getSize(){
        return this.kuchenList.length;
    }

    @Override
    public void addObserver(Observer observer) {
        this.oberverList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.oberverList.remove(observer);
    }

    @Override
    public void notifyObservers() throws EmptyListException, FullAutomatException, InvalidInputException, AlreadyExistsException {
        for(Observer observer : this.oberverList){
            observer.update();
        }
    }

    public int getKuchenCounter() {
        return kuchenCounter;
    }
}
