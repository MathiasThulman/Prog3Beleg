package simulation;

import automat.AutomatChangeObserver;
import automat.AutomatImpl;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;

public class Simulation1 {


    public static void main(String[] args) {
        String BENJAMIN = "benjamin";
        String BLUEMCHEN = "blümchen";
        String MOSES = "moses";
        Hersteller herst1 = new HerstellerImpl(BENJAMIN);
        Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
        Hersteller herst3 = new HerstellerImpl(MOSES);
        AutomatImpl automat = new AutomatImpl(60000);
        AutomatChangeObserver observer = new AutomatChangeObserver(automat);

        CreateThread createThread = new CreateThread();
        DeleteThread deleteThread = new DeleteThread();
        createThread.setAutomat(automat);
        deleteThread.setAutomat(automat);

        try {
            automat.addHersteller(herst1);
            automat.addHersteller(herst2);
            automat.addHersteller(herst3);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        };

        createThread.start();
        deleteThread.start();
    }

}
