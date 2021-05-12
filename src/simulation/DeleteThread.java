package simulation;

import automat.AutomatImpl;
import exceptions.EmptyListException;
import exceptions.InvalidInputException;

import java.util.Random;

public class DeleteThread extends Thread {
    private AutomatImpl automat;

    public void run(){
        while(true){
            try {
                this.automat.removeKuchen((int) (Math.random() * this.automat.checkKuchen().size()));
                System.out.println("Simulation hat kuchen entfernt");
            } catch (EmptyListException e) {
                System.out.println("simulation simulation: list empty");
            } catch (InvalidInputException e) {
                System.out.println("simulation: invalid input");
            }
        }
    }

    public void setAutomat(AutomatImpl automat) {
        this.automat = automat;
    }
}
