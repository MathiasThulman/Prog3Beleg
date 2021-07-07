package automat;

import java.io.Serializable;

public class Kremsorte implements Serializable {
    public String description;

    public Kremsorte(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
