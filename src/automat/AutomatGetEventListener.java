package automat;

import events.*;
import events.EventListener;
import exceptions.EmptyListException;

import java.util.*;

public class AutomatGetEventListener implements EventListener<InputGetEvent> {
    private Automat automat;
    private ErrorEventHandler<ErrorEvent> errorHandler;
    private CollectionOutputHandler<CollectionOutputEvent> collectionHandler;

    @Override
    public void addEvent(InputGetEvent event) {
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
            case getAbsentAllergene:
                try{
                    this.collectionHandler.handle(new CollectionOutputEvent(this, this.automat.checkAbsentAllergen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
            case getKuchen:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, automat.checkKuchen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
                break;
            case getKuchenSpecific:
                try{
                    this.collectionHandler.handle(new CollectionOutputEvent(this, automat.checkKuchen(event.getKuchenClass())));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Es gibt keine Kuchen dieses Typen"));
                }
        }
    }

    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

    public void setCollectionHandler(CollectionOutputHandler<CollectionOutputEvent> collectionHandler) {
        this.collectionHandler = collectionHandler;
    }

    private List<HerstellerNummer> hashmapToList(HashMap<String, Integer> hashMap){
        LinkedList<HerstellerNummer> hnList = new LinkedList<>();       //source: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        for(String key : hashMap.keySet()){
            hnList.add(new HerstellerNummer(key, hashMap.get(key)));
        }

        return hnList;
    }
}
