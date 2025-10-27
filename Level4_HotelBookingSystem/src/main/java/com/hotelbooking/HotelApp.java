package com.hotelbooking;

import com.hotelbooking.exception.*;
import com.hotelbooking.model.*;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.RoomRepository;
import com.hotelbooking.service.BookingService;
import com.hotelbooking.service.RoomService;

import java.time.LocalDate;

/**
 * Main application class for the Hotel Booking System
 * Demonstrates all use cases and features of the system
 *
 * @author [Ramadan Asani]
 * @version 1.0
 */
public class HotelApp {

    private static RoomService roomService;
    private static BookingService bookingService;
    private static RoomRepository roomRepository;
    private static BookingRepository bookingRepository;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     HOTEL BOOKING SYSTEM - DEMO APP        ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Initialize application
        initializeSystem();

        // Run all use case demonstrations
        demonstrateUseCases();

        // Show final system state
        showSystemSummary();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║          DEMO COMPLETE - SUCCESS!          ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    /**
     * Initialize all services and repositories
     * Sets up the system with sample data
     */
    private static void initializeSystem() {
        System.out.println("═══ SYSTEM INITIALIZATION ═══\n");

        // Initialize repositories
        roomRepository = new RoomRepository("rooms.csv");
        bookingRepository = new BookingRepository("bookings.csv");

        // Initialize services
        roomService = new RoomService();
        bookingService = new BookingService();

        // Load existing data
        roomRepository.loadAll();
        bookingRepository.loadAll();

        // Add sample rooms
        System.out.println("Adding rooms to system...");
        roomService.addRoom(new SingleRoom(101, 80.0));
        roomService.addRoom(new SingleRoom(102, 85.0));
        roomService.addRoom(new DoubleRoom(201, 120.0, true));
        roomService.addRoom(new DoubleRoom(202, 110.0, false));
        roomService.addRoom(new Suite(301, 250.0, 3, true));
        roomService.addRoom(new Suite(302, 280.0, 4, true));

        System.out.println("✓ System initialized successfully!\n");
    }

    /**
     * Demonstrate all use cases of the system
     */
    private static void demonstrateUseCases() {
        // Use Case 1: View Available Rooms
        useCase1_ViewAvailableRooms();

        // Use Case 2: Create Booking
        useCase2_CreateBooking();

        // Use Case 3: Add Additional Services (Decorator Pattern)
        useCase3_AddAdditionalServices();

        // Use Case 4: Booking Lifecycle
        useCase4_BookingLifecycle();

        // Use Case 5: Exception Handling
        useCase5_ExceptionHandling();

        // Use Case 6: Persistence
        useCase6_Persistence();
    }

    /**
     * USE CASE 1: View Available Rooms
     * User can see all available rooms with their details
     */
    private static void useCase1_ViewAvailableRooms() {
        System.out.println("═══ USE CASE 1: View Available Rooms ═══\n");
        roomService.displayAvailableRooms();
        System.out.println();
    }

