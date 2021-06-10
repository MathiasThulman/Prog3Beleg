import automat.HerstellerImpl;
import util.BeansLoader;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class BeansMain {
    public static void main(String[] args) {
        HerstellerImpl Frank = new HerstellerImpl("Frank");
        HerstellerImpl Guide = new HerstellerImpl("Guide");
        HerstellerImpl Faustulus = new HerstellerImpl("Faustulus");
        LinkedList<HerstellerImpl> list = new LinkedList<>(Arrays.asList(Frank, Guide, Faustulus));
        System.out.println(list + list.toString());

        BeansLoader loader = new BeansLoader();

        loader.encode(list, "que");
        LinkedList<HerstellerImpl> loadedList= loader.decode("que");
        System.out.println(loadedList + loadedList.toString());
    }
}
