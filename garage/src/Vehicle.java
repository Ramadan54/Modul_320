import java.util.Objects;
import java.util.UUID;

public abstract class Vehicle {
    private final String id;
    private final String brand;
    private final int mileageKm;

    private boolean repaired = false;
    private double lastRepairCost = 0.0;

    protected Vehicle(String brand, int mileageKm) {
        this.id = UUID.randomUUID().toString();
        this.brand = Objects.requireNonNull(brand);
        this.mileageKm = mileageKm;
    }
    public String getId() { return id; }
    protected String getBrand() { return brand; }
    protected int getMileageKm() { return mileageKm; }

    public boolean isRepaired() { return repaired; }
    public double getLastRepairCost() { return lastRepairCost; }

    public final double repair() {
        double cost = calculateRepairCost();
        this.repaired = true;
        this.lastRepairCost = cost;
        return cost;
    }

    public final double repair(double discountPercent) {
        double base = repair(); // markiert auch als repariert
        double factor = Math.max(0, Math.min(100, 100 - discountPercent)) / 100.0;
        this.lastRepairCost = base * factor;
        return this.lastRepairCost;
    }

    protected abstract double calculateRepairCost();

    public String getType() { return getClass().getSimpleName(); }

    @Override public String toString() {
        String shortId = id.substring(0, 8);
        return "%s %s (%dkm) id=%s%s".formatted(
                getType(), brand, mileageKm, shortId,
                repaired ? " repaired=Yes cost=" + String.format("%.2f", lastRepairCost) : " repaired=No"
        );
    }
}