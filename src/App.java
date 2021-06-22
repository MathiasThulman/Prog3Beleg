import automat.*;
import cli.*;
import events.*;

public class App {
    public static void main(String[] args) {
        Automat automat = new Automat(10);
        ConsoleOld console = new ConsoleOld();
        InputStringEventHandler stringHandler = new InputStringEventHandler();
        InputIntEventHandler intHandler = new InputIntEventHandler();
        GetEventHandler getHandler = new GetEventHandler();
        ErrorEventHandler errorHandler = new ErrorEventHandler();
        CollectionOutputHandler collectionHandler = new CollectionOutputHandler();
        AutomatWrapper wrapper = new AutomatWrapper();
        wrapper.setAutomat(automat);

        AutomatInputStringListener autoStringListener = new AutomatInputStringListener();
        autoStringListener.setAutomatWrapper(wrapper);
        stringHandler.add(autoStringListener);
        console.setStringHandler(stringHandler);
        AutomatInputIntListener autoIntListener = new AutomatInputIntListener();
        autoIntListener.setAutomatWrapper(wrapper);
        intHandler.add(autoIntListener);
        console.setIntHandler(intHandler);
        AutomatGetEventListener autoGetListner = new AutomatGetEventListener();
        autoGetListner.setAutomatWrapper(wrapper);
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
