package util;

import automat.Automat;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JoSSerializerTest {

    @Test
    void serializeValid(){
        ObjectOutput oos = mock(ObjectOutput.class);
        Automat automat = new Automat(10);
        JoSSerializer serializer = new JoSSerializer();

        try {
            serializer.serialize(oos, automat);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        try {
            verify(oos).writeObject(any());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deserializeValid(){
        ObjectInput ooi = mock(ObjectInput.class);
        JoSSerializer serializer = new JoSSerializer();
        try {
            Automat automat = serializer.deserialize(ooi);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }

        try {
            verify(ooi).readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            fail();
        }

    }

}
