import observer.AutomatChangeObserver;
import automat.Automat;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import simulation.AutomatSimulationWrapper;
import simulation.CreateThread;
import simulation.DeleteThread;

public class Simulation1 {


    public static void main(String[] args) {
        String BENJAMIN = "benjamin";
        String BLUEMCHEN = "blümchen";
        String MOSES = "moses";
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        Automat automat = new Automat(6000);
        AutomatChangeObserver observer = new AutomatChangeObserver(automat);
        AutomatSimulationWrapper wrapper = new AutomatSimulationWrapper();
        wrapper.setAutomat(automat);

        CreateThread createThread = new CreateThread();
        DeleteThread deleteThread = new DeleteThread();
        createThread.setSimulationWrapper(wrapper);
        deleteThread.setSimulationWrapper(wrapper);

        try {
            automat.addHersteller(herst1);
            automat.addHersteller(herst2);
            automat.addHersteller(herst3);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
        createThread.start();
        deleteThread.start();
    }

}
