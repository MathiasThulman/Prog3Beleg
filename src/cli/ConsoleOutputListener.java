package cli;

import events.OutputEvenListener;
import events.OutputStringEvent;

public class ConsoleOutputListener implements OutputEvenListener {
    private Console console;

    public void setConsole(Console console) {
        this.console = console;
    }

    @Override
    public void addEvent(OutputStringEvent event) {
        //TODO make the console print?
    }
}
