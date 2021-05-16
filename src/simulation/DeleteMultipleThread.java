package simulation;

public class DeleteMultipleThread extends Thread{
    private AutomatSimulationWrapper wrapper;

    public void run(){
        while(true){
            this.wrapper.removeMultipleOldestCakeSynchronized();
        }
    }


    public void setSimulationWrapper(AutomatSimulationWrapper wrapper) {
        this.wrapper = wrapper;
    }
}
