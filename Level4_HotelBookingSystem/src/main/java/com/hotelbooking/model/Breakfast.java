package com.hotelbooking.model;

public class Breakfast extends RoomDecorator {
    private static final double BREAKFAST_PRICE = 15.0;

    public Breakfast(Room room) {
        super(room);
    }

    @Override
    public String getServiceDescription() {
        return "Breakfast";
    }

    @Override
    public double getPrice() {
        return BREAKFAST_PRICE;
    }
}