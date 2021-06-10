import automat.Allergen;
import automat.Automat;
import automat.Hersteller;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import kuchen.ObstkuchenImpl;
import kuchen.ObsttorteImpl;
import util.AutomatSerializer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

public class SerializerMain {

    public static void main(String[] args) {
        Automat auto = new Automat(20);
        AutomatSerializer serializer = new AutomatSerializer();

        HerstellerImpl Frank = new HerstellerImpl("Frank");
        HerstellerImpl Pups = new HerstellerImpl("Pups");
        try {
            auto.addHersteller(Frank);
            auto.addHersteller(Pups);
            auto.addKuchen(new ObstkuchenImpl(Frank, new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Sesamsamen)), 600, Duration.ofDays(5), new BigDecimal(200), "Tomate"));
            auto.addKuchen(new ObsttorteImpl(Pups, new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss)), 900, Duration.ofDays(2), new BigDecimal(500), "Tomate", "Mayo"));
            System.out.println(auto + auto.checkKuchen().toString() + auto.checkHersteller().toString());

            serializer.serialize("benis", auto);
            Automat loadedAutomat = serializer.deserialize("benis");

            System.out.println(loadedAutomat + loadedAutomat.checkKuchen().toString() + loadedAutomat.checkHersteller().toString());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            e.printStackTrace();
        }





    }
}
