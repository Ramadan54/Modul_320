package m320.q3;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final AirportDirectory dir;
    private final Scanner in = new Scanner(System.in);

    public ConsoleUI(AirportDirectory dir) { this.dir = dir; }

    public void start() {
        boolean run = true;
        while (run) {
            System.out.println("""
                    
                    1) Alle Airports anzeigen
                    2) Airport per Code finden (z.B. JFK)
                    3) Suchen (Name/City)
                    4) Airport hinzufügen/aktualisieren
                    5) Airport löschen
                    0) Ende
                    """);
            switch (read("Auswahl")) {
                case "1" -> showAll();
                case "2" -> findByCode();
                case "3" -> search();
                case "4" -> addOrUpdate();
                case "5" -> remove();
                case "0" -> run = false;
                default  -> System.out.println("Ungültig.");
            }
        }
    }

    private void showAll() {
        List<Airport> list = dir.listAll();
        if (list.isEmpty()) System.out.println("(leer)");
        else list.forEach(a -> System.out.println("  " + a));
        System.out.println("Gesamt: " + dir.size());
    }

    private void findByCode() {
        String code = read("Code (IATA)");
        System.out.println(dir.get(code).map(Object::toString).orElse("Nicht gefunden"));
    }

    private void search() {
        String q = read("Suche (Teil von Name/City)");
        var res = dir.search(q);
        if (res.isEmpty()) System.out.println("(keine Treffer)");
        else res.forEach(a -> System.out.println("  " + a));
    }

    private void addOrUpdate() {
        String code = read("Code (IATA)").toUpperCase();
        String name = read("Name");
        String city = read("City");
        String state= read("State");
        dir.put(new Airport(code, name, city, state));
        System.out.println("Gespeichert: " + code);
    }

    private void remove() {
        String code = read("Code (IATA)");
        System.out.println(dir.remove(code) ? "Gelöscht." : "Nicht gefunden.");
    }

    private String read(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }
}
