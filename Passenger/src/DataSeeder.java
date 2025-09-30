import java.time.LocalDateTime;

/**
 * Erzeugt Beispiel-/Seed-Daten für die App.
 */
public final class DataSeeder {
    private DataSeeder() {}

    /**
     * Erstellt einen {@link Schedule} mit drei Flügen und einigen Passagieren.
     *
     * @return initialisierter Flugplan
     */
    public static Schedule seed() {
        Schedule schedule = new Schedule();

        Flight f1 = new Flight("LX100", "ZRH", "LHR", LocalDateTime.of(2025, 10, 1, 9, 5));
        Flight f2 = new Flight("LX200", "ZRH", "BER", LocalDateTime.of(2025, 10, 1, 13, 15));
        Flight f3 = new Flight("LX300", "ZRH", "MAD", LocalDateTime.of(2025, 10, 2, 7, 30));

        f1.addPassenger(new Passenger("Alice", "Meyer"));
        f1.addPassenger(new Passenger("Bob", "Keller"));
        f2.addPassenger(new Passenger("Carla", "Nguyen"));

        schedule.addFlight(f1);
        schedule.addFlight(f2);
        schedule.addFlight(f3);
        return schedule;
    }
}