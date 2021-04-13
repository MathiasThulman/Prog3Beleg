package kuchen;

import automat.Manufacturer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class KremkuchenImpl extends KuchenVerkaufsObjektImpl implements Kremkuchen {
    private String kremsorte;

    public KremkuchenImpl(Manufacturer manufacturer, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String kremSorte) {
        super(manufacturer, allergene, naehrwert, haltbarkeit, preis);
        this.kremsorte = kremSorte;
    }

    @Override
    public String getKremsorte() {
        return null;
    }

    public void setKremsorte(String kremsorte) {
        this.kremsorte = kremsorte;
    }
}
