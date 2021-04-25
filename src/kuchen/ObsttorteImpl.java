package kuchen;

import automat.Allergen;
import automat.Hersteller;
import automat.Obsttorte;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObsttorteImpl extends KuchenVerkaufsObjektImpl implements Obsttorte {
    private String kremsorte;
    private String obstsorte;

    public ObsttorteImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String kremsorte, String obstsorte) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }

    @Override
    public String getObstsorte() {
        return this.obstsorte;
    }

}
