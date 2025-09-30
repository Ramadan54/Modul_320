import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Zeitplan/Flugplan: verwaltet mehrere Flüge (1:n zu {@link Flight}).
 * <p>
 * Enthält Such- und Delegationsmethoden, z. B. Registrierung eines Passagiers
 * für einen bestimmten Flug.
 */
public class Schedule {
    private final List<Flight> flights = new ArrayList<>();

    /**
     * Fügt einen Flug zum Plan hinzu.
     *
     * @param f Flug (nicht {@code null})
     * @throws NullPointerException wenn {@code f} {@code null} ist
     */
    public void addFlight(Flight f) {
        Objects.requireNonNull(f);
        flights.add(f);
    }

    /**
     * Liefert alle Flüge zeitlich aufsteigend sortiert.
     *
     * @return sortierte unveränderliche Liste der Flüge
     */
    public List<Flight> getFlights() {
        return flights.stream()
                .sorted(Comparator.comparing(Flight::getDeparture))
                .toList();
    }

    /**
     * Sucht einen Flug anhand des Codes (case-insensitive).
     *
     * @param code Flugcode
     * @return Optional mit Flug bei Treffer, sonst leer
     */
    public Optional<Flight> findFlightByCode(String code) {
        return flights.stream()
                .filter(f -> f.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    /**
     * Liefert alle Abflüge zu einem Datum, aufsteigend sortiert.
     *
     * @param date Datum
     * @return Liste der Flüge am gegebenen Datum (kann leer sein)
     */
    public List<Flight> listDeparturesOn(LocalDate date) {
        return flights.stream()
                .filter(f -> f.getDeparture().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Flight::getDeparture))
                .toList();
    }

    /**
     * Registriert einen Passagier für einen Flug (Delegation an {@link Flight#addPassenger(Passenger)}).
     *
     * @param flightCode Flugcode
     * @param p          Passagier
     * @return {@code true}, wenn hinzugefügt; {@code false}, wenn Flug nicht existiert oder Duplikat
     */
    public boolean registerPassengerToFlight(String flightCode, Passenger p) {
        return findFlightByCode(flightCode)
                .map(f -> f.addPassenger(p))
                .orElse(false);
    }

    /**
     * Entfernt einen Passagier (per ID) von einem bestimmten Flug.
     *
     * @param flightCode  Flugcode
     * @param passengerId ID des Passagiers
     * @return {@code true}, wenn der Passagier entfernt wurde; sonst {@code false}
     */
    public boolean removePassengerFromFlight(String flightCode, String passengerId) {
        return findFlightByCode(flightCode)
                .map(f -> f.removePassengerById(passengerId))
                .orElse(false);
    }

    /**
     * Sucht über alle Flüge nach Passagieren anhand eines Namensteilstrings.
     *
     * @param query Suchbegriff (case-insensitive)
     * @return Map von Flug → Trefferliste (leere Listen bei keinen Treffern möglich)
     */
    public Map<Flight, List<Passenger>> searchPassengersEverywhere(String query) {
        String q = query.toLowerCase();
        return flights.stream().collect(Collectors.toMap(
                f -> f,
                f -> f.getPassengers().stream()
                        .filter(p -> p.getFullName().toLowerCase().contains(q))
                        .toList()
        ));
    }
}