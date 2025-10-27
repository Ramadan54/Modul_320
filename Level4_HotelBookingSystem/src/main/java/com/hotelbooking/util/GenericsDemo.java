package com.hotelbooking.util;

import com.hotelbooking.model.*;
import java.time.LocalDate;

/**
 * Demonstration of Generics usage in the application
 * Shows how the generic Result<T> class works with different data types
 *
 * @author Ramadan Asani
 */
public class GenericsDemo {
    public static void main(String[] args) {
        System.out.println("═══ GENERICS DEMONSTRATION ═══\n");

        // Example 1: Result with Room type
        demonstrateRoomResult();

        // Example 2: Result with Booking type
        demonstrateBookingResult();

        // Example 3: Result with String type
        demonstrateStringResult();

        // Example 4: Result with Integer type
        demonstrateIntegerResult();

        System.out.println("\n═══ GENERICS DEMO COMPLETE ═══");
    }

    /**
     * Demonstrates Result<Room> - Generic type with Room
     */
    private static void demonstrateRoomResult() {
        System.out.println("--- Example 1: Result<Room> ---");

        // GENERICS: Diamond Operator <> infers type from context
        Room room = new SingleRoom(101, 80.0);
        Result<Room> successResult = Result.success(room, "Room found");

        System.out.println(successResult);
        if (successResult.isSuccess()) {
            System.out.println("Retrieved Room: " + successResult.getData().getDescription());
        }

        // Failure case
        Result<Room> failureResult = Result.failure("Room not found");
        System.out.println(failureResult);
        System.out.println();
    }

    /**
     * Demonstrates Result<Booking> - Generic type with Booking
     */
    private static void demonstrateBookingResult() {
        System.out.println("--- Example 2: Result<Booking> ---");

        Room room = new DoubleRoom(201, 120.0, true);
        Customer customer = new Customer(1, "Max Mustermann", "max@test.com", "+41 79 123 45 67");
        Booking booking = new Booking(1, LocalDate.now(), LocalDate.now().plusDays(3), room, customer);

        // GENERICS: Same Result class works with different type
        Result<Booking> result = Result.success(booking, "Booking created successfully");
        System.out.println(result);
        System.out.println();
    }

    /**
     * Demonstrates Result<String> - Generic type with String
     */
    private static void demonstrateStringResult() {
        System.out.println("--- Example 3: Result<String> ---");

        // GENERICS: Works with String type
        Result<String> result = Result.success("Operation completed", "All validations passed");
        System.out.println(result);
        System.out.println();
    }

    /**
     * Demonstrates Result<Integer> - Generic type with Integer
     */
    private static void demonstrateIntegerResult() {
        System.out.println("--- Example 4: Result<Integer> ---");

        // GENERICS: Works with Integer type
        Result<Integer> result = Result.success(42, "Calculation complete");
        System.out.println(result);
        System.out.println("Result value: " + result.getData());
        System.out.println();
    }
}
