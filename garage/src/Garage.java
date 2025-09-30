import java.util.*;
import java.util.stream.Collectors;

public class Garage {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public void add(Vehicle v) { vehicles.add(Objects.requireNonNull(v)); }

    public List<Vehicle> all() { return List.copyOf(vehicles); }

    public Optional<Vehicle> findByIdPrefix(String idPrefix) {
        String pref = idPrefix.toLowerCase();
        return vehicles.stream()
                .filter(v -> v.getId().toLowerCase().startsWith(pref))
                .findFirst();
    }
    public Optional<Double> repair(String idPrefix) {
        return findByIdPrefix(idPrefix).map(Vehicle::repair);
    }
    public Optional<Double> repair(String idPrefix, double discountPercent) {
        return findByIdPrefix(idPrefix).map(v -> v.repair(discountPercent));
    }
    public List<Vehicle> repairedVehicles() {
        return vehicles.stream().filter(Vehicle::isRepaired).toList();
    }
    public double totalRepairCost() {
        return vehicles.stream().filter(Vehicle::isRepaired)
                .mapToDouble(Vehicle::getLastRepairCost).sum();
    }
    public Map<String, Long> repairedCountByType() {
        return vehicles.stream().filter(Vehicle::isRepaired)
                .collect(Collectors.groupingBy(Vehicle::getType, Collectors.counting()));
    }
}