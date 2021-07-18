package simulation;

import java.util.Random;

public class DeleteMultipleThread extends Thread{
    private AutomatSimulationWrapper wrapper;

    public void run(){
        while(true){
            this.wrapper.removeMultipleOldestCakeSynchronized(new Random(System.currentTimeMillis()));
        }
    }


    public void setSimulationWrapper(AutomatSimulationWrapper wrapper) {
        this.wrapper = wrapper;
    }
}
