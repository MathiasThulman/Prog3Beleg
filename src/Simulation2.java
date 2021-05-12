import automat.Automat;
import automat.AutomatChangeObserver;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import simulation.CreateThread;
import simulation.DeleteRandomThread;
import simulation.DeleteThread;
import simulation.InspektionThread;

public class Simulation2 {

    public static void main(String[] args) {
        String BENJAMIN = "benjamin";
        String BLUEMCHEN = "blümchen";
        String MOSES = "moses";
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        Automat automat = new Automat(60000);
        AutomatChangeObserver observer = new AutomatChangeObserver(automat);

        CreateThread createThread = new CreateThread();
        DeleteRandomThread deleteThread = new DeleteRandomThread();//random delete uses because non random is too slow
        InspektionThread inspektionThread = new InspektionThread();
        createThread.setAutomat(automat);
        deleteThread.setAutomat(automat);
        inspektionThread.setAutomat(automat);

        try {
            automat.addHersteller(herst1);
            automat.addHersteller(herst2);
            automat.addHersteller(herst3);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        };

        createThread.start();
        deleteThread.start();
        inspektionThread.start();
    }
}
