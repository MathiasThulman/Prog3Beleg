package gui;

import automat.Automat;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MainWindowControllerTest {

    @Test
    void onPressAddHersteller() {
        Automat automat = mock(Automat.class);
        ActionEvent e=new ActionEvent(null,null);
        MainWindowController controller=new MainWindowController();

        try {
            Field automatField;
            automatField = controller.getClass().getDeclaredField("automat");
            automatField.setAccessible(true);
            automatField.set(controller, automat);
            controller.onPressAddHersteller();

            verify(automat).addHersteller(any(HerstellerImpl.class));
        } catch (NoSuchFieldException | IllegalAccessException | AlreadyExistsException noSuchFieldException) {
            fail();
        }
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