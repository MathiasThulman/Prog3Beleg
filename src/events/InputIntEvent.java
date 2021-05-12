package events;

import java.util.Date;
import java.util.EventObject;

public class InputIntEvent extends EventObject {
    private EventType type;
    private int inputInt;
    private Date inputDate;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public InputIntEvent(Object source,EventType type, int inputInt) {
        super(source);
        this.inputInt = inputInt;
        this.type=type;
    }

    //not very pretty?
    public InputIntEvent(Object source,EventType type, int inputInt, Date date) {
        super(source);
        this.inputInt = inputInt;
        this.type=type;
        this.inputDate = date;
    }

    public EventType getType() {
        return type;
    }

    public int getInputInt() {
        return inputInt;
    }

    public Date getInputDate() {
        return inputDate;
    }
}
