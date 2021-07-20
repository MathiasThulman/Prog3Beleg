import automat.Allergen;
import automat.Automat;
import automat.HerstellerImpl;
import exceptions.AlreadyExistsException;
import exceptions.EmptyListException;
import exceptions.FullAutomatException;
import automat.Kremsorte;
import automat.ObstkuchenImpl;
import automat.Obstsorte;
import automat.ObsttorteImpl;
import util.JoSSerializer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

public class JoSMain {

    public static void main(String[] args) {
        Automat auto = new Automat(20);
        JoSSerializer serializer = new JoSSerializer();

        HerstellerImpl Frank = new HerstellerImpl("Frank");
        HerstellerImpl Faustulus = new HerstellerImpl("Faustulus");
        try {
            auto.addHersteller(Frank);
            auto.addHersteller(Faustulus);
            auto.addKuchen(new ObstkuchenImpl(Frank, new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Sesamsamen)), 600, Duration.ofDays(5), new BigDecimal(200)));
            auto.addKuchen(new ObsttorteImpl(Faustulus, new HashSet<>(Arrays.asList(Allergen.Gluten, Allergen.Erdnuss)), 900, Duration.ofDays(2), new BigDecimal(500)));
            System.out.println(auto + auto.checkKuchen().toString() + auto.checkHersteller().toString());

            serializer.serialize("JoSMainFile", auto);
            Automat loadedAutomat = serializer.deserialize("JoSMainFile");

            System.out.println(loadedAutomat + loadedAutomat.checkKuchen().toString() + loadedAutomat.checkHersteller().toString());
        } catch (AlreadyExistsException | FullAutomatException | EmptyListException e) {
            e.printStackTrace();
        }





    }
}
