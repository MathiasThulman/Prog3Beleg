package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObstkuchenImpl extends KuchenVerkaufsObjektImpl implements Obstkuchen {
    private final Obstsorte obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, Obstsorte obstsorte) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.obstsorte = obstsorte;
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte.getDescription();
    }

    @Override
    public String toString(){
        return this.obstsorte.getDescription() + ", " + super.toString();
    }

    @Override
    public String getName(){
        return "Obstkuchen";
    }
}
