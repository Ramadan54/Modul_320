package com.hotelbooking.model;

public class Suite extends Room {
    private int numberOfRooms;
    private boolean hasJacuzzi;

    public Suite(int roomNumber, double basePrice, int numberOfRooms, boolean hasJacuzzi) {
        super(roomNumber, basePrice);
        this.numberOfRooms = numberOfRooms;
        this.hasJacuzzi = hasJacuzzi;
    }

    @Override
    public double calculatePrice(int numberOfDays) {
        double price = getBasePrice() * numberOfDays;
        price += (numberOfRooms - 1) * 50 * numberOfDays; // Extra rooms
        if (hasJacuzzi) {
            price += 100 * numberOfDays; // Jacuzzi surcharge
        }
        return price;
    }

    @Override
    public String getDescription() {
        String jacuzzi = hasJacuzzi ? "with jacuzzi" : "without jacuzzi";
        return "Suite #" + getRoomNumber() +
                " - " + numberOfRooms + " rooms - " + jacuzzi + " - " +
                getBasePrice() + " CHF/night";
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public boolean hasJacuzzi() {
        return hasJacuzzi;
    }
}