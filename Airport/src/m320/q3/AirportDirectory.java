package m320.q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class AirportDirectory {
    private final Map<String, Airport> byCode = new HashMap<>();

    public void put(Airport a) {
        if (a == null || a.getCode() == null || a.getCode().isBlank()) return;
        byCode.put(a.getCode().toUpperCase(), a); // Key einmalig → überschreibt
    }

    public int size() { return byCode.size(); }

    public List<Airport> listAll() {
        return byCode.values().stream()
                .sorted(Comparator.comparing(Airport::getCode))
                .toList();
    }

    // --- NEU: suchen/holen/löschen ---
    public Optional<Airport> get(String code) {
        if (code == null) return Optional.empty();
        return Optional.ofNullable(byCode.get(code.toUpperCase()));
    }

    public boolean remove(String code) {
        return byCode.remove(code.toUpperCase()) != null;
    }

    /** Volltextsuche in Name ODER City (case-insensitive). */
    public List<Airport> search(String query) {
        String q = query.toLowerCase();
        return byCode.values().stream()
                .filter(a -> a.getName().toLowerCase().contains(q)
                        || a.getCity().toLowerCase().contains(q))
                .sorted(Comparator.comparing(Airport::getCode))
                .toList();
    }
    // --- ENDE: neue Methoden ---

    public void loadCsv(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String header = br.readLine();
            if (header == null) return;
            var cols = CsvUtils.splitCsvLine(header).stream()
                    .map(s -> s.toLowerCase().trim()).toList();

            int iCode = indexOf(cols, "iata", "code", "iata_code");
            int iName = indexOf(cols, "name", "airport");
            int iCity = indexOf(cols, "city", "municipality");
            int iState= indexOf(cols, "state", "region", "state_code");

            if (iCode  < 0) iCode  = 0;
            if (iName  < 0) iName  = Math.min(1, cols.size()-1);
            if (iCity  < 0) iCity  = Math.min(2, cols.size()-1);
            if (iState < 0) iState = Math.min(3, cols.size()-1);

            String line;
            while ((line = br.readLine()) != null) {
                var f = CsvUtils.splitCsvLine(line);
                if (f.isEmpty()) continue;
                String code  = getField(f, iCode).toUpperCase();
                String name  = getField(f, iName);
                String city  = getField(f, iCity);
                String state = getField(f, iState);
                if (!code.isBlank() && code.length() <= 4) {
                    put(new Airport(code, name, city, state));
                }
            }
        }
    }

    private static String getField(List<String> f, int i) {
        return (i >= 0 && i < f.size()) ? f.get(i) : "";
    }
    private static int indexOf(List<String> cols, String... names) {
        for (int i = 0; i < cols.size(); i++)
            for (String n : names) if (cols.get(i).contains(n)) return i;
        return -1;
    }

    /** CSV an typischen Orten finden. */
    public static Path findCsvAutomatically() {
        Path[] candidates = {
                Path.of("data","airports.csv"),
                Path.of("airports.csv"),
                Path.of("src","data","airports.csv")
        };
        for (Path p : candidates) if (Files.exists(p)) return p;
        return null;
    }
}