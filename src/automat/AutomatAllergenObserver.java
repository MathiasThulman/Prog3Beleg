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
        this.automat.addObserver(this);
    }

    @Override
    public void update() throws EmptyListException {
       if(!this.allergenList.equals(automat.checkAllergen())){
           this.allergenList = automat.checkAllergen();
           System.out.println("Die Allergene im Automat haben sich verändert");
       }
    }
}
