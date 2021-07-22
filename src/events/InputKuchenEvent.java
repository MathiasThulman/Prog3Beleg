package events;

import automat.KuchenKomponente;

public class InputKuchenEvent extends Event {
    KuchenKomponente kuchenObjekt;

    public InputKuchenEvent(Object source, EventType type, KuchenKomponente kuchenObjekt) {
        super(source, type);
        this.kuchenObjekt = kuchenObjekt;
    }

    public KuchenKomponente getKuchenObjekt() {
        return kuchenObjekt;
    }
}
