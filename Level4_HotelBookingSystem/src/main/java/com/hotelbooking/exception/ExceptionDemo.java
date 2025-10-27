package com.hotelbooking.exception;

import com.hotelbooking.model.*;
import com.hotelbooking.service.BookingService;
import java.time.LocalDate;

public class ExceptionDemo {
    public static void main(String[] args) {
        System.out.println("=== Exception Handling Demo ===\n");

        BookingService bookingService = new BookingService();
        Customer customer = new Customer(1, "Test Customer", "test@example.com", "+41 79 111 22 33");
        Room room = new DoubleRoom(201, 100.0, true);

        // Test 1: Valid booking (should work)
        System.out.println("--- Test 1: Valid Booking ---");
        try {
            LocalDate checkIn = LocalDate.of(2025, 12, 20);
            LocalDate checkOut = LocalDate.of(2025, 12, 25);
            Booking booking = bookingService.createBooking(checkIn, checkOut, room, customer);
            System.out.println("Success: " + booking);
        } catch (RoomNotAvailableException | InvalidBookingDateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 2: Room not available (should throw exception)
        System.out.println("--- Test 2: Room Not Available ---");
        try {
            LocalDate checkIn = LocalDate.of(2026, 1, 10);
            LocalDate checkOut = LocalDate.of(2026, 1, 15);
            Booking booking = bookingService.createBooking(checkIn, checkOut, room, customer);
            System.out.println("Success: " + booking);
        } catch (RoomNotAvailableException e) {
            System.out.println("Caught exception: " + e.getMessage());
        } catch (InvalidBookingDateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 3: Invalid dates (check-out before check-in)
        System.out.println("--- Test 3: Invalid Dates (Check-out before Check-in) ---");
        Room room2 = new SingleRoom(202, 80.0);
        try {
            LocalDate checkIn = LocalDate.of(2025, 12, 25);
            LocalDate checkOut = LocalDate.of(2025, 12, 20); // Before check-in!
            Booking booking = bookingService.createBooking(checkIn, checkOut, room2, customer);
            System.out.println("Success: " + booking);
        } catch (RoomNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidBookingDateException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        System.out.println();

        // Test 4: Booking not found
        System.out.println("--- Test 4: Booking Not Found ---");
        try {
            bookingService.confirmBooking(999); // Non-existent booking
        } catch (BookingNotFoundException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        System.out.println();

        System.out.println("=== All Exception Tests Complete ===");
    }
}
