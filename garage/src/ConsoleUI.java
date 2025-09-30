import java.util.Scanner;

public class ConsoleUI {
    private final Garage garage;
    private final Scanner in = new Scanner(System.in);

    public ConsoleUI(Garage garage) {
        this.garage = garage;
    }
    public void start() {
        System.out.println("=== Garage V2 (Vererbung, Override, Polymorphismus) ===");
        boolean run = true;
        while (run) {
            menu();
            switch (read("Auswahl")) {
                case "1" -> listAll();
                case "2" -> repair();
                case "3" -> repairWithDiscount();
                case "4" -> listRepairedAndTotals();
                case "0" -> run = false;
                default  -> System.out.println("Ungültig.");
            }
        }
        System.out.println("Tschüss!");
    }
    private void menu() {
        System.out.println("""
                
                1) Fahrzeuge anzeigen
                2) Fahrzeug reparieren
                3) Fahrzeug reparieren (mit Rabatt %)
                4) Reparierte + Gesamtkosten anzeigen
                0) Beenden
                """);
    }
    private void listAll() {
        System.out.println("-- Alle Fahrzeuge --");
        garage.all().forEach(v -> System.out.println("  " + v));
    }
    private void repair() {
        String id = read("Fahrzeug-ID (Prefix, z.B. erste 8 Zeichen)");
        var res = garage.repair(id);
        System.out.println(res.isPresent()
                ? "Repariert. Kosten = " + String.format("%.2f", res.get())
                : "Nicht gefunden.");
    }
    private void repairWithDiscount() {
        String id = read("Fahrzeug-ID (Prefix)");
        double disc = Double.parseDouble(read("Rabatt in % (0-100)"));
        var res = garage.repair(id, disc);
        System.out.println(res.isPresent()
                ? "Repariert mit Rabatt. Kosten = " + String.format("%.2f", res.get())
                : "Nicht gefunden.");
    }
    private void listRepairedAndTotals() {
        System.out.println("-- Reparierte Fahrzeuge --");
        var list = garage.repairedVehicles();
        if (list.isEmpty()) System.out.println("  (keine)");
        else list.forEach(v -> System.out.println("  " + v));

        System.out.println("Gesamtkosten: " + String.format("%.2f", garage.totalRepairCost()));
        System.out.println("Anzahl nach Typ: " + garage.repairedCountByType());
    }
    private String read(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }
}