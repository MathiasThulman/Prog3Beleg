package cliNew;

import automat.Allergen;
import automat.HerstellerImpl;
import events.*;
import kuchen.KremkuchenImpl;
import kuchen.ObstkuchenImpl;
import kuchen.ObsttorteImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;


public class InputReader {
    private GetEventHandler<InputGetEvent> getHandler;
    private InputIntEventHandler<InputIntEvent> intHandler;
    private InputStringEventHandler<InputStringEvent> stringHandler;
    private InputKuchenEventHandler<InputKuchenEvent> kuchenHandler;

    private final String KUCHEN = "kuchen";
    private final String HERSTELLER = "hersteller";
    private final String OBSTKUCHEN = "Obstkuchen";
    private final String OBSTTORTE = "Obsttorte";
    private final String KREMKUCHEN = "Kremkuchen";

    public void readInsert(String input) {
        if(input.split(" ").length == 1){
            this.stringHandler.handle(new InputStringEvent(this, EventType.addHersteller, input));
        } else if (input.split(" ").length == 8 || input.split(" ").length == 9){
            String[] ps = input.split(" ");

            switch(ps[0]){
                case OBSTKUCHEN:
                    Duration dur1 = Duration.ofDays(Integer.parseInt(ps[4]));
                    ObstkuchenImpl obstkuchen = new ObstkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[2]), Integer.parseInt(ps[3]), dur1, new BigDecimal(Integer.parseInt(ps[5])), ps[6]);
                    this.kuchenHandler.handle(new InputKuchenEvent<ObstkuchenImpl>(this, EventType.addKuchen, obstkuchen));
                    break;
                case KREMKUCHEN:
                    Duration dur2 = Duration.ofDays(Integer.parseInt(ps[4]));
                    KremkuchenImpl kremkuchen = new KremkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[2]), Integer.parseInt(ps[3]), dur2, new BigDecimal(Integer.parseInt(ps[5])), ps[6]);

                    break;
                case OBSTTORTE:
                    Duration dur3 = Duration.ofDays(Integer.parseInt(ps[4]));
                    ObsttorteImpl obsttorte = new ObsttorteImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[2]), Integer.parseInt(ps[3]), dur3, new BigDecimal(Integer.parseInt(ps[5])), ps[6], ps[7]);
                    this.kuchenHandler.handle(new InputKuchenEvent<ObsttorteImpl>(this, EventType.addKuchen, obsttorte));
                    break;
            }
        }
    }

    public void readDelete(String input) {
        if (isNumeric(input)) {
            this.intHandler.handle(new InputIntEvent(this, EventType.removeKuchen, Integer.parseInt(input)));
        } else {
            this.stringHandler.handle(new InputStringEvent(this, EventType.remHersteller, input));
        }
    }

    public void readDisplay(String input) {
        switch (input.split(" ").length) {
            case 1:
                if (input.equals(HERSTELLER)) {
                    this.getHandler.handle(new InputGetEvent(this, EventType.getHersteller));
                } else if (input.equals(KUCHEN)) {
                    this.getHandler.handle(new InputGetEvent(this, EventType.getKuchen));
                }
                break;
            case 2:
                if (input.split(" ")[0].equals(KUCHEN)) {
                    switch (input.split(" ")[1]) {
                        case KREMKUCHEN:
                            this.getHandler.handle(new InputGetEvent(this, EventType.getKuchenSpecific, KremkuchenImpl.class));
                            break;
                        case OBSTKUCHEN:
                            this.getHandler.handle(new InputGetEvent(this, EventType.getKuchenSpecific, ObstkuchenImpl.class));
                            break;
                        case OBSTTORTE:
                            this.getHandler.handle(new InputGetEvent(this, EventType.getKuchenSpecific, ObsttorteImpl.class));
                            break;
                    }
                } else if (input.split(" ")[0].equals("Allergene")) {
                    if (input.split(" ")[1].equals("i")) {
                        this.getHandler.handle(new InputGetEvent(this, EventType.getAllergene));
                    } else if (input.split(" ")[1].equals("e")) {
                        this.getHandler.handle(new InputGetEvent(this, EventType.getAbsentAllergene));
                    }
                }
                break;
        }
    }

    public void readChange(String input) {
        if(isNumeric(input)){
            this.intHandler.handle(new InputIntEvent(this, EventType.removeKuchen,Integer.parseInt(input)));
        } else {
            this.stringHandler.handle(new InputStringEvent(this, EventType.remHersteller, input));
        }
    }

    public void readPersistence(String input) {

    }

    private boolean isNumeric(String str) {         //source: https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void setGetHandler(GetEventHandler<InputGetEvent> getHandler) {
        this.getHandler = getHandler;
    }

    public void setIntHandler(InputIntEventHandler<InputIntEvent> intHandler) {
        this.intHandler = intHandler;
    }

    public void setStringHandler(InputStringEventHandler<InputStringEvent> stringHandler) {
        this.stringHandler = stringHandler;
    }

    private Set<Allergen> stringToAllergen(String s) {
        Set<Allergen> set = new HashSet<>();

        String[] paraString = s.split(",");
        for (String value : paraString) {
            set.add(Allergen.valueOf(value));
        }
        return set;
    }
}
