package com.hotelbooking.validator;

import com.hotelbooking.exception.InvalidBookingDateException;
import java.time.LocalDate;

public class BookingValidator {

    /**
     * Validates booking dates
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @throws InvalidBookingDateException if dates are invalid
     */
    public static void validateDates(LocalDate checkIn, LocalDate checkOut)
            throws InvalidBookingDateException {

        // Check for null dates
        if (checkIn == null || checkOut == null) {
            throw new InvalidBookingDateException("Check-in and check-out dates cannot be null.");
        }

        // Check if check-out is before or equal to check-in
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new InvalidBookingDateException(checkIn, checkOut);
        }

        // Check if check-in is in the past
        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidBookingDateException("Check-in date cannot be in the past.");
        }

        // Check if booking is too far in the future (e.g., max 2 years)
        if (checkIn.isAfter(LocalDate.now().plusYears(2))) {
            throw new InvalidBookingDateException("Check-in date cannot be more than 2 years in the future.");
        }
    }

    /**
     * Validates customer email format
     * @param email Email address
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Simple email validation
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Validates phone number format (Swiss format)
     * @param phone Phone number
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        // Swiss phone format: +41 XX XXX XX XX
        return phone.matches("^\\+41 \\d{2} \\d{3} \\d{2} \\d{2}$");
    }

    /**
     * Validates customer name
     * @param name Customer name
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.length() >= 2 && name.length() <= 100;
    }

    /**
     * Validates room number
     * @param roomNumber Room number
     * @return true if valid, false otherwise
     */
    public static boolean isValidRoomNumber(int roomNumber) {
        return roomNumber > 0 && roomNumber < 10000;
    }

    /**
     * Validates price
     * @param price Price value
     * @return true if valid, false otherwise
     */
    public static boolean isValidPrice(double price) {
        return price > 0 && price <= 100000;
    }
}
