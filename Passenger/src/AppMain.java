/**
 * Einstiegspunkt der Anwendung. Startet die Console-UI.
 */
public class AppMain {
    /**
     * Main-Methode der App.
     *
     * @param args Kommandozeilenargumente (werden hier nicht verwendet)
     */
    public static void main(String[] args) {
        Schedule schedule = DataSeeder.seed();
        new ConsoleUI(schedule).start();
    }
}