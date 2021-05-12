package events;

public interface EventListener<T extends Event> {

    void addEvent(T event);
}
