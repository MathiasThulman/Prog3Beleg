package automat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class KuchenBelag implements KuchenKomponente {
    private KuchenKomponente kuchenBelag;
    private HashSet<Allergen> allergens;
    private int naehrwert;
    private Duration haltbarkeit;
    private BigDecimal preis;
    private String name;

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
        return this.kuchenBelag.getName() + " " + this.name;
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

    public String toString(){
        return this.name + " "  + this.kuchenBelag.toString();
    }
}
