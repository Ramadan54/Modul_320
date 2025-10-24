package com.hotelbooking.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private int bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room room;
    private Customer customer;
    private double totalPrice;
    private BookingStatus status;

    public Booking(int bookingId, LocalDate checkInDate, LocalDate checkOutDate,
                   Room room, Customer customer) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.customer = customer;
        this.totalPrice = calculateTotalPrice();
        this.status = BookingStatus.RESERVED;
    }

    public double calculateTotalPrice() {
        int numberOfDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return room.calculatePrice(numberOfDays);
    }

    public void confirm() {
        this.status = BookingStatus.CONFIRMED;
        System.out.println("Booking #" + bookingId + " confirmed.");
    }

    public void checkIn() {
        if (status == BookingStatus.CONFIRMED) {
            this.status = BookingStatus.CHECKED_IN;
            System.out.println("Check-in successful for Booking #" + bookingId);
        } else {
            System.out.println("Cannot check in. Booking must be confirmed first.");
        }
    }

    public void checkOut() {
        if (status == BookingStatus.CHECKED_IN) {
            this.status = BookingStatus.CHECKED_OUT;
            room.setAvailable(true);
            System.out.println("Check-out successful for Booking #" + bookingId);
        } else {
            System.out.println("Cannot check out. Customer must be checked in first.");
        }
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        room.setAvailable(true);
        System.out.println("Booking #" + bookingId + " has been cancelled.");
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Booking #" + bookingId +
                " | Room: " + room.getRoomNumber() +
                " | Check-in: " + checkInDate +
                " | Check-out: " + checkOutDate +
                " | Total: " + totalPrice + " CHF" +
                " | Status: " + status;
    }
}