    /**
     * USE CASE 2: Create Booking
     * User can create a new booking for a room
     */
    private static void useCase2_CreateBooking() {
        System.out.println("═══ USE CASE 2: Create Booking ═══\n");

        // Create customer
        Customer customer1 = new Customer(1, "Max Mustermann",
                "max@example.com", "+41 79 123 45 67");
        System.out.println("Customer registered: " + customer1);
        System.out.println();

        // Create booking
        try {
            Room room = roomService.findRoomByNumber(201);
            LocalDate checkIn = LocalDate.of(2025, 12, 15);
            LocalDate checkOut = LocalDate.of(2025, 12, 20);

            Booking booking = bookingService.createBooking(checkIn, checkOut, room, customer1);
            System.out.println("Booking created: " + booking);

            // Save to repository
            bookingRepository.save(booking);
            roomRepository.save(room);

        } catch (RoomNotAvailableException | InvalidBookingDateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
    }

    /**
     * USE CASE 3: Add Additional Services
     * Demonstrates Decorator Pattern - adding services to rooms
     */
    private static void useCase3_AddAdditionalServices() {
        System.out.println("═══ USE CASE 3: Add Additional Services (DECORATOR PATTERN) ═══\n");

        Room basicRoom = new SingleRoom(103, 80.0);
        System.out.println("Basic Room:");
        System.out.println("  " + basicRoom.getDescription());
        System.out.println("  Price (3 nights): " + basicRoom.calculatePrice(3) + " CHF\n");

        // Add breakfast
        Room withBreakfast = new Breakfast(basicRoom);
        System.out.println("Room + Breakfast:");
        System.out.println("  " + withBreakfast.getDescription());
        System.out.println("  Price (3 nights): " + withBreakfast.calculatePrice(3) + " CHF\n");

        // Add breakfast + parking
        Room withBreakfastParking = new Parking(new Breakfast(basicRoom));
        System.out.println("Room + Breakfast + Parking:");
        System.out.println("  " + withBreakfastParking.getDescription());
        System.out.println("  Price (3 nights): " + withBreakfastParking.calculatePrice(3) + " CHF\n");

        // Full package
        Room fullPackage = new Wellness(new Parking(new Breakfast(basicRoom)));
        System.out.println("Room + Full Package (Breakfast + Parking + Wellness):");
        System.out.println("  " + fullPackage.getDescription());
        System.out.println("  Price (3 nights): " + fullPackage.calculatePrice(3) + " CHF\n");

        System.out.println("✓ Decorator Pattern successfully demonstrated!\n");
    }

    /**
     * USE CASE 4: Booking Lifecycle
     * Shows complete booking flow: Create → Confirm → Check-in → Check-out
     */
    private static void useCase4_BookingLifecycle() {
        System.out.println("═══ USE CASE 4: Booking Lifecycle ═══\n");

        Customer customer2 = new Customer(2, "Anna Schmidt",
                "anna@example.com", "+41 78 999 88 77");

        try {
            // Create booking
            Room room = roomService.findRoomByNumber(202);
            LocalDate checkIn = LocalDate.of(2025, 12, 22);
            LocalDate checkOut = LocalDate.of(2025, 12, 27);

            Booking booking = bookingService.createBooking(checkIn, checkOut, room, customer2);
            System.out.println("1. Created: " + booking + "\n");

            // Confirm booking
            bookingService.confirmBooking(booking.getBookingId());
            System.out.println("2. Confirmed: " + booking + "\n");

            // Check-in
            bookingService.checkInBooking(booking.getBookingId());
            System.out.println("3. Checked In: " + booking + "\n");

            // Check-out
            bookingService.checkOutBooking(booking.getBookingId());
            System.out.println("4. Checked Out: " + booking + "\n");

            System.out.println("✓ Complete booking lifecycle demonstrated!\n");

        } catch (RoomNotAvailableException | InvalidBookingDateException | BookingNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * USE CASE 5: Exception Handling
     * Demonstrates custom exceptions and validation
     */
    private static void useCase5_ExceptionHandling() {
        System.out.println("═══ USE CASE 5: Exception Handling & Validation ═══\n");

        Customer customer3 = new Customer(3, "Test Customer",
                "test@test.com", "+41 79 111 22 33");

        // Test 1: Invalid dates (check-out before check-in)
        System.out.println("Test 1: Invalid Dates (Check-out before Check-in)");
        try {
            Room room = roomService.findRoomByNumber(301);
            LocalDate checkIn = LocalDate.of(2025, 12, 30);
            LocalDate checkOut = LocalDate.of(2025, 12, 25); // Before check-in!
            bookingService.createBooking(checkIn, checkOut, room, customer3);
        } catch (InvalidBookingDateException e) {
            System.out.println("✓ Exception caught: " + e.getMessage());
        } catch (RoomNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 2: Room not available
        System.out.println("Test 2: Room Not Available");
        try {
            Room room = roomService.findRoomByNumber(201); // Already booked!
            LocalDate checkIn = LocalDate.of(2025, 12, 16);
            LocalDate checkOut = LocalDate.of(2025, 12, 18);
            bookingService.createBooking(checkIn, checkOut, room, customer3);
        } catch (RoomNotAvailableException e) {
            System.out.println("✓ Exception caught: " + e.getMessage());
        } catch (InvalidBookingDateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        // Test 3: Booking not found
        System.out.println("Test 3: Booking Not Found");
        try {
            bookingService.confirmBooking(999); // Non-existent
        } catch (BookingNotFoundException e) {
            System.out.println("✓ Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("✓ Exception handling successfully demonstrated!\n");
    }

    /**
     * USE CASE 6: Persistence
     * Demonstrates saving data to files (Repository Pattern)
     */
    private static void useCase6_Persistence() {
        System.out.println("═══ USE CASE 6: Data Persistence (REPOSITORY PATTERN) ═══\n");

        // Save all rooms
        System.out.println("Saving all rooms to repository...");
        for (Room room : roomService.getAllRooms()) {
            roomRepository.save(room);
        }

        // Save all bookings
        System.out.println("Saving all bookings to repository...");
        for (Booking booking : bookingService.getAllBookings()) {
            bookingRepository.save(booking);
        }

        // Persist to files
        roomRepository.saveAll();
        bookingRepository.saveAll();

        System.out.println("\n✓ Data successfully persisted to CSV files!");
        System.out.println("  - rooms.csv");
        System.out.println("  - bookings.csv\n");
    }

    /**
     * Display final system summary
     */
    private static void showSystemSummary() {
        System.out.println("═══ SYSTEM SUMMARY ═══\n");

        System.out.println("Total Rooms: " + roomService.getAllRooms().size());
        System.out.println("Available Rooms: " + roomService.getAvailableRooms().size());
        System.out.println("Total Bookings: " + bookingService.getAllBookings().size());
        System.out.println();

        System.out.println("Design Patterns Used:");
        System.out.println("  ✓ Decorator Pattern (Additional Services)");
        System.out.println("  ✓ Repository Pattern (Data Persistence)");
        System.out.println();

        System.out.println("Features Demonstrated:");
        System.out.println("  ✓ Inheritance (Room hierarchy)");
        System.out.println("  ✓ Polymorphism (calculatePrice override)");
        System.out.println("  ✓ Interfaces (Bookable, AdditionalService, Repository)");
        System.out.println("  ✓ Exception Handling (4 custom exceptions)");
        System.out.println("  ✓ Validation (BookingValidator)");
        System.out.println("  ✓ Clean Architecture (Model-Service-Repository)");
    }
}
