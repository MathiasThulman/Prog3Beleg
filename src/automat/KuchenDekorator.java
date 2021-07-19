package automat;

import java.util.Date;

public interface KuchenDekorator extends Kuchen, Verkaufsobjekt{
    Date getEinfuegeDatum();
    String getName();
}
