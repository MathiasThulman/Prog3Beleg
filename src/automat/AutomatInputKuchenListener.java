package automat;

import events.ErrorEvent;
import events.ErrorEventHandler;
import events.EventListener;
import events.InputKuchenEvent;
import exceptions.FullAutomatException;

public class AutomatInputKuchenListener implements EventListener<InputKuchenEvent> {
    Automat automat;
    ErrorEventHandler<ErrorEvent> errorHandler;

    @Override
    public void addEvent(InputKuchenEvent event) {
        try {
            this.automat.addKuchen(event.getKuchenObjekt());
        } catch (FullAutomatException e) {
            this.errorHandler.handle(new ErrorEvent(this, "der Automat ist voll"));
        }
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }
}
