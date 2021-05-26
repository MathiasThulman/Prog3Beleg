package gui;

import automat.Automat;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MainWindowControllerTest {

    @Test
    void onPressAddHersteller() {
        Automat automat = mock(Automat.class);
        ActionEvent e=new ActionEvent(null,null);
        MainWindowController controller=new MainWindowController();

        controller.onPressAddHersteller();

        //verify(automat.addHersteller("");
    }

    @Test
    void onPressRemoveHersteller() {
        Automat automat = mock(Automat.class);
        ActionEvent e=new ActionEvent(null,null);
        MainWindowController controller=new MainWindowController();

        //TODO how do i enter text in a test?
        controller.onPressRemoveHersteller();
        verify(automat).removeHersteller("hersteller");
    }

    @Test
    void onPressSetInspection() {
        Automat automat = mock(Automat.class);
        ActionEvent e=new ActionEvent(null,null);
        MainWindowController controller=new MainWindowController();

        //TODO how do i enter text in a test?
        controller.onPressRemoveHersteller();
        //verify(automat).setInspectionDate(Date);
    }

    @Test
    void onPressAddKuchen() {
    }

    @Test
    void onPressRemoveKuchen() {
    }
}