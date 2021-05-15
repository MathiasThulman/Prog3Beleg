package simulation;

import exceptions.FullAutomatException;


public class CreateThread extends Thread {
    private AutomatSimulationWrapper simulationWrapper;

    public void run() {
        while (true) {
//            try {
            this.simulationWrapper.createRandomCake();
//            sleep(20);
//            }  catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public void setSimulationWrapper(AutomatSimulationWrapper simulationWrapper) {
        this.simulationWrapper = simulationWrapper;
    }
}
