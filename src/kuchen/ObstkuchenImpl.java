package kuchen;

import automat.Manufacturer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObstkuchenImpl extends KuchenVerkaufsObjektImpl implements Obstkuchen {
    private String obstsorte;

    public ObstkuchenImpl(Manufacturer manufacturer, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String obstsorte) {
        super(manufacturer, allergene, naehrwert, haltbarkeit, preis);
        this.obstsorte = obstsorte;
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }

    public void setObstsorte(String obstsorte) {
        this.obstsorte = obstsorte;
    }
}
