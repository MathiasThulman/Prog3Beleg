package kuchen;

import automat.Verkaufsobjekt;
import kuchen.Kuchen;

public interface Obstkuchen extends Kuchen, Verkaufsobjekt {
    String getObstsorte();
}
