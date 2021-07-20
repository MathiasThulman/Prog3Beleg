package events;

import automat.KuchenDekorator;
import automat.KuchenVerkaufsObjektImpl;

public class InputKuchenEvent extends Event {
    KuchenDekorator kuchenObjekt;

    public InputKuchenEvent(Object source, EventType type, KuchenDekorator kuchenObjekt) {
        super(source, type);
        this.kuchenObjekt = kuchenObjekt;
    }

    public KuchenDekorator getKuchenObjekt() {
        return kuchenObjekt;
    }
}
