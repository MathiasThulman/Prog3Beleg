package kuchen;

import automat.Manufacturer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class ObsttorteImpl extends KuchenVerkaufsObjektImpl implements Obsttorte {
    private String kremsorte;
    private String obstsorte;

    public ObsttorteImpl(Manufacturer manufacturer, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String kremsorte, String obstsorte) {
        super(manufacturer, allergene, naehrwert, haltbarkeit, preis);
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

    public void setKremsorte(String kremsorte) {
        this.kremsorte = kremsorte;
    }

    public void setObstsorte(String obstsorte) {
        this.obstsorte = obstsorte;
    }
}
