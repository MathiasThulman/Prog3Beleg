package observer;

import automat.Automat;
import observer.Observer;

public class AutomatCapacityObserver implements Observer {
    private final String evenString = "Automat is over 90% capacity";
    private final Automat automat;
    int capacity;


    public AutomatCapacityObserver(Automat automat) {
        this.automat = automat;
        this.automat.addObserver(this);
        this.capacity = this.automat.getSize();
    }

    @Override
    public void update(){
        if(this.automat.getKuchenCounter() > capacity * 0.9){
            System.out.println("Dieser Automat hat die Kapazität von 90% überschritten");
        }
    }
}
