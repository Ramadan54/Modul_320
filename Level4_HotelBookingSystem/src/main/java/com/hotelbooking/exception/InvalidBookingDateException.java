package com.hotelbooking.exception;

import java.time.LocalDate;

public class InvalidBookingDateException extends BookingException {
    private LocalDate checkIn;
    private LocalDate checkOut;

    public InvalidBookingDateException(LocalDate checkIn, LocalDate checkOut) {
        super("Invalid booking dates. Check-in: " + checkIn + ", Check-out: " + checkOut);
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public InvalidBookingDateException(String message) {
        super(message);
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }
}
