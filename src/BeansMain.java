import automat.*;
import exceptions.AlreadyExistsException;
import exceptions.FullAutomatException;
import util.BeansLoader;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

public class BeansMain {

    public static void main(String[] args) {

//        saving/loading hersteller
        HerstellerImpl Frank = new HerstellerImpl("Frank");
        System.out.println(Frank + Frank.getName());

        BeansLoader loader = new BeansLoader();
        loader.encodeHersteller(Frank, "beansFile1");
        HerstellerImpl loadedHerst = loader.decodeHersteller("beansFile1");

        System.out.println(loadedHerst + loadedHerst.getName());

        KuchenVerkaufsObjektImpl kuchen = new KuchenVerkaufsObjektImpl(new HerstellerImpl("Frank"), new HashSet<>(Collections.singletonList(Allergen.Gluten)), 1000, Duration.ofDays(3), new BigDecimal(200));
//        System.out.println(kuchen + kuchen.getHersteller().getName()); this does not work  because beans is very reasonable
        loader.encodeKuchen(kuchen, "beansFile2");

        KuchenVerkaufsObjektImpl loadedKuchen = loader.decodeKuchen("beansFile2");
//        System.out.println(loadedKuchen + loadedKuchen.getHersteller().getName());
    }
}
