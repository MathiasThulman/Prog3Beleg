package automat;

import java.util.Date;

public interface KuchenDekorator extends Kuchen, Verkaufsobjekt{
    Date getEinfuegeDatum();
    String getName();
    void setInspektionsDatum(Date date);
    void setFachNummer(int fachnummer);
    void setEinfuegeDatum(Date date);
}
