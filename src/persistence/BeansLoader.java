package persistence;

import automat.Automat;
import automat.HerstellerImpl;
import automat.KuchenVerkaufsObjektImpl;

import java.beans.*;
import java.io.*;

public class BeansLoader {

    public void encodeHersteller(HerstellerImpl object, String fileName) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));) {
            encoder.setPersistenceDelegate(HerstellerImpl.class,new DefaultPersistenceDelegate(new String[] {"name"}));
            encoder.writeObject(object);
        } catch (Exception e) {
        }
    }

    public HerstellerImpl decodeHersteller(String fileName) {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));) {
            return (HerstellerImpl) decoder.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void encodeKuchen(KuchenVerkaufsObjektImpl object, String fileName){
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));) {
            encoder.setPersistenceDelegate(KuchenVerkaufsObjektImpl.class,new DefaultPersistenceDelegate(new String[] {"hersteller", "allergene", "naehrwert", "durationInMS", "stringBigDecimal"}));
            encoder.setPersistenceDelegate(HerstellerImpl.class,new DefaultPersistenceDelegate(new String[] {"name"}));
            encoder.writeObject(object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KuchenVerkaufsObjektImpl decodeKuchen(String fileName){
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));) {
            return (KuchenVerkaufsObjektImpl) decoder.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void encodeAutomat(Automat automat, String fileName){
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));) {
            encoder.setPersistenceDelegate(Automat.class,new DefaultPersistenceDelegate(new String[]{"fachzahl"}));
            encoder.setPersistenceDelegate(KuchenVerkaufsObjektImpl.class,new DefaultPersistenceDelegate(new String[] {"hersteller", "allergene", "naehrwert", "durationInMS", "stringBigDecimal"}));
            encoder.setPersistenceDelegate(HerstellerImpl.class,new DefaultPersistenceDelegate(new String[] {"name"}));
            encoder.writeObject(automat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
