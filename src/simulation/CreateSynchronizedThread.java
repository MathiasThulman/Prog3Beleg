package simulation;


public class CreateSynchronizedThread extends Thread{
    private AutomatSimulationWrapper wrapper;

    public void run(){
        while(true){
            this.wrapper.createRandomCakeSynchronized();
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
