package simulation;

import automat.Automat;
import exceptions.EmptyListException;
import exceptions.InvalidInputException;

import java.util.Calendar;
import java.util.Random;

public class InspektionThread extends Thread {
    private AutomatSimulationWrapper wrapper;

    public void run() {
        while (true) {
            this.wrapper.causeInspection();
            try {
                sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWrapper(AutomatSimulationWrapper wrapper) {
        this.wrapper = wrapper;
    }
}
