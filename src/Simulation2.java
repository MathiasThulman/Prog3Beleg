import automat.Automat;
import observer.AutomatChangeObserver;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import simulation.*;

public class Simulation2 {

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

        CreateSynchronizedThread createThread = new CreateSynchronizedThread();
        DeleteSynchronizedThread deleteThread = new DeleteSynchronizedThread();
        InspektionThread inspektionThread = new InspektionThread();
        createThread.setSimulationWrapper(wrapper);
        deleteThread.setSimulationWrapper(wrapper);
        inspektionThread.setWrapper(wrapper);

        try {
            automat.addHersteller(herst1);
            automat.addHersteller(herst2);
            automat.addHersteller(herst3);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }

        createThread.start();
        deleteThread.start();
        inspektionThread.start();
    }
}
