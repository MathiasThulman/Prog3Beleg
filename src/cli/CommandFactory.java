package cli;

import events.InputEvent;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.LinkedList;

public class CommandFactory {
    private final String sPleaseEnterHersteller = "bitte geben sie einen Herstellernamen an";
    private final String sKuchenWahl = "Wählen sie einen Kucheen zum hinzufügen";
    private final String sFachnummer = "bitte geben sie eine Fachnummer ein";
    private final String sPleaseEnterKuchen = "bitte wählen sie einen KuchenTyp";
    private final String sPleaseEnterDay = "bitte geben sie den tag ein";
    private final String sPleaseEntherMonth = "bitte geben sie den monat ein";
    private final String sPleaseEnterYear = "bitte geben sie ein jahr ein";
    private final Console console;
    private final String sExitMessage = "Close Program";
    private final String KUCHENWAHL = "bitte wähle einen Kuchen" + System.lineSeparator()  +
            "1. Erdbeerkuchen" + System.lineSeparator() + "2. Butterkremkuchen";
    private final String ADDKUCHEN = "addKuchen";
    private final String ADDHERSTELLER = "addHersteller";
    private final String REMOVEHERSTELLER = "removeHersteller";
    private final String REMOVEKUCHEN = "removeKuchen";
    private final String GETKUCHEN = "getKuchen";
    private final String CHECKHERSTELLER = "checkHersteller";
    private final String CHECKKUCHEN = "checkKuchen";
    private final String CHECKKUCHESPECIFIC = "checkKuchenSpecific";
    private final String CHECKALLERGEN = "checkAllergen";
    private final String SETINSPECTIONDATE = "setInspectionDate";

    public CommandFactory(Console console){
        this.console = console;

    }

    public LinkedList<ICommand> returnsCommandList(){
        LinkedList<ICommand> cmds = new LinkedList<>();
        cmds.add(createExit());
        cmds.add(addKuchen());
        cmds.add(addHersteller());
        cmds.add(getKuchen());
        cmds.add(removeKuchen());
        cmds.add(removeHersteller());
        cmds.add(checkHersteller());
        cmds.add(checkKuchen());
        cmds.add(checkKuchenSpecific());
        cmds.add(checkAllergen());
        cmds.add(setInspectionDate());
        return cmds;
    }

    private ICommand createExit() {
        return new ICommand() {
            @Override
            public void execute() {
                System.out.println(sExitMessage);
                System.exit(0);
            }

            @Override
            public String description() {
                return "Exit";
            }
        };
    }

    private ICommand addHersteller() {
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = ADDHERSTELLER + "#" + InputReader.readStringfromStdIn(sPleaseEnterHersteller);
                InputEvent e=new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "1. Füge deinem Automat einen Hersteller Hinzu";
            }
        };
    }

    private ICommand addKuchen() {
        return new ICommand() {
            @Override
            public void execute() {
                String text = ADDKUCHEN + "#" + InputReader.readStringfromStdIn(KUCHENWAHL); //TODO kuchen bauen
            }

            @Override
            public String description() {
                return "2. Füge deinem Automaten einen Kuchen Hinzu";
            }
        };
    }

    private ICommand getKuchen(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = GETKUCHEN + "#" + InputReader.readIntFromStdin(sFachnummer);
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "3. Zeige einen Kuchen an bestimmer Fachnummer";
            }
        };
    }

    private ICommand removeKuchen(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = REMOVEKUCHEN + "#" + InputReader.readIntFromStdin(sFachnummer);
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "4. entferne Kuchen aus Fachnummer";
            }
        };
    }

    private ICommand removeHersteller(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = REMOVEHERSTELLER + "#" + InputReader.readStringfromStdIn(sPleaseEnterHersteller);
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "5. Entferne einen Hersteller und alle seine Kuchen aus dem Automat";
            }
        };
    }

    private ICommand checkHersteller(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = CHECKHERSTELLER;
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "6. Zeige mir die Liste aller Hersteller und wie viele Kuchen sie haben";
            }
        };
    }

    private ICommand checkKuchen(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = CHECKKUCHEN;
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "7. Zeige mir alle Kuchen Im Automat";
            }
        };
    }

    private ICommand checkKuchenSpecific(){
        return  new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = CHECKKUCHESPECIFIC + "#" + InputReader.readStringfromStdIn(sPleaseEnterKuchen);
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "8. Zeige mir alle Kuchen eines Bestimmen typ";
            }
        };
    }

    private ICommand checkAllergen(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = CHECKALLERGEN;
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "9. Zeige mir alle Allergene die in den Kuchen des Automats auftauchen";
            }
        };
    }

    private ICommand setInspectionDate(){
        return new ICommand() {
            @Override
            public void execute() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
                String text = SETINSPECTIONDATE + "#" + InputReader.readIntFromStdin(sPleaseEnterDay) + "." + InputReader.readIntFromStdin(sPleaseEntherMonth) + "." + InputReader.readIntFromStdin(sPleaseEnterYear);
                InputEvent e = new InputEvent(this,text);
                if(null != console.getHandler()) console.getHandler().handle(e);
            }

            @Override
            public String description() {
                return "10. Setze ein neues Inspektionsdatum für alle kuchen";
            }
        };
    }
}
