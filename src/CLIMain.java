import automat.*;
import cli.*;
import events.*;
import util.JoSSerializer;

public class CLIMain {
    public static void main(String[] args) {
        int automatCap = 50;
        if(args.length == 1){
             automatCap =Integer.parseInt(args[1]);
        }
        Automat automat = new Automat(automatCap);
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
        AutomatWrapper automatWrapper = new AutomatWrapper();
        automatWrapper.setAutomat(automat);


        AutomatInputStringListener autoStringListener = new AutomatInputStringListener();
        AutomatInputKuchenListener kuchenListener = new AutomatInputKuchenListener();
        kuchenHandler.add(kuchenListener);
        kuchenListener.setAutomatWrapper(automatWrapper);
        kuchenListener.setErrorHandler(errorHandler);
        autoStringListener.setAutomatWrapper(automatWrapper);
        stringHandler.add(autoStringListener);
        reader.setStringHandler(stringHandler);
        AutomatInputIntListener autoIntListener = new AutomatInputIntListener();
        autoIntListener.setAutomatWrapper(automatWrapper);
        intHandler.add(autoIntListener);
        reader.setIntHandler(intHandler);
        AutomatGetEventListener autoGetListner = new AutomatGetEventListener();
        autoGetListner.setAutomatWrapper(automatWrapper);
        getHandler.add(autoGetListner);
        autoGetListner.setErrorHandler(errorHandler);
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
        autoStringListener.setErrorHandler(errorHandler);
        autoGetListner.setErrorHandler(errorHandler);
        autoIntListener.setErrorhandler(errorHandler);
        autoGetListner.setAutomatWrapper(automatWrapper);
        JoSSerializer serializer = new JoSSerializer();
        automatWrapper.setSerializer(serializer);

        AutomatCapacityObserver capacityObserver = new AutomatCapacityObserver(automat);
        AutomatAllergenObserver allergenObserver = new AutomatAllergenObserver(automat);

        while (true){
            console.read();
        }
    }
}
