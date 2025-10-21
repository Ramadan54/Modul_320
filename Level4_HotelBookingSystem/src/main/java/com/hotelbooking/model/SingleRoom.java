package com.hotelbooking.model;

public class SingleRoom extends Room {
    private static final int NUMBER_OF_BEDS = 1;

    public SingleRoom(int roomNumber, double basePrice) {
        super(roomNumber, basePrice);
    }

    @Override
    public double calculatePrice(int numberOfDays) {
        return getBasePrice() * numberOfDays;
    }

    @Override
    public String getDescription() {
        return "Single Room #" + getRoomNumber() +
                " - " + NUMBER_OF_BEDS + " bed - " +
                getBasePrice() + " CHF/night";
    }
}