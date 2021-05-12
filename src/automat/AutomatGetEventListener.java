package automat;

import events.*;
import events.EventListener;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import exceptions.InvalidInputException;

import java.util.*;

public class AutomatGetEventListener implements EventListener<GetEvent> {
    private AutomatImpl automat;
    private ErrorEventHandler<ErrorEvent> errorHandler;
    private CollectionOutputHandler<CollectionOutputEvent> collectionHandler;

    @Override
    public void addEvent(GetEvent event) {
        switch(event.getType()) {

            case getHersteller:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, hashmapToList(this.automat.checkHersteller())));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat is leer"));
                }
                break;
            case getAllergene:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, this.automat.checkAllergen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
                break;
            case getKuchen:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, automat.checkKuchen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
        }
    }

    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setAutomat(AutomatImpl automat) {
        this.automat = automat;
    }

    public void setCollectionHandler(CollectionOutputHandler<CollectionOutputEvent> collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    private List hashmapToList(HashMap<String, Integer> hashMap){
        LinkedList<HerstellerNummer> hnList = new LinkedList<>();       //source: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for(String key : hashMap.keySet()){
            hnList.add(new HerstellerNummer(key, hashMap.get(key)));
        }

        return hnList;
    }
}
