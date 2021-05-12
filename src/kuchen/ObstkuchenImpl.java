package kuchen;

import automat.Allergen;
import automat.Hersteller;
import automat.Obstkuchen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObstkuchenImpl extends KuchenVerkaufsObjektImpl implements Obstkuchen {
    private String obstsorte;

    public ObstkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String obstsorte) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.obstsorte = obstsorte;
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }

    @Override
    public String toString(){
        return this.obstsorte + ", " + super.toString();
    }
}
