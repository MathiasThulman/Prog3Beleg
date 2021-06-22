package automat;

import util.JoSSerializer;

public class AutomatWrapper {
    private Automat automat;
    private JoSSerializer serializer;

    public Automat getAutomat() {
        return automat;
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }


    public JoSSerializer getSerializer() {
        return serializer;
    }

    public void setSerializer(JoSSerializer serializer) {
        this.serializer = serializer;
    }
}
