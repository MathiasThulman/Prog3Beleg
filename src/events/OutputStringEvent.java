package events;

import java.util.EventObject;

public class OutputStringEvent extends EventObject {
    private String printString;

    public OutputStringEvent(Object source, String printString) {
        super(source);
        this.printString = printString;
    }

    public String getPrintString() {
        return printString;
    }
}
