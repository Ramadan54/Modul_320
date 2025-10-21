package com.hotelbooking.model;

public class DoubleRoom extends Room {
    private boolean hasBalcony;

    public DoubleRoom(int roomNumber, double basePrice, boolean hasBalcony) {
        super(roomNumber, basePrice);
        this.hasBalcony = hasBalcony;
    }

    @Override
    public double calculatePrice(int numberOfDays) {
        double price = getBasePrice() * numberOfDays;
        if (hasBalcony) {
            price += 20 * numberOfDays; // Balcony surcharge
        }
        return price;
    }

    @Override
    public String getDescription() {
        String balcony = hasBalcony ? "with balcony" : "without balcony";
        return "Double Room #" + getRoomNumber() +
                " - 2 beds - " + balcony + " - " +
                getBasePrice() + " CHF/night";
    }

    public boolean hasBalcony() {
        return hasBalcony;
    }
}