package kuchen;

import automat.Allergen;
import automat.Hersteller;
import automat.Kremkuchen;
import automat.KuchenVerkaufsObjektImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class KremkuchenImpl extends KuchenVerkaufsObjektImpl implements Kremkuchen {
    private String kremsorte;

    public KremkuchenImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis, String kremSorte) {
        super(hersteller, allergene, naehrwert, haltbarkeit, preis);
        this.kremsorte = kremSorte;
    }

    @Override
    public String getKremsorte() {
        return this.kremsorte;
    }

    public void setKremsorte(String kremsorte) {
        this.kremsorte = kremsorte;
    }

    @Override
    public String toString(){
        return this.kremsorte + ", " + super.toString();
    }

}
