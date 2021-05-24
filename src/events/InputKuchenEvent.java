package events;

import automat.KuchenVerkaufsObjektImpl;

public class InputKuchenEvent<T extends KuchenVerkaufsObjektImpl> extends Event {
    T kuchenObjekt;

    public InputKuchenEvent(Object source, EventType type, T kuchenObjekt) {
        super(source, type);
        this.kuchenObjekt = kuchenObjekt;
    }

    public T getKuchenObjekt() {
        return kuchenObjekt;
    }
}
