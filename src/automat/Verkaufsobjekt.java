package automat;

import java.math.BigDecimal;
import java.util.Date;

interface Verkaufsobjekt {
    BigDecimal getPreis();
    Date getInspektionsdatum();
    int getFachnummer();
}
