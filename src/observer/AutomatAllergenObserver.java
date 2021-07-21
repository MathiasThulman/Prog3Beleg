package observer;

import automat.Allergen;
import automat.Automat;
import exceptions.EmptyListException;
import observer.Observer;

import java.util.HashSet;
import java.util.Set;

public class AutomatAllergenObserver implements Observer {
    Automat automat;
    Set<Allergen> allergenList = new HashSet<>();

    public AutomatAllergenObserver(Automat automat) {
        this.automat = automat;
        this.automat.addObserver(this);
    }

    @Override
    public void update() {
        try {
            if(!this.allergenList.equals(automat.checkAllergen())){
                this.allergenList = automat.checkAllergen();
                System.out.println("Die Allergene im Automat haben sich verändert");
            }
        } catch (EmptyListException e) {
            //ignore this as it will always be thrown on the first thing added
        }
    }
}
