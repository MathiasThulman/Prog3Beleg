package simulation;

public class DeleteSynchronizedThread extends Thread {
    private AutomatSimulationWrapper wrapper;

    public void run(){
        while(true){
            this.wrapper.removeOldestCakeSynchronized();
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
