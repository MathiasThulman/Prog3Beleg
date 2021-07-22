package automat;

import java.time.Duration;
import java.util.Collection;

interface Kuchen {
    Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
}
