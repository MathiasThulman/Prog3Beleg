package simulation;

import automat.AutomatImpl;
import exceptions.EmptyListException;
import exceptions.InvalidInputException;

import java.util.NoSuchElementException;
import java.util.Random;

public class DeleteRandomThread extends Thread {
    private AutomatImpl automat;

    public void run(){
        int domain = this.automat.getSize();
        while(true){
            Random rand = new Random();
            try {
                this.automat.removeKuchen((int) (Math.random()* domain));
                System.out.println("Simulation hat kuchen entfernt");
           // } catch (EmptyListException e) {
            //    System.out.println("simulation simulation: list empty");
            } catch (InvalidInputException e) {
                System.out.println("simulation: invalid input");
            } catch (NoSuchElementException e){
                //System.out.println("simulation picked invalid random"); this only muddles the sout
            }
        }
    }

    public void setAutomat(AutomatImpl automat) {
        this.automat = automat;
    }
}
