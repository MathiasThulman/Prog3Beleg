package simulation;


public class DeleteOldestThread extends Thread {
    private AutomatSimulationWrapper simulationWrapper;

    public void run() {
        while (true) {
            this.simulationWrapper.removeOldestCakeSynchronized();
        }
    }

    public void setSimulationWrapper(AutomatSimulationWrapper simulationWrapper) {
        this.simulationWrapper = simulationWrapper;
    }
}
