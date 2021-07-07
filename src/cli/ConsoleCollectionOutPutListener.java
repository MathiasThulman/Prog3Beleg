package cli;

import events.CollectionOutputEvent;
import events.EventListener;

public class ConsoleCollectionOutPutListener implements EventListener<CollectionOutputEvent> {
    private ConsoleView console;


    public void setConsole(ConsoleView console) {
        this.console = console;
    }

    @Override
    public void addEvent(CollectionOutputEvent event) {
        this.console.printCollectionEvent(event);
    }
}
