package cliNew;

import cli.ConsoleOld;
import events.ErrorEvent;
import events.EventListener;

public class ConsoleErrorListener implements EventListener<ErrorEvent> {
    private ConsoleView console;


    @Override
    public void addEvent(ErrorEvent event) {
        this.console.printError(event);
    }

    public void setConsole(ConsoleView console) {

        this.console = console;
    }
}
