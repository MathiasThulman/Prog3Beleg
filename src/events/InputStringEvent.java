package events;


public class InputStringEvent extends Event {
    private final String message;


    public InputStringEvent(Object source, EventType type, String message) {
        super(source, type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
