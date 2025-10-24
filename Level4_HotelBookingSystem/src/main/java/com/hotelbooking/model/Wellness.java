package com.hotelbooking.model;

public class Wellness extends RoomDecorator {
    private static final double WELLNESS_PRICE = 25.0;

    public Wellness(Room room) {
        super(room);
    }

    @Override
    public String getServiceDescription() {
        return "Wellness Access";
    }

    @Override
    public double getPrice() {
        return WELLNESS_PRICE;
    }
}