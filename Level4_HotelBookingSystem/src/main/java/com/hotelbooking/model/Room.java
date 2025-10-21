package com.hotelbooking.model;

public abstract class Room {
    private int roomNumber;
    private double basePrice;
    private boolean isAvailable;

    public Room(int roomNumber, double basePrice) {
        this.roomNumber = roomNumber;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    // Abstract methods - must be implemented by child classes
    public abstract double calculatePrice(int numberOfDays);

    public abstract String getDescription();

    // Getters and Setters
    public int getRoomNumber() {
        return roomNumber;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}