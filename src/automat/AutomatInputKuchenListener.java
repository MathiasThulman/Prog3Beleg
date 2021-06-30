package automat;

import events.ErrorEvent;
import events.ErrorEventHandler;
import events.EventListener;
import events.InputKuchenEvent;
import exceptions.FullAutomatException;

import java.util.NoSuchElementException;

public class AutomatInputKuchenListener implements EventListener<InputKuchenEvent> {
    private AutomatWrapper automatWrapper;
    private ErrorEventHandler<ErrorEvent> errorHandler;

    @Override
    public void addEvent(InputKuchenEvent event) {
        try {
            this.automatWrapper.getAutomat().addKuchen(event.getKuchenObjekt());
        } catch (FullAutomatException e) {
            this.errorHandler.handle(new ErrorEvent(this, "der Automat ist voll"));
        } catch (NoSuchElementException e){
            this.errorHandler.handle(new ErrorEvent(this, "Es wurde kein Hersteller zu diesem Kuchen gefuden"));
        }
    }

    public void setAutomatWrapper(AutomatWrapper wrapper) {
        this.automatWrapper = wrapper;
    }

    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }
}
