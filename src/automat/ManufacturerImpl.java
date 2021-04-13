package automat;

public class ManufacturerImpl implements Manufacturer {
    private String name;

    public ManufacturerImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
