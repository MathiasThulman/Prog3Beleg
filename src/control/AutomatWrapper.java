package control;

import automat.Automat;
import persistence.JoSSerializer;

public class AutomatWrapper {
    private Automat automat;


    public Automat getAutomat() {
        return automat;
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

}
