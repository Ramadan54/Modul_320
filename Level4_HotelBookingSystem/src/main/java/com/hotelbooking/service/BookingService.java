package com.hotelbooking.service;

import com.hotelbooking.model.*;
import com.hotelbooking.exception.*;
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

    public Booking createBooking(LocalDate checkIn, LocalDate checkOut, Room room, Customer customer)
            throws RoomNotAvailableException, InvalidBookingDateException {

        // Validate dates
        if (checkIn == null || checkOut == null) {
            throw new InvalidBookingDateException("Check-in and check-out dates cannot be null.");
        }

        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new InvalidBookingDateException(checkIn, checkOut);
        }

        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidBookingDateException("Check-in date cannot be in the past.");
        }

        // Validate room availability
        if (!room.isAvailable()) {
            throw new RoomNotAvailableException(room.getRoomNumber());
        }

        Booking booking = new Booking(nextBookingId++, checkIn, checkOut, room, customer);
        bookings.add(booking);
        room.setAvailable(false);
        customer.addBooking(booking);

        System.out.println("Booking created successfully!");
        return booking;
    }

    public void confirmBooking(int bookingId) throws BookingNotFoundException {
        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException(bookingId);
        }
        booking.confirm();
    }

    public void checkInBooking(int bookingId) throws BookingNotFoundException {
        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException(bookingId);
        }
        booking.checkIn();
    }

    public void checkOutBooking(int bookingId) throws BookingNotFoundException {
        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException(bookingId);
        }
        booking.checkOut();
    }

    public void cancelBooking(int bookingId) throws BookingNotFoundException {
        Booking booking = findBookingById(bookingId);
        if (booking == null) {
            throw new BookingNotFoundException(bookingId);
        }
        booking.cancel();
        System.out.println("Booking cancelled successfully!");
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
