package cli;

import events.CollectionOutputEvent;
import events.CollectionOutputListener;
import events.EventListener;

public class ConsoleCollectionOutputListener implements EventListener<CollectionOutputEvent> {
    Console console;

    public ConsoleCollectionOutputListener(Console console) {
        this.console = console;
    }

    @Override
    public void addEvent(CollectionOutputEvent event) {
        this.console.printCollectionEvent(event);
    }
}
