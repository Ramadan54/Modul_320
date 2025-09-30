public class Car extends Vehicle {
    public Car(String brand, int mileageKm) {
        super(brand, mileageKm);
    }
    @Override
    protected double calculateRepairCost() {

        return 200.0 + 0.05 * getMileageKm();
    }
}
