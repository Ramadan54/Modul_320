import java.util.Objects;
import java.util.UUID;

/**
 * Repräsentiert einen Passagier.
 * <p>
 * Die Klasse ist unveränderlich (immutable). Identität basiert auf {@code id}.
 * {@link #equals(Object)} und {@link #hashCode()} verwenden ausschließlich die {@code id}.
 */
public final class Passenger {
    /** Eindeutige technische ID (z. B. UUID). */
    private final String id;
    /** Vorname des Passagiers. */
    private final String firstName;
    /** Nachname des Passagiers. */
    private final String lastName;

    /**
     * Konstruktor, der automatisch eine neue zufällige ID vergibt.
     *
     * @param firstName Vorname
     * @param lastName  Nachname
     */
    public Passenger(String firstName, String lastName) {
        this(UUID.randomUUID().toString(), firstName, lastName);
    }

    /**
     * Vollständiger Konstruktor.
     *
     * @param id        eindeutige ID (darf nicht {@code null} sein)
     * @param firstName Vorname (nicht {@code null})
     * @param lastName  Nachname (nicht {@code null})
     * @throws NullPointerException wenn ein Parameter {@code null} ist
     */
    public Passenger(String id, String firstName, String lastName) {
        this.id = Objects.requireNonNull(id);
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
    }

    /** @return die eindeutige ID des Passagiers */
    public String getId()        { return id; }
    /** @return der Vorname */
    public String getFirstName() { return firstName; }
    /** @return der Nachname */
    public String getLastName()  { return lastName; }

    /**
     * @return Vor- und Nachname zusammengefügt (z. B. {@code "Max Muster"})
     */
    public String getFullName()  { return firstName + " " + lastName; }

    /**
     * Gleichheit basiert ausschließlich auf der {@code id}.
     */
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger p)) return false;
        return id.equals(p.id);
    }

    /** Hash-Code basiert ausschließlich auf der {@code id}. */
    @Override public int hashCode() { return id.hashCode(); }

    /**
     * Kurze Darstellung: {@code "<FullName> (<erste 8 Zeichen der ID>)"}.
     */
    @Override public String toString() {
        return "%s (%s)".formatted(getFullName(), id.substring(0, 8));
    }
}