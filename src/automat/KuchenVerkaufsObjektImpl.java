package automat;

import kuchen.KuchenVerkaufsObjekt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KuchenVerkaufsObjektImpl implements KuchenVerkaufsObjekt, Serializable {
    private final Hersteller hersteller;
    private final Collection<Allergen> allergene;
    private final int naehrwert;
    private final Duration haltbarkeit;
    private final BigDecimal preis;
    private Date inspektionsDatum;
    private int fachNummer;

    public KuchenVerkaufsObjektImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis) {
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
    }


    @Override
    public Hersteller getHersteller() {
        return this.hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return this.allergene;
    }

    @Override
    public int getNaehrwert() {
        return this.naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return this.haltbarkeit;
    }

    @Override
    public BigDecimal getPreis() {
        return this.preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.inspektionsDatum;
    }

    @Override
    public int getFachnummer() {
        return this.fachNummer;
    }

    protected void setInspektionsDatum(Date inspektionsDatum) {
        this.inspektionsDatum = inspektionsDatum;
    }

    protected void setFachNummer(int fachNummer) {
        this.fachNummer = fachNummer;
    }

    public String toString(){
        return this.hersteller.getName() + ", " + this.allergene.toString()  + ", " + this.haltbarkeit.toDays()  + ", "+ this.inspektionsDatum.toString() + ", "+ this.preis.toString();
    }

}
