public abstract class Shape {
    protected final String name;

    protected Shape(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public abstract double area();
    public abstract double perimeter();

    @Override
    public String toString() {
        return name + " A=" + String.format("%.2f", area())
                + " P=" + String.format("%.2f", perimeter());
    }
}
