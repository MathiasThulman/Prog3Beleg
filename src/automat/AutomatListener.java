package automat;

import cli.InputEvent;
import cli.InputEventListener;

public class AutomatListener implements InputEventListener {
    private AutomatImpl automat;

    @Override
    public void addEvent(InputEvent event) {
    }

    public void setAutomat(AutomatImpl automat) {
        this.automat = automat;
    }
}
