package m320.q3;

import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {
    private CsvUtils() {}

    /** Trennt eine CSV-Zeile (unterstützt "..." und Kommas darin). */
    public static List<String> splitCsvLine(String line) {
        List<String> out = new ArrayList<>();
        if (line == null) return out;
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') { cur.append('"'); i++; }
                else inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                out.add(cur.toString().trim()); cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        out.add(cur.toString().trim());
        return out;
    }
}
