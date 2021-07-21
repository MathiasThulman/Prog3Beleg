package persistence;

import automat.Automat;

import java.io.*;

public class JoSSerializer {

    public void serialize(String filename, Automat item){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            serialize(oos, item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(ObjectOutput objectOutput, Automat item) throws IOException {
        objectOutput.writeObject(item);
    }

    public Automat deserialize(String filename){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return deserialize(ois);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Automat deserialize(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return (Automat) objectInput.readObject();
    }
}
