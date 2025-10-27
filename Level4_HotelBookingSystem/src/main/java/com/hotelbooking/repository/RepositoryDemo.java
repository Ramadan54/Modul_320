package com.hotelbooking.repository;

import com.hotelbooking.model.*;
import java.time.LocalDate;

public class RepositoryDemo {
    public static void main(String[] args) {
        System.out.println("=== Repository/Persistenz Demo ===\n");

        // Initialize repositories
        RoomRepository roomRepo = new RoomRepository("rooms.csv");
        BookingRepository bookingRepo = new BookingRepository("bookings.csv");

        // Try to load existing data
        roomRepo.loadAll();
        bookingRepo.loadAll();
        System.out.println();

        // Create and save rooms
        System.out.println("=== Adding Rooms to Repository ===");
        Room room101 = new SingleRoom(101, 80.0);
        Room room102 = new DoubleRoom(102, 120.0, true);
        Room room103 = new Suite(103, 250.0, 3, true);

        roomRepo.save(room101);
        roomRepo.save(room102);
        roomRepo.save(room103);
        System.out.println();

        // Create booking
        System.out.println("=== Creating Booking ===");
        Customer customer = new Customer(1, "Max Mustermann", "max@example.com", "+41 79 123 45 67");
        LocalDate checkIn = LocalDate.of(2025, 12, 10);
        LocalDate checkOut = LocalDate.of(2025, 12, 15);

        Booking booking = new Booking(1, checkIn, checkOut, room102, customer);
        bookingRepo.save(booking);
        System.out.println();

        // Display repository contents
        System.out.println("=== Repository Contents ===");
        System.out.println("Total rooms in repository: " + roomRepo.getRoomCount());
        System.out.println("Total bookings in repository: " + bookingRepo.getBookingCount());
        System.out.println();

        // Save all to files
        System.out.println("=== Saving to Files ===");
        roomRepo.saveAll();
        bookingRepo.saveAll();
        System.out.println();

        // Find by ID
        System.out.println("=== Finding Room by ID ===");
        Room foundRoom = roomRepo.findById(102);
        if (foundRoom != null) {
            System.out.println("Found: " + foundRoom.getDescription());
        }
        System.out.println();

        // Find all
        System.out.println("=== All Rooms in Repository ===");
        for (Room room : roomRepo.findAll()) {
            System.out.println(room.getDescription());
        }

        System.out.println("\n=== Files created in project root! ===");
        System.out.println("Check: rooms.csv and bookings.csv");
    }
}