package com.hotelbooking.service;

import com.hotelbooking.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings;
    private int nextBookingId;

    public BookingService() {
        this.bookings = new ArrayList<>();
        this.nextBookingId = 1;
    }

    public Booking createBooking(LocalDate checkIn, LocalDate checkOut, Room room, Customer customer) {
        if (!room.isAvailable()) {
            System.out.println("Error: Room is not available!");
            return null;
        }

        Booking booking = new Booking(nextBookingId++, checkIn, checkOut, room, customer);
        bookings.add(booking);
        room.setAvailable(false);
        customer.addBooking(booking);

        System.out.println("Booking created successfully!");
        return booking;
    }

    public void confirmBooking(int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.confirm();
        } else {
            System.out.println("Booking not found!");
        }
    }

    public void checkInBooking(int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.checkIn();
        } else {
            System.out.println("Booking not found!");
        }
    }

    public void checkOutBooking(int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.checkOut();
        } else {
            System.out.println("Booking not found!");
        }
    }

    public void cancelBooking(int bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking != null) {
            booking.cancel();
            System.out.println("Booking cancelled successfully!");
        } else {
            System.out.println("Booking not found!");
        }
    }

    public Booking findBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public List<Booking> getBookingsByCustomer(Customer customer) {
        List<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomer().getCustomerId() == customer.getCustomerId()) {
                customerBookings.add(booking);
            }
        }
        return customerBookings;
    }
}