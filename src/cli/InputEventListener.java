package cli;

import java.util.EventListener;

public interface InputEventListener extends EventListener {
    void addEvent(InputEvent event);
}

