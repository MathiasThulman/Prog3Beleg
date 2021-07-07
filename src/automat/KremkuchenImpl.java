package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class KremkuchenImpl extends KuchenVerkaufsObjektImpl implements Kremkuchen {
    private Kremsorte kremsorte;

    public KremkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, Kremsorte kremSorte) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.kremsorte = kremSorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte.getDescription();
    }

    @Override
    public String toString(){
        return this.kremsorte.getDescription() + ", " + super.toString();
    }

}
