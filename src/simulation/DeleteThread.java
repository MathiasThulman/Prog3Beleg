package simulation;

import exceptions.EmptyListException;
import exceptions.InvalidInputException;

import java.util.Random;

public class DeleteThread extends Thread {
    private AutomatSimulationWrapper simulationWrapper;

    public void run() {
        while (true) {
            this.simulationWrapper.removeRandomCake(new Random(System.currentTimeMillis()));
        }
    }

    public void setSimulationWrapper(AutomatSimulationWrapper simulationWrapper) {
        this.simulationWrapper = simulationWrapper;
    }
}
