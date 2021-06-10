package cli;

import events.CollectionOutputEvent;
import events.EventListener;

public class ConsoleCollectionOutputListener implements EventListener<CollectionOutputEvent> {
    ConsoleOld console;

    public ConsoleCollectionOutputListener(ConsoleOld console) {
        this.console = console;
    }

    @Override
    public void addEvent(CollectionOutputEvent event) {
        this.console.printCollectionEvent(event);
    }
}
