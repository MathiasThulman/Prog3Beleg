import automat.*;
import cli.*;
import events.*;

import java.util.LinkedList;

public class App {
    public static void main(String[] args) {
        AutomatImpl automat = new AutomatImpl(10);
        Console console = new Console();
        InputStringEventHandler stringHandler = new InputStringEventHandler();
        InputIntEventHandler intHandler = new InputIntEventHandler();
        GetEventHandler getHandler = new GetEventHandler();
        ErrorEventHandler errorHandler = new ErrorEventHandler();
        CollectionOutputHandler collectionHandler = new CollectionOutputHandler();

        AutomatInputStringListener autoStringListener = new AutomatInputStringListener();
        autoStringListener.setAutomat(automat);
        stringHandler.add(autoStringListener);
        console.setStringHandler(stringHandler);
        AutomatInputIntListener autoIntListener = new AutomatInputIntListener();
        autoIntListener.setAutomat(automat);
        intHandler.add(autoIntListener);
        console.setIntHandler(intHandler);
        AutomatGetEventListener autoGetListner = new AutomatGetEventListener();
        autoGetListner.setAutomat(automat);
        getHandler.add(autoGetListner);
        console.setGetHandler(getHandler);

        ConsoleErrorListener consoleErrorListener = new ConsoleErrorListener(console);
        ConsoleCollectionOutputListener consoleCollectionListener = new ConsoleCollectionOutputListener(console);
        errorHandler.add(consoleErrorListener);
        collectionHandler.add(consoleCollectionListener);
        autoStringListener.setErrorHandler(errorHandler);
        autoIntListener.setErrorhandler(errorHandler);
        autoGetListner.setErrorHandler(errorHandler);
        autoGetListner.setCollectionHandler(collectionHandler);
        autoIntListener.setCollectionHandler(collectionHandler);

        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        AutomatAllergenObserver allergenObserver = new AutomatAllergenObserver(automat);

        console.start();
    }
}
