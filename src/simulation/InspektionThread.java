package simulation;

import automat.AutomatImpl;
import exceptions.EmptyListException;
import exceptions.InvalidInputException;

public class InspektionThread extends Thread {
    private AutomatImpl automat;

    public void run(){
        while(true){
            try {
                System.out.println(this.automat.getKuchen((int) (Math.random() * this.automat.checkKuchen().size())).toString());
                System.out.println("Simulation inspektion");
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
