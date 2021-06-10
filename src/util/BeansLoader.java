package util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class BeansLoader {

    public void encode(Object object, String fileName) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));) {
            encoder.writeObject(object);
        } catch (Exception e) {

        }
    }

    public LinkedList decode(String fileName) {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));) {
            return new LinkedList (Collections.singletonList(decoder.readObject()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
