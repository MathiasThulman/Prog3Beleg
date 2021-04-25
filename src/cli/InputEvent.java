package cli;

import java.util.EventObject;

public class InputEvent extends EventObject {
    private String message;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public InputEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
