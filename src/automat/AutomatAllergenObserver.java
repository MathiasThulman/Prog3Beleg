package automat;

import exceptions.EmptyListException;
import observer.Observer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class AutomatAllergenObserver implements Observer {
    AutomatImpl automat;
    Set<Allergen> allergenList = new HashSet<>();

    public AutomatAllergenObserver(AutomatImpl automat) {
        this.automat = automat;
    }

    @Override
    public void update() throws EmptyListException {
       // this.allergenList = this.automat.checkAllergen();
    }
}
