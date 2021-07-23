package cli;

import automat.*;
import events.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;


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
            if ((input.split(" ").length - 2) % 5 == 0) {
                input = input.replace(",", ".");
                String[] ps = input.split(" ");

                switch (ps[0]) {
                    case OBSTKUCHEN:
                        try {
                            //duration on all boden high by default so upon aggregating the belaege arent ignored when boden 0
                            ObstkuchenImpl boden1 = new ObstkuchenImpl(new HerstellerImpl(ps[1]), new HashSet<>(), 0,
                                    Duration.ofDays(1000), new BigDecimal(0));
                            if(ps.length == 2){
                                this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, boden1));
                            } else {
                                this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, buildKuchen(boden1, ps)));
                            }
                        } catch (IllegalArgumentException e) {
                            return;
                        }

                        break;
                    case KREMKUCHEN:
                        try {
                            KremkuchenImpl boden2 = new KremkuchenImpl(new HerstellerImpl(ps[1]), new HashSet<>(), 0,
                                    Duration.ofDays(1000), new BigDecimal(0));
                            if(ps.length == 2){
                                this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, boden2));
                            } else {
                                this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, buildKuchen(boden2, ps)));
                            }
                        } catch (IllegalArgumentException e) {
                            return;
                        }
                        break;

                    case OBSTTORTE:
                        try {
                            ObsttorteImpl boden3 = new ObsttorteImpl(new HerstellerImpl(ps[1]), new HashSet<>(), 0,
                                    Duration.ofDays(1000), new BigDecimal(0));

                            if(ps.length == 2){
                                this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, boden3));
                            } else {
                                this.kuchenHandler.handle(new InputKuchenEvent(this, EventType.addKuchen, buildKuchen(boden3, ps)));
                            }
                        } catch (IllegalArgumentException e) {
                            return;
                        }

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
                            this.getHandler.handle(new InputGetEvent(this, EventType.getKuchenSpecific, "Kremkuchen"));
                            break;
                        case OBSTKUCHEN:
                            this.getHandler.handle(new InputGetEvent(this, EventType.getKuchenSpecific, "Obstkuchen"));
                            break;
                        case OBSTTORTE:
                            this.getHandler.handle(new InputGetEvent(this, EventType.getKuchenSpecific, "Obsttorte"));
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

    private KuchenKomponente buildKuchen(BasisKuchenImpl boden, String[] ps){
        int counter = 2;
        KuchenKomponente kuchen1 = boden;
        while(counter < ps.length){
            if(!isNumeric(ps[counter]) || !isNumeric(ps[counter + 1]) || !isNumeric(ps[counter + 2])){      //to prevent crashed on parseExceptions
                throw new IllegalArgumentException();
            }
            kuchen1 = new KuchenBelag( ps[counter + 4] , new BigDecimal(ps[counter]) , Integer.parseInt(ps[counter + 1]),
                    stringToDuration(ps[counter + 2]), stringToAllergen(ps[counter  + 3]), kuchen1);
            counter += 5;
        }
        return kuchen1;
    }

    private HashSet<Allergen> stringToAllergen(String s) {
        HashSet<Allergen> set = new HashSet<>();
        if(s.equals(".")){
            return set;
        }

        try {
            String[] paraString = s.split("\\.");
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
