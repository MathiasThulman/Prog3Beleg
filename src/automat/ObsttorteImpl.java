package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObsttorteImpl extends KuchenVerkaufsObjektImpl implements Obsttorte {
    private final Kremsorte kremsorte;
    private final Obstsorte obstsorte;

    public ObsttorteImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, Kremsorte kremsorte, Obstsorte obstsorte) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte.getDescription();
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte.getDescription();
    }

    @Override
    public String toString(){
        return this.kremsorte + ", " + this.obstsorte + ", " + super.toString();
    }

    @Override
    public String getName(){
        return "Obsttorte";
    }
}
