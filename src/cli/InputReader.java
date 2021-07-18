package cli;

import automat.*;
import events.*;

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
        if(input.split(" ").length == 1 && this.stringHandler != null){
            this.stringHandler.handle(new InputStringEvent(this, EventType.addHersteller, input));
        } else if (this.kuchenHandler != null) {
            if (input.split(" ").length == 7 || input.split(" ").length == 8) {
                String[] ps = input.split(" ");
                //TODO change price string to you can write bigdecimal with , and make it safer to put in nonsense

                switch (ps[0]) {
                    case OBSTKUCHEN:
                        Duration dur1 = Duration.ofDays(Integer.parseInt(ps[4]));
                        ObstkuchenImpl obstkuchen = new ObstkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]), dur1, new BigDecimal((ps[2])), new Obstsorte(ps[6]));
                        this.kuchenHandler.handle(new InputKuchenEvent<ObstkuchenImpl>(this, EventType.addKuchen, obstkuchen));
                        break;
                    case KREMKUCHEN:
                        Duration dur2 = Duration.ofDays(Integer.parseInt(ps[4]));
                        KremkuchenImpl kremkuchen = new KremkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]), dur2, new BigDecimal((ps[2])), new Kremsorte(ps[6]));
                        this.kuchenHandler.handle(new InputKuchenEvent<KremkuchenImpl>(this, EventType.addKuchen, kremkuchen));
                        break;
                    case OBSTTORTE:
                        Duration dur3 = Duration.ofDays(Integer.parseInt(ps[4]));
                        ObsttorteImpl obsttorte = new ObsttorteImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]), dur3,
                                new BigDecimal((ps[2])), new Kremsorte(ps[6]), new Obstsorte(ps[7]));
                        this.kuchenHandler.handle(new InputKuchenEvent<ObsttorteImpl>(this, EventType.addKuchen, obsttorte));
                        break;
                }
            }
        }
    }

    public void readDelete(String input) {
        if (isNumeric(input) && this.intHandler != null) {
            this.intHandler.handle(new InputIntEvent(this, EventType.removeKuchen, Integer.parseInt(input)));
        } else if (this.stringHandler != null && !isNumeric(input)){
            this.stringHandler.handle(new InputStringEvent(this, EventType.remHersteller, input));
        }
    }

    public void readDisplay(String input) {
        if(this.getHandler == null){
            return;
        }
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
                } else if (input.split(" ")[0].equals("allergene")) {
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
        if(this.intHandler == null){
            return;
        }
        if(isNumeric(input)){
            try {
                this.intHandler.handle(new InputIntEvent(this, EventType.setDate, Integer.parseInt(input)));
            } catch (NumberFormatException e) {
                System.out.println("Falsches nummernformat");;
            }
        }
    }

    public void readPersistence(String input) {
        if(this.getHandler == null){
            return;
        }
        if(input.equals("saveJOS") ){
            getHandler.handle(new InputGetEvent(this, EventType.saveAutomat));
        } else if (input.equals("loadJOS")){
            getHandler.handle(new InputGetEvent(this, EventType.loadAutomat));
        }
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
        if(s.equals(",")){
            return set;
        }

        try {
            String[] paraString = s.split(",");
            for (int i = 0; i < paraString.length; i++) {
                set.add(Allergen.valueOf(paraString[i]));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Illegales Argument, Allergene falsch Eingegeben");;
        }
        return set;
    }

    public void setKuchenHandler(InputKuchenEventHandler<InputKuchenEvent> kuchenHandler) {
        this.kuchenHandler = kuchenHandler;
    }
}
