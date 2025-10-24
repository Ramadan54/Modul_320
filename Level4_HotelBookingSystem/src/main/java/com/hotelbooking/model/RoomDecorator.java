package com.hotelbooking.model;

public abstract class RoomDecorator extends Room implements AdditionalService {
    protected Room decoratedRoom;

    public RoomDecorator(Room room) {
        super(room.getRoomNumber(), room.getBasePrice());
        this.decoratedRoom = room;
    }

    @Override
    public double calculatePrice(int numberOfDays) {
        return decoratedRoom.calculatePrice(numberOfDays) + (getPrice() * numberOfDays);
    }

    @Override
    public String getDescription() {
        return decoratedRoom.getDescription() + " + " + this.getServiceDescription();
    }

    // Abstract method for service description
    public abstract String getServiceDescription();
}