package cli;

import events.ErrorEvent;
import events.EventListener;

public class ConsoleErrorListener implements EventListener<ErrorEvent> {
    private final ConsoleOld console;

    public ConsoleErrorListener(ConsoleOld console) {
        this.console = console;
    }

    @Override
    public void addEvent(ErrorEvent event) {
        this.console.printError(event);
    }

//    @Override
//    public void addEvent(ErrorEvent event) {
//        this.console.printError(event);
//    }


}
