package automat;

import events.*;
import exceptions.AlreadyExistsException;

import java.util.NoSuchElementException;

public class AutomatInputStringListener implements EventListener<InputStringEvent> {
    private AutomatWrapper automatWrapper;
    private ErrorEventHandler<ErrorEvent> errorHandler;


    @Override
    public void addEvent(InputStringEvent event) {
        switch(event.getType()){
            case addHersteller:
                try {
                    this.automatWrapper.getAutomat().addHersteller(new HerstellerImpl(event.getMessage()));
                } catch (AlreadyExistsException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "es existiert bereits ein Hersteller mit diesem Namen"));
                }
                break;
            case remHersteller:
                try {
                    this.automatWrapper.getAutomat().removeHersteller(event.getMessage());
                } catch (NoSuchElementException e) {
                    this.errorHandler.handle(new ErrorEvent(this, "dieser Hersteller existiert nicht"));
                }
        }
    }


    public void setErrorHandler(ErrorEventHandler<ErrorEvent> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setAutomatWrapper(AutomatWrapper automatWrapper) {
        this.automatWrapper = automatWrapper;
    }
}
