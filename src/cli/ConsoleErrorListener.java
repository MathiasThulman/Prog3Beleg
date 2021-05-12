package cli;

import events.ErrorEvent;
import events.ErrorEventListener;

public class ConsoleErrorListener implements ErrorEventListener {
    private Console console;

    public ConsoleErrorListener(Console console) {
        this.console = console;
    }

    @Override
    public void addEvent(ErrorEvent event) {
        this.console.printError(event);
    }
}
