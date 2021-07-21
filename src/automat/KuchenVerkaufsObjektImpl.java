package automat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KuchenVerkaufsObjektImpl implements KuchenDekorator {
    private final Hersteller hersteller;
    private final Collection<Allergen> allergene;
    private final int naehrwert;
    private final Duration haltbarkeit;
    private final BigDecimal preis;
    private Date inspektionsDatum;
    private int fachNummer;
    private Date einfuegeDatum;

    public KuchenVerkaufsObjektImpl(Hersteller hersteller, Collection<Allergen> allergene, int naehrwert, Duration haltbarkeit, BigDecimal preis) {
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
//        this.einfuegeDatum = new Date(1982, 6,9);//to avoid nullpointer exceptions
//        this.inspektionsDatum = new Date(1982, 6,9);
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

    //TODO why does this have to be public
    public void setInspektionsDatum(Date inspektionsDatum) {
        this.inspektionsDatum = inspektionsDatum;
    }

    public void setFachNummer(int fachNummer) {
        this.fachNummer = fachNummer;
    }

    public String toString() {
        if (this.inspektionsDatum != null) {
            return this.getName() + ", " + this.hersteller.getName() + ", " + this.allergene.toString() + ", " + this.haltbarkeit.toDays() +
                    ", " + this.inspektionsDatum.toString() + ", " + this.preis.toString();
        } else {
            return this.getName() + ", " + this.hersteller.getName() + ", " + this.allergene.toString() + ", " + this.haltbarkeit.toDays() +
                    ", " + "kein Inspektionsdatum" + ", " + this.preis.toString();
        }
    }

    //TODO why is this public too
    public void setEinfuegeDatum(Date einfuegeDatum) {
        this.einfuegeDatum = einfuegeDatum;
    }

    public Date getEinfuegeDatum() {
        return einfuegeDatum;
    }

    @Override
    public String getName() {
        return "Langweiliger Kuchenboden";
    }
}
