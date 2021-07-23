package automat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class KuchenBelag implements KuchenKomponente, Serializable {
    private KuchenKomponente kuchenBelag;
    private HashSet<Allergen> allergens;
    private final int naehrwert;
    private final Duration haltbarkeit;
    private final BigDecimal preis;
    private final String name;

    public KuchenBelag(String name,  BigDecimal preis, int naehrwert, Duration haltbarkeit, HashSet<Allergen> allergens, KuchenKomponente kuchenBelag) {
        this.kuchenBelag = kuchenBelag;
        this.allergens = allergens;
        allergens.addAll(this.kuchenBelag.getAllergene());
        this.naehrwert = naehrwert;
        this.haltbarkeit = haltbarkeit;
        this.preis = preis;
        this.name = name;
    }


    @Override
    public Hersteller getHersteller() {
        return kuchenBelag.getHersteller();
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return this.allergens;
    }

    @Override
    public int getNaehrwert() {
        return this.kuchenBelag.getNaehrwert() + this.naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        if(0 > this.haltbarkeit.compareTo(this.kuchenBelag.getHaltbarkeit())){
            return this.haltbarkeit;
        } else {
            return this.kuchenBelag.getHaltbarkeit();
        }
    }

    @Override
    public BigDecimal getPreis() {
        return this.kuchenBelag.getPreis().add(this.preis);
    }

    @Override
    public Date getInspektionsdatum() {
        return this.kuchenBelag.getInspektionsdatum();
    }

    @Override
    public int getFachnummer() {
        return this.kuchenBelag.getFachnummer();
    }

    @Override
    public Date getEinfuegeDatum() {
        return this.kuchenBelag.getEinfuegeDatum();
    }

    @Override
    public String getName() {
        return this.name  + " " + this.kuchenBelag.getName();
    }

    @Override
    public void setInspektionsDatum(Date date) {
        this.kuchenBelag.setInspektionsDatum(date);
    }

    @Override
    public void setFachNummer(int fachnummer) {
        this.kuchenBelag.setFachNummer(fachnummer);
    }

    @Override
    public void setEinfuegeDatum(Date date) {
        this.kuchenBelag.setEinfuegeDatum(date);
    }

    //this is not very elegant but makes listing in cli and gui a lot easier
    public String toString() {
        if (this.getInspektionsdatum() != null && this.getEinfuegeDatum() != null) {  //to avoid nullpointer exceptions when working with cakes out of automat and in debugging
            return this.getFachnummer() + ", " + this.getName() + ", " + this.getHersteller().getName() + ", " + this.getAllergene() + ", " + this.getRemainingDuration().toDays() +
                    ", " + this.getInspektionsdatum().toString() + ", " + this.getPreis().toString();
        } else {
            return this.getFachnummer() + ", " + this.getName() + ", " + this.getHersteller().getName() + ", " + this.getAllergene().toString() + ", " + this.getHaltbarkeit().toDays() +
                    ", " + "kein Inspektionsdatum" + ", " + this.getPreis().toString();
        }
    }

    private Duration getRemainingDuration(){
        return this.getHaltbarkeit().minusDays((new Date().getTime() - this.getEinfuegeDatum().getTime()) / (86400000));
    }
}
