package m320.q3;

import java.nio.file.Path;

public class AppMain {
    public static void main(String[] args) {
        try {
            AirportDirectory dir = new AirportDirectory();

            Path csv = AirportDirectory.findCsvAutomatically();
            if (csv != null) {
                dir.loadCsv(csv);
                System.out.println("Geladen: " + csv.toAbsolutePath());
                System.out.println("Anzahl:  " + dir.size());
            } else {
                System.out.println("Hinweis: Keine CSV gefunden (erwartet data/airports.csv).");
            }

            // Interaktive Funktionen (add/search/show/delete)
            new ConsoleUI(dir).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}