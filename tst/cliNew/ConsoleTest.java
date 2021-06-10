package cliNew;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ConsoleTest {
    private final String MODE = "mode";


    @Test
    public void executeCommandInsertValid(){
        try {
            final String Gunther = "Gunther";
            System.setIn( new ByteArrayInputStream(Gunther.getBytes()));

            Console console = new Console();
            InputReader reader = mock(InputReader.class);
            console.setInputReader(reader);
            //create field to put cli into proper mode
            final Field field = console.getClass().getDeclaredField(MODE);
            field.setAccessible(true);
            field.set(console, InputMode.insertMode);
            console.read();

            verify(reader).readInsert(Gunther);
            System.setIn(System.in);
        } catch (NoSuchFieldException | IllegalAccessException e) {
           fail();
        }
    }

    @Test
    public void executeCommandDeleteValid(){
        try {
            System.setIn( new ByteArrayInputStream("2".getBytes()));

            Console console = new Console();
            InputReader reader = mock(InputReader.class);
            console.setInputReader(reader);
            //create field to put cli into proper mode
            final Field field = console.getClass().getDeclaredField(MODE);
            field.setAccessible(true);
            field.set(console, InputMode.deleteMode);
            console.read();

            verify(reader).readDelete("2");
            System.setIn(System.in);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void executeCommandDisplayValid(){
        try {
            System.setIn( new ByteArrayInputStream("hersteller".getBytes()));

            Console console = new Console();
            InputReader reader = mock(InputReader.class);
            console.setInputReader(reader);
            //create field to put cli into proper mode
            final Field field = console.getClass().getDeclaredField(MODE);
            field.setAccessible(true);
            field.set(console, InputMode.displayMode);
            console.read();

            verify(reader).readDisplay("hersteller");
            System.setIn(System.in);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void executeCommandChangeValid(){
        try {
            System.setIn( new ByteArrayInputStream("5".getBytes()));

            Console console = new Console();
            InputReader reader = mock(InputReader.class);
            console.setInputReader(reader);
            //create field to put cli into proper mode
            final Field field = console.getClass().getDeclaredField(MODE);
            field.setAccessible(true);
            field.set(console, InputMode.changeMode);
            console.read();

            verify(reader).readChange("5");
            System.setIn(System.in);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }
}
