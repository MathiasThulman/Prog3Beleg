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
            if ((input.split(" ").length - 6) % 5 == 0) {
                String[] ps = input.split(" ");
                if(ps[2].contains(",")){
                    ps[2] = ps[2].replace(",",".");
                }

                //TODO make it safer to put in nonsense

                switch (ps[0]) {
                    case OBSTKUCHEN:
                        ObstkuchenImpl boden1 = new ObstkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]),
                                Duration.ofDays(Integer.parseInt(ps[4])), new BigDecimal((ps[2])));
                        if(ps.length == 6){
                            this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, boden1));
                        } else {
                            this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, buildKuchen(boden1, ps)));
                        }
//                        int counter = 6;
//                        KuchenDekorator boden = new ObstkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]), Duration.ofDays(Integer.parseInt(ps[4])), new BigDecimal((ps[2])));
//                        KuchenDekorator kuchen1 = boden;
//                        while(counter < ps.length){
//                            kuchen1 = new KuchenBelag( ps[counter] , new BigDecimal(ps[counter + 1]) , Integer.parseInt(ps[counter + 2]),
//                                    stringToDuration(ps[counter + 3]), stringToAllergen(ps[counter  + 4]), kuchen1);
//                            counter += 5;
//                        }
                        break;
                    case KREMKUCHEN:
                        KremkuchenImpl boden2 = new KremkuchenImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]),
                                Duration.ofDays(Integer.parseInt(ps[4])), new BigDecimal((ps[2])));
                        if(ps.length == 6){
                            this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, boden2));
                        } else {
                            this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, buildKuchen(boden2, ps)));
                        }
                        break;
                    case OBSTTORTE:
                        Duration dur3 = Duration.ofDays(Integer.parseInt(ps[4]));
                        ObsttorteImpl boden3 = new ObsttorteImpl(new HerstellerImpl(ps[1]), stringToAllergen(ps[5]), Integer.parseInt(ps[3]),
                                dur3, new BigDecimal((ps[2])));

                        if(ps.length == 6){
                            this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, boden3));
                        } else {
                            this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, buildKuchen(boden3, ps)));
                        }
//                        this.kuchenHandler.handle(new InputKuchenEvent<ObsttorteImpl>(this, EventType.addKuchen, obsttorte));
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

    private Duration stringToDuration(String string){
        return Duration.ofDays(Integer.parseInt(string));
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

    private KuchenDekorator buildKuchen(KuchenVerkaufsObjektImpl boden, String[] ps){
        int counter = 6;
        KuchenDekorator kuchen1 = boden;
        while(counter < ps.length){
            kuchen1 = new KuchenBelag( ps[counter] , new BigDecimal(ps[counter + 1]) , Integer.parseInt(ps[counter + 2]),
                    stringToDuration(ps[counter + 3]), stringToAllergen(ps[counter  + 4]), kuchen1);
            counter += 5;
        }
        return kuchen1;
    }

    private HashSet<Allergen> stringToAllergen(String s) {
        HashSet<Allergen> set = new HashSet<>();
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
