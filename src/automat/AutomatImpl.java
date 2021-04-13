package automat;

import exceptions.AlreadyExistsException;
import exceptions.InvalidInputException;
import kuchen.Allergen;
import kuchen.KuchenVerkaufsObjekt;
import kuchen.KuchenVerkaufsObjektImpl;

import java.util.*;

public class AutomatImpl implements Automat {
    private LinkedList<Manufacturer> manufacturerList = new LinkedList<>();
    private LinkedList<KuchenVerkaufsObjekt> kuchenList = new LinkedList<>();

    @Override
    public void addManufacturer(Manufacturer manufacturer) throws AlreadyExistsException {
        //check if manufacturer already exists
        for (Manufacturer herst : this.manufacturerList) {
            if (herst.getName().equals(manufacturer.getName())) {
                throw new AlreadyExistsException();
            }
        }
        this.manufacturerList.add(manufacturer);
    }

    @Override
    public void addKuchen(int fachnummer, KuchenVerkaufsObjektImpl kuchen) throws AlreadyExistsException, InvalidInputException {
        checkNumber(fachnummer);

        //check if the fachnummer already exists
        for (KuchenVerkaufsObjekt kuch : this.kuchenList) {
            if (kuch.getFachnummer() == fachnummer) {
                throw new AlreadyExistsException();
            }
        }
        kuchen.setFachNummer(fachnummer);     //put this somewhere else?
        kuchen.setInspektionsDatum(Calendar.getInstance().getTime());
        this.kuchenList.add(kuchen);
    }

    @Override
    public KuchenVerkaufsObjekt getKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);

        for (KuchenVerkaufsObjekt kuch : this.kuchenList) {
            if (kuch.getFachnummer() == fachnummer) {
                return kuch;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void removeKuchen(int fachnummer) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);

        //check the list if the fachnummer exists, remove it if it does
        for (KuchenVerkaufsObjekt kuch : this.kuchenList) {
            if (kuch.getFachnummer() == fachnummer) {
                kuchenList.remove(kuch);
                return;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void changeKuchen(int fachnummer, KuchenVerkaufsObjektImpl kuchen) throws NoSuchElementException, InvalidInputException {
        checkNumber(fachnummer);
        kuchen.setFachNummer(fachnummer);

        for (int i = 0; i < kuchenList.size(); i++) {
            if (kuchenList.get(i).getFachnummer() == fachnummer) {
                kuchenList.set(i, kuchen);
                return;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public LinkedList<Manufacturer> getManurfacturer() throws NullPointerException {
        return this.manufacturerList;
    }

    @Override
    public HashMap checkManufacturers() throws NoSuchElementException {
        if(this.manufacturerList.isEmpty()){
            throw new NullPointerException();
        }
        HashMap<Manufacturer, Integer> manufacturerHashmap = new HashMap<>();

        for(Manufacturer manu : this.manufacturerList){
            if(!manufacturerHashmap.containsKey(manu));
                manufacturerHashmap.put(manu, 0);
        }

        for(KuchenVerkaufsObjekt kuch : this.kuchenList){
            if(manufacturerHashmap.containsKey(kuch.getHersteller())){
                manufacturerHashmap.put(kuch.getHersteller(), manufacturerHashmap.get(kuch.getHersteller()) + 1);
            } else {
                manufacturerHashmap.put(kuch.getHersteller(), 0);
            }
        }


        return manufacturerHashmap;
    }

    @Override
    public LinkedList<KuchenVerkaufsObjekt> checkKuchen() {
        if(this.kuchenList.isEmpty()){
            throw new NullPointerException();
        }

        return this.kuchenList;
    }

    @Override
    public LinkedList<KuchenVerkaufsObjekt> checkKuchen(KuchenVerkaufsObjekt kuchen) throws NoSuchElementException {
        if(this.kuchenList.isEmpty()){
            throw new NullPointerException();
        }

        LinkedList<KuchenVerkaufsObjekt> res = new LinkedList<>();

        for (KuchenVerkaufsObjekt kuch : kuchenList) {
            if (kuch.getClass().equals(kuchen.getClass())) {
                res.add(kuch);
            }
        }

        if(res.isEmpty()){
            throw new NoSuchElementException();
        }

        return res;
    }

    @Override
    public Collection<Allergen> checkAllergen() throws NullPointerException {
        LinkedList<Allergen> res = new LinkedList<>();

        for (KuchenVerkaufsObjekt kuch : kuchenList) {
            for (Allergen allergen : kuch.getAllergene()) {
                if (!res.contains(allergen)) {
                    res.add(allergen);
                }
            }
        }

        return res;
    }

    //function to check if the fachnummer is negative
    private static void checkNumber(int num) throws InvalidInputException {
        if (num < 0) {
            throw new InvalidInputException();
        }
    }
}
