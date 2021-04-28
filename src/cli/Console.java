package cli;


import events.InputEventHandler;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.LinkedList;

public class Console {
    private InputEventHandler handler;

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
    public void setHandler(InputEventHandler handler){
        this.handler = handler;
    }

    public void start() throws FullAutomatException, InvalidInputException, AlreadyExistsException, EmptyListException {
        CommandFactory cf = new CommandFactory(this);
        LinkedList<ICommand> cmds = cf.returnsCommandList();
        do {
            System.out.println(buildCLIMenu(cmds));
            ICommand selectedCMD = selectCommand(cmds);
            selectedCMD.execute();
        } while (true);
    }

    public InputEventHandler getHandler() {
        return handler;
    }
}
