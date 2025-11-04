package com.hotelbooking.exception;

public class BookingNotFoundException extends BookingException {
    private int bookingId;

    public BookingNotFoundException(int bookingId) {
        super("Booking with ID #" + bookingId + " not found.");
        this.bookingId = bookingId;
    }
}
