package events;

import automat.Hersteller;

public class HerstellerNummer {
    private String herst;
    private int anzahl;

    public HerstellerNummer(String herst, int anzahl) {
        this.herst = herst;
        this.anzahl = anzahl;
    }

    public String getHerst() {
        return herst;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public String toString(){
        return this.herst + ": " + this.anzahl;
    }
}
