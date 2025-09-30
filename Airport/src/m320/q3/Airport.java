package m320.q3;

public class Airport {
    private final String code;   // IATA, z.B. "JFK"
    private final String name;
    private final String city;
    private final String state;

    public Airport(String code, String name, String city, String state) {
        this.code = code;
        this.name = name;
        this.city = city;
        this.state = state;
    }

    public String getCode()  { return code; }
    public String getName()  { return name; }
    public String getCity()  { return city; }
    public String getState() { return state; }

    @Override public String toString() {
        return "%s - %s (%s, %s)".formatted(code, name, city, state);
    }
}
