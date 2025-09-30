public class Truck extends Vehicle {
    public Truck(String brand, int mileageKm) {
        super(brand, mileageKm);
    }
    @Override
    protected double calculateRepairCost() {
        // Beispiel-Formel: Basis 400 + 0.08 pro km
        return 400.0 + 0.08 * getMileageKm();
    }
}