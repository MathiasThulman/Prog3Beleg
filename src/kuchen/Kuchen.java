package kuchen;

import automat.Manufacturer;

import java.time.Duration;
import java.util.Collection;

public interface Kuchen {
    Manufacturer getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
}
