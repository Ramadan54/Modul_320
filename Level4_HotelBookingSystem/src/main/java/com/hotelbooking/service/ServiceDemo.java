package com.hotelbooking.service;

import com.hotelbooking.model.*;
import com.hotelbooking.exception.*;
import java.time.LocalDate;

public class ServiceDemo {
    public static void main(String[] args) {
        System.out.println("=== Hotel Booking System - Service Layer Demo ===\n");

        // Initialize services
        RoomService roomService = new RoomService();
        BookingService bookingService = new BookingService();

        // Add rooms to system
        Room room101 = new SingleRoom(101, 80.0);
        Room room102 = new DoubleRoom(102, 120.0, true);
        Room room103 = new Suite(103, 250.0, 3, true);

        roomService.addRoom(room101);
        roomService.addRoom(room102);
        roomService.addRoom(room103);
        System.out.println();

        // Display all rooms
        roomService.displayAllRooms();
        System.out.println();

        // Create customer
        Customer customer1 = new Customer(1, "Max Mustermann",
                "max@example.com", "+41 79 123 45 67");

        // Create booking using service with exception handling
        System.out.println("=== Creating Booking ===");
        LocalDate checkIn = LocalDate.of(2025, 11, 15);
        LocalDate checkOut = LocalDate.of(2025, 11, 20);

        Booking booking = null;
        try {
            booking = bookingService.createBooking(checkIn, checkOut, room102, customer1);
            System.out.println(booking);
        } catch (RoomNotAvailableException | InvalidBookingDateException e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
        System.out.println();

        // Display available rooms (room102 should now be occupied)
        roomService.displayAvailableRooms();
        System.out.println();

        // Booking lifecycle using service
        if (booking != null) {
            System.out.println("=== Booking Lifecycle ===");
            try {
                bookingService.confirmBooking(1);
                bookingService.checkInBooking(1);
                bookingService.checkOutBooking(1);
            } catch (BookingNotFoundException e) {
                System.out.println("Error in booking lifecycle: " + e.getMessage());
            }
            System.out.println();
        }

        // Display available rooms (room102 should be available again)
        roomService.displayAvailableRooms();
        System.out.println();

        // Display all bookings
        System.out.println("=== All Bookings ===");
        for (Booking b : bookingService.getAllBookings()) {
            System.out.println(b);
        }
    }
}