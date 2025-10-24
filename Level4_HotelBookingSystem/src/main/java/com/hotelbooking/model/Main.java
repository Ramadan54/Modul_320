package com.hotelbooking.model;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hotel Booking System ===\n");

        // Create rooms
        Room room101 = new SingleRoom(101, 80.0);
        Room room102 = new DoubleRoom(102, 120.0, true);
        Room room103 = new Suite(103, 250.0, 3, true);

        // Display room descriptions
        System.out.println("Available Rooms:");
        System.out.println(room101.getDescription());
        System.out.println(room102.getDescription());
        System.out.println(room103.getDescription());
        System.out.println();

        // Create customer
        Customer customer1 = new Customer(1, "Max Mustermann",
                "max@example.com", "+41 79 123 45 67");
        System.out.println("Customer created: " + customer1);
        System.out.println();

        // Create booking
        LocalDate checkIn = LocalDate.of(2025, 11, 1);
        LocalDate checkOut = LocalDate.of(2025, 11, 5);

        Booking booking1 = new Booking(1, checkIn, checkOut, room102, customer1);
        customer1.addBooking(booking1);
        room102.setAvailable(false);

        System.out.println("Booking created:");
        System.out.println(booking1);
        System.out.println();

        // Calculate price for different rooms
        System.out.println("Price calculations for 4 nights:");
        System.out.println("Single Room: " + room101.calculatePrice(4) + " CHF");
        System.out.println("Double Room with Balcony: " + room102.calculatePrice(4) + " CHF");
        System.out.println("Suite with Jacuzzi: " + room103.calculatePrice(4) + " CHF");

        // DECORATOR PATTERN DEMO
        System.out.println("\n--- Decorator Pattern - Additional Services ---\n");

        // Basic room
        Room basicRoom = new SingleRoom(201, 80.0);
        System.out.println(basicRoom.getDescription());
        System.out.println("Price for 3 nights: " + basicRoom.calculatePrice(3) + " CHF\n");

        // Room with Breakfast
        Room roomWithBreakfast = new Breakfast(basicRoom);
        System.out.println(roomWithBreakfast.getDescription());
        System.out.println("Price for 3 nights: " + roomWithBreakfast.calculatePrice(3) + " CHF\n");

        // Room with Breakfast AND Parking (stacking decorators!)
        Room roomWithBreakfastAndParking = new Parking(new Breakfast(basicRoom));
        System.out.println(roomWithBreakfastAndParking.getDescription());
        System.out.println("Price for 3 nights: " + roomWithBreakfastAndParking.calculatePrice(3) + " CHF\n");

        // Full package: Breakfast + Parking + Wellness
        Room fullPackage = new Wellness(new Parking(new Breakfast(basicRoom)));
        System.out.println(fullPackage.getDescription());
        System.out.println("Price for 3 nights: " + fullPackage.calculatePrice(3) + " CHF");
    }
}