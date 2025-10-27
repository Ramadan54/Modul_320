package com.hotelbooking.validator;

import com.hotelbooking.exception.InvalidBookingDateException;
import java.time.LocalDate;

public class ValidatorDemo {
    public static void main(String[] args) {
        System.out.println("=== Validator Demo ===\n");

        // Test 1: Valid dates
        System.out.println("--- Test 1: Valid Dates ---");
        try {
            LocalDate checkIn = LocalDate.of(2025, 12, 20);
            LocalDate checkOut = LocalDate.of(2025, 12, 25);
            BookingValidator.validateDates(checkIn, checkOut);
            System.out.println("✓ Dates are valid!");
        } catch (InvalidBookingDateException e) {
            System.out.println("✗ " + e.getMessage());
        }
        System.out.println();

        // Test 2: Invalid dates (check-out before check-in)
        System.out.println("--- Test 2: Invalid Dates ---");
        try {
            LocalDate checkIn = LocalDate.of(2025, 12, 25);
            LocalDate checkOut = LocalDate.of(2025, 12, 20);
            BookingValidator.validateDates(checkIn, checkOut);
            System.out.println("✓ Dates are valid!");
        } catch (InvalidBookingDateException e) {
            System.out.println("✗ " + e.getMessage());
        }
        System.out.println();

        // Test 3: Email validation
        System.out.println("--- Test 3: Email Validation ---");
        String[] emails = {
                "max@example.com",
                "invalid-email",
                "test@domain.co.uk",
                "@invalid.com"
        };
        for (String email : emails) {
            boolean valid = BookingValidator.isValidEmail(email);
            System.out.println(email + " → " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        System.out.println();

        // Test 4: Phone validation
        System.out.println("--- Test 4: Phone Validation ---");
        String[] phones = {
                "+41 79 123 45 67",
                "+41 78 999 88 77",
                "079 123 45 67",
                "+41791234567"
        };
        for (String phone : phones) {
            boolean valid = BookingValidator.isValidPhone(phone);
            System.out.println(phone + " → " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        System.out.println();

        // Test 5: Name validation
        System.out.println("--- Test 5: Name Validation ---");
        String[] names = {"Max Mustermann", "A", "John Doe", ""};
        for (String name : names) {
            boolean valid = BookingValidator.isValidName(name);
            System.out.println("\"" + name + "\" → " + (valid ? "✓ Valid" : "✗ Invalid"));
        }
        System.out.println();

        // Test 6: Price validation
        System.out.println("--- Test 6: Price Validation ---");
        double[] prices = {80.0, -10.0, 0.0, 50000.0, 150000.0};
        for (double price : prices) {
            boolean valid = BookingValidator.isValidPrice(price);
            System.out.println(price + " CHF → " + (valid ? "✓ Valid" : "✗ Invalid"));
        }

        System.out.println("\n=== All Validator Tests Complete ===");
    }
}
