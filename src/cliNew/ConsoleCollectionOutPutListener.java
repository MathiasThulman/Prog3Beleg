package cliNew;

import cli.ConsoleOld;
import events.CollectionOutputEvent;
import events.EventListener;
import org.w3c.dom.events.Event;

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
