package events;

import java.util.EventObject;

public class InputEvent extends EventObject {
    private String message;

    public String getMessage() {
        return message;
    }

    public InputEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
