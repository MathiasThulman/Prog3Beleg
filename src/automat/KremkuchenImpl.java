package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class KremkuchenImpl extends KuchenVerkaufsObjektImpl implements Kremkuchen {

    public KremkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
    }

    @Override
    public String getName(){
        return "Kremkuchen";
    }
}
