package simulation;

public class DeleteMultipleThread extends Thread{
    private AutomatSimulationWrapper wrapper;

    public void run(){
        while(true){
            this.wrapper.removeMultipleOldestCake();
        }
    }


    public void setSimulationWrapper(AutomatSimulationWrapper wrapper) {
        this.wrapper = wrapper;
    }
}
