import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Einfache Konsolenoberfläche (UI) für die Flight-App.
 * <p>
 * Kapselt alle Benutzerinteraktionen (Eingabe/Ausgabe). Die Business-Logik
 * liegt in {@link Schedule} und {@link Flight}.
 */
public class ConsoleUI {
    private final Schedule schedule;
    private final Scanner in = new Scanner(System.in);

    /**
     * @param schedule gemeinsam genutzter Flugplan
     */
    public ConsoleUI(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Startet die UI-Hauptschleife (Menü).
     */
    public void start() {
        println("=== Flight App (HAS + Delegation) ===");
        boolean running = true;
        while (running) {
            showMenu();
            switch (readLine("Auswahl")) {
                case "1" -> listFlights();
                case "2" -> showPassengersOfFlight();
                case "3" -> registerPassenger();
                case "4" -> removePassenger();
                case "5" -> searchPassengerByName();
                case "6" -> searchFlightsByDate();
                case "0" -> running = false;
                default  -> println("Ungültige Auswahl.");
            }
        }
        println("Tschüss!");
    }

    /** Zeigt das Hauptmenü. */
    private void showMenu() {
        println("""
                
                1) Alle Flüge anzeigen
                2) Passagiere eines Flugs anzeigen
                3) Passagier für Flug registrieren
                4) Passagier von Flug entfernen
                5) Passagiere suchen (Name, global)
                6) Flüge eines Datums anzeigen
                0) Beenden
                """);
    }

    /** Listet alle Flüge auf. */
    private void listFlights() {
        println("-- Flüge --");
        schedule.getFlights().forEach(f -> println("  " + f));
    }

    /** Zeigt die Passagiere eines bestimmten Flugs an. */
    private void showPassengersOfFlight() {
        String code = readLine("Flugcode (z.B. LX123)");
        schedule.findFlightByCode(code).ifPresentOrElse(f -> {
            println("Passagiere für " + f.getCode() + ":");
            if (f.getPassengers().isEmpty()) {
                println("  (keine)");
            } else {
                f.getPassengers().forEach(p -> println("  - " + p));
            }
        }, () -> println("Flug nicht gefunden."));
    }

    /** Registriert einen neuen Passagier für einen Flug. */
    private void registerPassenger() {
        String code = readLine("Flugcode");
        String first = readLine("Vorname");
        String last  = readLine("Nachname");
        Passenger p = new Passenger(first, last);
        boolean ok = schedule.registerPassengerToFlight(code, p);
        println(ok ? "Registriert: " + p : "Fehlgeschlagen (Flug existiert nicht oder Duplikat).");
    }

    /** Entfernt einen Passagier (per ID oder ID-Präfix) von einem Flug. */
    private void removePassenger() {
        String code = readLine("Flugcode");
        String pid  = readLine("Passenger-ID (die kurze ID in Klammern)");
        String shortOrFull = pid.trim();
        boolean success = schedule.findFlightByCode(code).map(f -> {
            boolean exact = f.removePassengerById(shortOrFull);
            if (exact) return true;
            return f.getPassengers().stream()
                    .filter(p -> p.getId().startsWith(shortOrFull))
                    .findFirst()
                    .map(p -> f.removePassengerById(p.getId()))
                    .orElse(false);
        }).orElse(false);
        println(success ? "Passagier entfernt." : "Nicht gefunden.");
    }

    /** Globale Suche nach Passagieren per Namens-Teilstring. */
    private void searchPassengerByName() {
        String q = readLine("Name enthält");
        Map<Flight, List<Passenger>> hits = schedule.searchPassengersEverywhere(q);
        println("-- Treffer --");
        long total = 0;
        for (var e : hits.entrySet()) {
            if (!e.getValue().isEmpty()) {
                println(e.getKey().toString());
                for (Passenger p : e.getValue()) {
                    println("  - " + p);
                    total++;
                }
            }
        }
        if (total == 0) println("(keine)");
    }

    /** Listet alle Flüge eines angegebenen Datums auf. */
    private void searchFlightsByDate() {
        String s = readLine("Datum [YYYY-MM-DD]");
        try {
            var date = LocalDate.parse(s);
            var list = schedule.listDeparturesOn(date);
            if (list.isEmpty()) println("Keine Flüge an diesem Datum.");
            else list.forEach(f -> println("  " + f));
        } catch (Exception e) {
            println("Ungültiges Datum.");
        }
    }

    /**
     * Liest eine Zeile aus der Konsole ein.
     *
     * @param label Prompt-Text
     * @return eingegebene Zeile (ohne Zeilenumbruch)
     */
    private String readLine(String label) {
        System.out.print(label + ": ");
        return in.nextLine();
    }

    /** Wrapper um {@link System#out#println(String)}. */
    private void println(String s) { System.out.println(s); }
}