package automat;

import events.*;
import exceptions.AlreadyExistsException;

import java.util.NoSuchElementException;

public class AutomatInputStringListener implements EventListener<InputStringEvent> {
    private Automat automat;
    private ErrorEventHandler<ErrorEvent> errorHandler;


    @Override
    public void addEvent(InputStringEvent event) {
        switch(event.getType()){
            case addHersteller:
                try {
                    this.automat.addHersteller(new HerstellerImpl(event.getMessage()));
                } catch (AlreadyExistsException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "es existiert bereits ein Hersteller mit diesem Namen"));
                }
                break;
            case remHersteller:
                try {
                    this.automat.removeHersteller(event.getMessage());
                } catch (NoSuchElementException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "dieser Hersteller existiert nicht"));
                }
        }
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
    }

    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }
}
