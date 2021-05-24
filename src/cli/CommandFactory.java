package cli;

import events.*;

import java.util.Date;
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
            "1. Erdbeerkuchen, hersteller faustulus" + System.lineSeparator() + "2. Butterkremkuchen, hersteller bob";

    public CommandFactory(Console console){
        this.console = console;
    }

    public LinkedList<ICommand> returnsCommandList(){
        LinkedList<ICommand> cmds = new LinkedList<>();
        cmds.add(createExit());
        cmds.add(addHersteller());
        cmds.add(addKuchen());
        cmds.add(getKuchen());
        cmds.add(removeKuchen());
        cmds.add(removeHersteller());
        //cmds.add(checkHersteller()); this does not work yet
        cmds.add(checkKuchen());
        //cmds.add(checkKuchenSpecific()); this does not work yet
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
            public void execute() {
                InputStringEvent e = new InputStringEvent(this, EventType.addHersteller, InputReader.readStringfromStdIn(sPleaseEnterHersteller));
                if(null != console.getStringHandler()) console.getStringHandler().handle(e);
            }

            @Override
            public String description() {
                return "Füge deinem Automat einen Hersteller Hinzu";
            }
        };
    }

    private ICommand addKuchen() {
        return new ICommand() {
            @Override
            public void execute() {
                InputIntEvent e = new InputIntEvent(this, EventType.addKuchen, InputReader.readIntFromStdin(KUCHENWAHL));
                if(null != console.getIntHandler()) console.getIntHandler().handle(e);
            }

            @Override
            public String description() {
                return "Füge deinem Automaten einen Kuchen Hinzu";
            }
        };
    }

    private ICommand getKuchen(){
        return new ICommand() {
            @Override
            public void execute() {
                InputIntEvent e = new InputIntEvent(this, EventType.getOneKuchen, InputReader.readIntFromStdin(sFachnummer));
                if(null != console.getIntHandler()) console.getIntHandler().handle(e);
            }

            @Override
            public String description() {
                return "Zeige einen Kuchen an bestimmer Fachnummer";
            }
        };
    }

    private ICommand removeKuchen(){
        return new ICommand() {
            @Override
            public void execute()  {
                InputIntEvent e = new InputIntEvent(this, EventType.removeKuchen, InputReader.readIntFromStdin(sFachnummer));
                if(null != console.getIntHandler()) console.getIntHandler().handle(e);
            }

            @Override
            public String description() {
                return "entferne Kuchen aus Fachnummer";
            }
        };
    }

    private ICommand removeHersteller(){
        return new ICommand() {
            @Override
            public void execute()  {
                InputStringEvent e = new InputStringEvent(this, EventType.remHersteller, InputReader.readStringfromStdIn(sPleaseEnterHersteller));
                if(null != console.getStringHandler()) console.getStringHandler().handle(e);
            }

            @Override
            public String description() {
                return "Entferne einen Hersteller und alle seine Kuchen aus dem Automat";
            }
        };
    }

    private ICommand checkHersteller(){
        return new ICommand() {
            @Override
            public void execute()  {
                InputGetEvent e = new InputGetEvent(this, EventType.getHersteller);
                if(null != console.getGetHandler()) console.getGetHandler().handle(e);
            }

            @Override
            public String description() {
                return "Zeige mir die Liste aller Hersteller und wie viele Kuchen sie haben";
            }
        };
    }

    private ICommand checkKuchen(){
        return new ICommand() {
            @Override
            public void execute() {
                InputGetEvent e = new InputGetEvent(this, EventType.getKuchen);
                if(null != console.getGetHandler()) console.getGetHandler().handle(e);
            }

            @Override
            public String description() {
                return "Zeige mir alle Kuchen Im Automat";
            }
        };
    }

    private ICommand checkKuchenSpecific(){
        return  new ICommand() {
            @Override
            public void execute() {
                InputGetEvent e = new InputGetEvent(this, EventType.getKuchenSpecific);//TODO nur einen kuchen ausgeben lassen
                if(null != console.getGetHandler()) console.getGetHandler().handle(e);
            }
            @Override
            public String description() {
                return "Zeige mir alle Kuchen eines Bestimmen typ";
            }
        };
    }

    private ICommand checkAllergen(){
        return new ICommand() {
            @Override
            public void execute() {
                InputGetEvent e = new InputGetEvent(this, EventType.getAllergene);
                if(null != console.getGetHandler()) console.getGetHandler().handle(e);
            }

            @Override
            public String description() {
                return "Zeige mir alle Allergene die in den Kuchen des Automats auftauchen";
            }
        };
    }

    private ICommand setInspectionDate(){
        return new ICommand() {
            @Override
            public void execute() {
                Date date = new Date(InputReader.readIntFromStdin(sPleaseEnterYear), InputReader.readIntFromStdin(sPleaseEntherMonth), InputReader.readIntFromStdin(sPleaseEnterDay));
                InputIntEvent e = new InputIntEvent(this, EventType.setDate, InputReader.readIntFromStdin(sFachnummer), date );
                if(null != console.getIntHandler()) console.getIntHandler().handle(e);
            }

            @Override
            public String description() {
                return "Setze ein neues Inspektionsdatum für einen kuchen";
            }
        };
    }
}
