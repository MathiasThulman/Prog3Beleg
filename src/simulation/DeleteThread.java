package simulation;

import automat.Automat;
import exceptions.EmptyListException;
import exceptions.InvalidInputException;

public class DeleteThread extends Thread {
    private Automat automat;

    public void run(){
        while(true){
            try {
                this.automat.removeKuchen((int) (Math.random() * this.automat.checkKuchen().size()));
                System.out.println("Simulation removed thread");
            } catch (EmptyListException e) {
                System.out.println("simulation simulation: list empty");
            } catch (InvalidInputException e) {
                System.out.println("simulation: invalid input");
            }
        }
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }
}
