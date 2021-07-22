package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class KuchenVerkaufsObjektImpl implements KuchenKomponente {
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
        if (this.inspektionsDatum != null && this.einfuegeDatum != null) {
            return this.getName() + ", " + this.hersteller.getName() + ", " + this.allergene.toString() + ", " + this.getRemainingDuration().toDays() +
                    ", " + this.inspektionsDatum.toString() + ", " + this.preis.toString();
        } else {
            return this.getName() + ", " + this.hersteller.getName() + ", " + this.allergene.toString() + ", " + this.haltbarkeit.toDays() +
                    ", " + "kein Inspektionsdatum" + ", " + this.preis.toString();
        }
    }

    private Duration getRemainingDuration(){
        return this.haltbarkeit.minusDays((new Date().getTime() - this.einfuegeDatum.getTime()) / (86400000));
    }

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
