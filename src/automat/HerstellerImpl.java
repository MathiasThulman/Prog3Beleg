package automat;

import java.io.Serializable;

public class HerstellerImpl implements Hersteller, Serializable {
    private String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
