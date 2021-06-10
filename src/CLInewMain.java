import automat.*;
import cli.ConsoleCollectionOutputListener;
import cli.ConsoleOld;
import cliNew.*;
import events.*;

public class CLInewMain {
    public static void main(String[] args) {
        Automat automat = new Automat(10);
        Console console = new Console();
        InputReader reader = new InputReader();
        console.setInputReader(reader);
        ConsoleView view = new ConsoleView();

        InputStringEventHandler<InputStringEvent> stringHandler = new InputStringEventHandler<>();
        InputIntEventHandler<InputIntEvent> intHandler = new InputIntEventHandler<>();
        GetEventHandler<InputGetEvent> getHandler = new GetEventHandler<>();
        InputKuchenEventHandler<InputKuchenEvent> kuchenHandler = new InputKuchenEventHandler<>();
        ErrorEventHandler<ErrorEvent> errorHandler = new ErrorEventHandler<>();
        CollectionOutputHandler<CollectionOutputEvent> collectionHandler = new CollectionOutputHandler<>();

        AutomatInputStringListener autoStringListener = new AutomatInputStringListener();
        autoStringListener.setAutomat(automat);
        stringHandler.add(autoStringListener);
        reader.setStringHandler(stringHandler);
        AutomatInputIntListener autoIntListener = new AutomatInputIntListener();
        autoIntListener.setAutomat(automat);
        intHandler.add(autoIntListener);
        reader.setIntHandler(intHandler);
        AutomatGetEventListener autoGetListner = new AutomatGetEventListener();
        autoGetListner.setAutomat(automat);
        getHandler.add(autoGetListner);
        reader.setGetHandler(getHandler);
        reader.setKuchenHandler(kuchenHandler);

        ConsoleErrorListener errorListener = new ConsoleErrorListener();
        errorHandler.add(errorListener);
        errorListener.setConsole(view);
        ConsoleCollectionOutPutListener collectionOutPutListener = new ConsoleCollectionOutPutListener();
        collectionHandler.add(collectionOutPutListener);
        collectionOutPutListener.setConsole(view);
        autoGetListner.setCollectionHandler(collectionHandler);
        autoIntListener.setCollectionHandler(collectionHandler);

        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        AutomatAllergenObserver allergenObserver = new AutomatAllergenObserver(automat);

        while (true){
            console.read();
        }
    }
}
