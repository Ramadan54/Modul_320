package com.hotelbooking.model;

public class Parking extends RoomDecorator {
    private static final double PARKING_PRICE = 10.0;

    public Parking(Room room) {
        super(room);
    }

    @Override
    public String getServiceDescription() {
        return "Parking";
    }

    @Override
    public double getPrice() {
        return PARKING_PRICE;
    }
}