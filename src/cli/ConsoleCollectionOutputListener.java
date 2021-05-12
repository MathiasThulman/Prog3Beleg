package cli;

import events.CollectionOutputEvent;
import events.CollectionOutputListener;

public class ConsoleCollectionOutputListener implements CollectionOutputListener {
    Console console;

    public ConsoleCollectionOutputListener(Console console) {
        this.console = console;
    }

    @Override
    public void addEvent(CollectionOutputEvent event) {
        this.console.printCollectionEvent(event);
    }
}
