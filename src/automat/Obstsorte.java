package automat;

import java.io.Serializable;

public class Obstsorte implements Serializable {
    public String description;

    public Obstsorte(String description)
    {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
