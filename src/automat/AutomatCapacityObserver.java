package automat;

import events.InputEvent;
import events.InputEventHandler;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;
import observer.Observer;

public class AutomatCapacityObserver implements Observer {
    private final String evenString = "Automat is over 90% capacity";
    private AutomatImpl automat;
    int capacity;
  //inputhandler not necessary


    public AutomatCapacityObserver(AutomatImpl automat) {
        this.automat = automat;
        this.automat.addObserver(this);
        this.capacity = this.automat.getSize();
    }

    @Override
    public void update() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
        if(this.automat.getKuchenCounter() > capacity * 0.9){
            System.out.println("Dieser Automat hat die Kapazität von 90% überschritten");
        }
    }
}
