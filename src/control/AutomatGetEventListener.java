package control;

import events.*;
import events.EventListener;
import exceptions.EmptyListException;

import java.util.*;

public class AutomatGetEventListener implements EventListener<InputGetEvent> {
    private AutomatWrapper automatWrapper;
    private ErrorEventHandler<ErrorEvent> errorHandler;
    private CollectionOutputHandler<CollectionOutputEvent> collectionHandler;

    @Override
    public void addEvent(InputGetEvent event) {
        switch(event.getType()) {

            case getHersteller:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, hashmapToList(this.automatWrapper.getAutomat().checkHersteller())));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat is leer"));
                }
                break;
            case getAllergene:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, this.automatWrapper.getAutomat().checkAllergen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
                break;
            case getAbsentAllergene:
                try{
                    this.collectionHandler.handle(new CollectionOutputEvent(this, this.automatWrapper.getAutomat().checkAbsentAllergen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
            case getKuchen:
                try {
                    this.collectionHandler.handle(new CollectionOutputEvent(this, this.automatWrapper.getAutomat().checkKuchen()));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Der Automat ist leer"));
                }
                break;
            case getKuchenSpecific:
                try{
                    this.collectionHandler.handle(new CollectionOutputEvent(this, this.automatWrapper.getAutomat().checkKuchen(event.getKuchenClass())));
                } catch (EmptyListException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "Es gibt keine Kuchen dieses Typen"));
                }
                break;
            case saveAutomat:
                try {
                    this.automatWrapper.getSerializer().serialize("AutomatSaveFile", this.automatWrapper.getAutomat());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case loadAutomat:
                try {
                    this.automatWrapper.setAutomat(this.automatWrapper.getSerializer().deserialize("AutomatSaveFile"));
                } catch (Exception e ) {
                    e.printStackTrace();
                }
        }
    }

    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setAutomatWrapper(AutomatWrapper wrapper) {
        this.automatWrapper = wrapper;
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
