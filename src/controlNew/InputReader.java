package controlNew;

import events.*;

import java.util.Scanner;



public class InputReader {
    private GetEventHandler<GetEvent> getHandler;
    private InputIntEventHandler<InputIntEvent> intHandler;
    private InputStringEventHandler<InputStringEvent> stringHandler;

    private final String OBSTKUCHEN = "Obstkuchen";
    private final String OBSTTORTE = "Obsttorte";
    private final String KREMKUCHEN = "Kremkuchen";

    public void readInsert(String input) {
        int cutInt = input.indexOf(' ');


    }

    public void readDelete(String input) {
        if(checkForInt(input)){
           this.intHandler.handle(new InputIntEvent(this, EventType.removeKuchen, Integer.parseInt(input)));
        } else {
          this.stringHandler.handle(new InputStringEvent(this, EventType.remHersteller, input));
        }
    }

    public void readDisplay(String input) {
        switch(input.split(" ").length){
            case 1:
                if(input.equals("hersteller")){
                    this.getHandler.handle(new GetEvent(this, EventType.getHersteller));
                } else if (input.equals("kuchen")){
                    this.getHandler.handle(new GetEvent(this, EventType.getKuchen));
                }
                break;
            case 2:
                
        }

        //TODO split for other types
    }

    private String[] cutString(String input) {
        return input.split(" ");

    }

    public void readChange(String input) {

    }

    public void readPersistence(String input) {

    }

    private boolean checkForInt(String input){
        Scanner scanner = new Scanner(input);
        int x = -1;
        boolean isNumber;

        do {
            if (scanner.hasNextInt()){
                x = scanner.nextInt();
                isNumber = true;
            }else {
                isNumber = false;
                scanner.next();
            }
        } while (!(isNumber));

        return isNumber;
    }

    public void setGetHandler(GetEventHandler<GetEvent> getHandler) {
        this.getHandler = getHandler;
    }

    public void setIntHandler(InputIntEventHandler<InputIntEvent> intHandler) {
        this.intHandler = intHandler;
    }

    public void setStringHandler(InputStringEventHandler<InputStringEvent> stringHandler) {
        this.stringHandler = stringHandler;
    }
}
