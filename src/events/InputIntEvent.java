package events;

import java.util.Date;
import java.util.EventObject;

public class InputIntEvent extends Event {
    private final int inputInt;
    private Date inputDate;

    public InputIntEvent(Object source,EventType type, int inputInt) {
        super(source, type);
        this.inputInt = inputInt;
    }

    //not very pretty?
    public InputIntEvent(Object source,EventType type, int inputInt, Date date) {
        super(source, type);
        this.inputInt = inputInt;
        this.inputDate = date;
    }

    public int getInputInt() {
        return inputInt;
    }

    public Date getInputDate() {
        return inputDate;
    }
}
