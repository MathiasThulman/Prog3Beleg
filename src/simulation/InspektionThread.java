package simulation;

import automat.Automat;
import exceptions.EmptyListException;
import exceptions.InvalidInputException;

import java.util.Calendar;

public class InspektionThread extends Thread {
    private Automat automat;

    public void run(){
        while(true){
            try {
                this.automat.setInspectionDate(Calendar.getInstance().getTime() ,(int) (Math.random() * this.automat.checkKuchen().size()));
                System.out.println("Simulation inspektion");
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
