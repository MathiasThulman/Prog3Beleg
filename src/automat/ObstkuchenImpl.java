package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObstkuchenImpl extends BasisKuchenImpl implements Obstkuchen {

    public ObstkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
    }

    @Override
    public String getName(){
        return "Obstkuchen";
    }
}
