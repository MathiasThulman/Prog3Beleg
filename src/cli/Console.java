package cli;


import events.*;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.Collection;
import java.util.LinkedList;

public class Console {
    private GetEventHandler getHandler;
    private InputIntEventHandler intHandler;
    private InputStringEventHandler stringHandler;

    public Console(){
    }

    private static String buildCLIMenu(LinkedList<ICommand> cmds) {
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
       builder.append("Welcome to the Cake Zone" + System.lineSeparator());
        builder.append(System.lineSeparator());
        for(int i = 1; i < cmds.size(); i++){
            ICommand cmd = cmds.get(i);
            builder.append(" " + i + "." + cmd.description() + System.lineSeparator());
        }
        builder.append(" " + 0 + "." + cmds.get(0).description() + System.lineSeparator());
        return builder.toString();
    }

    static private ICommand selectCommand(LinkedList<ICommand> cmds) {
        do{
            int select = InputReader.readIntFromStdin("Please select an option");
            if(select >= 0 && select < cmds.size()){
                return cmds.get(select);
            }   System.out.println("\tWarning -> Please select a valid option between 0 and " + (cmds.size() - 1) + ".");

        }
        while(true);
    }

    public void start()  {
        CommandFactory cf = new CommandFactory(this);
        LinkedList<ICommand> cmds = cf.returnsCommandList();
        do {
            System.out.println(buildCLIMenu(cmds));
            ICommand selectedCMD = selectCommand(cmds);
            selectedCMD.execute();
        } while (true);
    }



    public void printError(ErrorEvent error){
        System.out.println(error.getError());
    }
    public void printCollectionEvent(CollectionOutputEvent event){

        for(Object object : event.getEventColletion()){
            if(object != null) {
                System.out.println(object.toString());
            }
        }
    }

    public void setGetHandler(GetEventHandler getHandler) {
        this.getHandler = getHandler;
    }

    public GetEventHandler getGetHandler() {
        return getHandler;
    }

    public void setIntHandler(InputIntEventHandler intHandler) {
        this.intHandler = intHandler;
    }

    public InputIntEventHandler getIntHandler() {
        return intHandler;
    }

    public InputStringEventHandler getStringHandler() {
        return stringHandler;
    }

    public void setStringHandler(InputStringEventHandler stringHandler) {
        this.stringHandler = stringHandler;
    }
}
