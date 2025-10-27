package com.hotelbooking.exception;

public class RoomNotAvailableException extends BookingException {
    private int roomNumber;

    public RoomNotAvailableException(int roomNumber) {
        super("Room #" + roomNumber + " is not available for booking.");
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}