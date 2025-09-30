public class AppMain {
    public static void main(String[] args) {
        Garage garage = DataSeeder.seed();
        new ConsoleUI(garage).start();
    }
}
