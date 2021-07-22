package automat;

import java.io.Serializable;
import java.util.Date;

public interface KuchenKomponente extends Kuchen, Verkaufsobjekt, Serializable {
    Date getEinfuegeDatum();
    String getName();
    void setInspektionsDatum(Date date);
    void setFachNummer(int fachnummer);
    void setEinfuegeDatum(Date date);
}
