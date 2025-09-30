import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Repräsentiert einen konkreten Flug mit Passagierliste.
 * <p>
 * 1:n-Beziehung: Ein {@code Flight} HAT viele {@link Passenger}.
 */
public class Flight {
    /** Flugcode, z. B. "LX100". */
    private final String code;
    /** Abflughafen (IATA), z. B. "ZRH". */
    private final String origin;
    /** Zielflughafen (IATA), z. B. "LHR". */
    private final String destination;
    /** Abflugzeitpunkt. */
    private final LocalDateTime departure;
    /** Interne Liste der Passagiere. */
    private final List<Passenger> passengers = new ArrayList<>();

    /**
     * Erstellt einen Flug.
     *
     * @param code       Flugcode (nicht {@code null})
     * @param origin     Abflugort/IATA (nicht {@code null})
     * @param destination Ziel/IATA (nicht {@code null})
     * @param departure  Abflugzeitpunkt (nicht {@code null})
     * @throws NullPointerException wenn ein Parameter {@code null} ist
     */
    public Flight(String code, String origin, String destination, LocalDateTime departure) {
        this.code = Objects.requireNonNull(code);
        this.origin = Objects.requireNonNull(origin);
        this.destination = Objects.requireNonNull(destination);
        this.departure = Objects.requireNonNull(departure);
    }

    /** @return Flugcode (z. B. {@code "LX100"}) */
    public String getCode()              { return code; }
    /** @return Abflughafen (IATA) */
    public String getOrigin()            { return origin; }
    /** @return Zielflughafen (IATA) */
    public String getDestination()       { return destination; }
    /** @return Abflugzeitpunkt */
    public LocalDateTime getDeparture()  { return departure; }

    /**
     * Liefert eine unveränderliche Sicht auf die Passagierliste.
     *
     * @return unmodifizierbare Liste der Passagiere
     */
    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    /**
     * Fügt einen Passagier hinzu, sofern noch nicht vorhanden
     * (Duplikate werden über {@link Passenger#equals(Object)} verhindert).
     *
     * @param p Passagier (nicht {@code null})
     * @return {@code true}, wenn hinzugefügt; {@code false}, wenn bereits vorhanden
     * @throws NullPointerException wenn {@code p} {@code null} ist
     */
    public boolean addPassenger(Passenger p) {
        Objects.requireNonNull(p);
        if (passengers.contains(p)) return false;
        return passengers.add(p);
    }

    /**
     * Entfernt einen Passagier anhand seiner ID.
     *
     * @param passengerId ID des Passagiers
     * @return {@code true}, wenn mindestens ein Eintrag entfernt wurde
     */
    public boolean removePassengerById(String passengerId) {
        return passengers.removeIf(p -> p.getId().equals(passengerId));
    }

    /**
     * Sucht einen Passagier anhand der ID.
     *
     * @param passengerId ID des Passagiers
     * @return Optional mit Passagier bei Treffer, sonst leer
     */
    public Optional<Passenger> findPassengerById(String passengerId) {
        return passengers.stream().filter(p -> p.getId().equals(passengerId)).findFirst();
    }

    /**
     * Sucht Passagiere per Namens-Teilstring (case-insensitive).
     *
     * @param query Suchbegriff (Teil von Vor-/Nachname)
     * @return Liste aller Treffer (kann leer sein)
     */
    public List<Passenger> searchPassengersByName(String query) {
        String q = query.toLowerCase();
        return passengers.stream()
                .filter(p -> p.getFullName().toLowerCase().contains(q))
                .toList();
    }

    /**
     * Textdarstellung, z. B.: {@code "LX100 ZRH→LHR 2025-10-01 09:05 (PAX 3)"}.
     */
    @Override public String toString() {
        String ts = departure.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return "%s %s→%s %s (PAX %d)".formatted(code, origin, destination, ts, passengers.size());
    }
}