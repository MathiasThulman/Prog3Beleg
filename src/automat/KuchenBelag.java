package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class KuchenBelag implements KuchenDekorator{
    private KuchenDekorator dekorator;
    private HashSet<Allergen> allergens;
    private int naehrwert;
    private Duration haltbarkeit;
    private BigDecimal preis;
    private String name;


    @Override
    public Hersteller getHersteller() {
        return dekorator.getHersteller();
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return null;
    }

    @Override
    public int getNaehrwert() {
        return 0;
    }

    @Override
    public Duration getHaltbarkeit() {
        return null;
    }

    @Override
    public BigDecimal getPreis() {
        return null;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.dekorator.getInspektionsdatum();
    }

    @Override
    public int getFachnummer() {
        return this.dekorator.getFachnummer();
    }

    @Override
    public Date getEinfuegeDatum() {
        return this.dekorator.getEinfuegeDatum();
    }

    @Override
    public String getName() {
        return this.dekorator.getName() + " " + this.name;
    }
}
