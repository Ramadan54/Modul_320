public final class DataSeeder {
    private DataSeeder() {}

    public static Garage seed() {
        Garage g = new Garage();
        g.add(new Car("VW Golf", 120_000));
        g.add(new Car("BMW 3er", 80_000));
        g.add(new Truck("Mercedes Actros", 550_000));
        return g;
    }
}