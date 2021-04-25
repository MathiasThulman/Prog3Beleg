package automat;

public class HerstellerImpl implements Hersteller {
    private String name;

    public HerstellerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
