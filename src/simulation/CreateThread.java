package simulation;

import automat.Allergen;
import automat.Automat;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.FullAutomatException;
import kuchen.KremkuchenImpl;
import kuchen.KuchenVerkaufsObjektImpl;
import kuchen.ObstkuchenImpl;
import kuchen.ObsttorteImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class CreateThread extends Thread {
    private Automat automat;
    private final String MASCARPONE = "Mascarpone";
    private final String SENF = "Senf";
    private final String KIRSCHE = "Kirsche";
    private final String ERDBEERE = "Erdbeere";
    private final String BENJAMIN = "benjamin";
    private final String BLUEMCHEN = "blümchen";
    private final String MOSES = "moses";
    private final Hersteller herst1 = new HerstellerImpl(BENJAMIN);
    private final Hersteller herst2 = new HerstellerImpl(BLUEMCHEN);
    private final Hersteller herst3 = new HerstellerImpl(MOSES);
    private final Duration dur1 = Duration.ofDays(4);
    private final LinkedList<Allergen> allergList1 = new LinkedList<>(Arrays.asList(Allergen.Erdnuss, Allergen.Haselnuss));
    private final LinkedList<Allergen> allergList2 = new LinkedList<>(Arrays.asList(Allergen.Gluten, Allergen.Sesamsamen));
    private final KremkuchenImpl kuch1 = new KremkuchenImpl(herst1, allergList1, 300, dur1, new BigDecimal(500), MASCARPONE);
    private final ObstkuchenImpl kuch2 = new ObstkuchenImpl(herst1, allergList2, 400, dur1, new BigDecimal(250), KIRSCHE);
    private final ObsttorteImpl kuch3 = new ObsttorteImpl(herst1, allergList2, 500, dur1, new BigDecimal(300), MASCARPONE, ERDBEERE);
    private final KremkuchenImpl kuch4 = new KremkuchenImpl(herst1, allergList1,250 , dur1, new BigDecimal(400), SENF);
    private final KremkuchenImpl kuch5 = new KremkuchenImpl(herst2, allergList2, 300, dur1, new BigDecimal(500), MASCARPONE);
    private final ObstkuchenImpl kuch6 = new ObstkuchenImpl(herst2, allergList1, 400, dur1, new BigDecimal(250), KIRSCHE);
    private final ObsttorteImpl kuch7 = new ObsttorteImpl(herst2, allergList2, 500, dur1, new BigDecimal(300), MASCARPONE, ERDBEERE);
    private final KremkuchenImpl kuch8 = new KremkuchenImpl(herst3, allergList1,250 , dur1, new BigDecimal(400), SENF);
    private final ObsttorteImpl kuch9 = new ObsttorteImpl(herst3, allergList2, 500, dur1, new BigDecimal(300), MASCARPONE, ERDBEERE);
    private final KremkuchenImpl kuch10 = new KremkuchenImpl(herst3, allergList1,250 , dur1, new BigDecimal(400), SENF);
    private final KuchenVerkaufsObjektImpl[] kuchList = {kuch1,kuch2,kuch3,kuch4,kuch5,kuch6,kuch7,kuch8,kuch9,kuch10};


    public void run(){
        while(true){
            try {
                this.automat.addKuchen(kuchList[(int) (Math.random() * 10)]);
                System.out.println("Thread added Cake");
            } catch (FullAutomatException e)  {
                System.out.println("simulation: automat voll");
            }
        }
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }
}
