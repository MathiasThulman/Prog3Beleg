package automat;

import events.*;
import exceptions.InvalidInputException;

import java.util.Collections;
import java.util.NoSuchElementException;

public class AutomatInputIntListener implements EventListener<InputIntEvent> {
    private AutomatWrapper automatWrapper;
    private ErrorEventHandler<ErrorEvent> errorhandler;
    private CollectionOutputHandler<CollectionOutputEvent> collectionHandler;


    @Override
    public void addEvent(InputIntEvent event) {
        switch (event.getType()) {
            case removeKuchen:
                try {
                    this.automatWrapper.getAutomat().removeKuchen(event.getInputInt());
                } catch (InvalidInputException e) {
                    errorhandler.handle(new ErrorEvent(this, "Ungütlige eingabe, diese fachnummer existiert nicht"));
                } catch (NoSuchElementException e){
                    this.errorhandler.handle(new ErrorEvent(this, "an dieser Stelle befindet sich kein Kuchen"));
                }
                break;
            case getOneKuchen:
                try {
                    collectionHandler.handle(new CollectionOutputEvent(this, Collections.singleton(this.automatWrapper.getAutomat().getKuchen(event.getInputInt())))); //nicht so schön
                } catch (InvalidInputException e) {
                    this.errorhandler.handle(new ErrorEvent(this, "Ungültige Eingabe"));
                } catch (NoSuchElementException e){
                    this.errorhandler.handle(new ErrorEvent(this, "an dieser Stelle befindet sich kein Kuchen"));
                }
                break;
            case setDate:
                try {
                    this.automatWrapper.getAutomat().setInspectionDate(event.getInputInt());
                } catch (InvalidInputException e) {
                    this.errorhandler.handle(new ErrorEvent(this, "Ungültige Eingabe"));;
                }catch (NoSuchElementException e){
                    this.errorhandler.handle(new ErrorEvent(this, "an dieser Stelle befindet sich kein Kuchen"));
                }
        }
    }

    public void setAutomatWrapper(AutomatWrapper wrapper) {
        this.automatWrapper = wrapper;
    }

    public void setErrorhandler(ErrorEventHandler<ErrorEvent> errorhandler) {
        this.errorhandler = errorhandler;
    }

    public void setCollectionHandler(CollectionOutputHandler<CollectionOutputEvent> collectionHandler) {
        this.collectionHandler = collectionHandler;
    }
}
