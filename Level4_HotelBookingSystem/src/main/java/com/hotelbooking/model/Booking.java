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

    public Booking(int bookingId, LocalDate checkInDate, LocalDate checkOutDate,
                   Room room, Customer customer) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.customer = customer;
        this.totalPrice = calculateTotalPrice();
    }

    public double calculateTotalPrice() {
        int numberOfDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return room.calculatePrice(numberOfDays);
    }

    public void cancel() {
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

    @Override
    public String toString() {
        return "Booking #" + bookingId +
                " | Room: " + room.getRoomNumber() +
                " | Check-in: " + checkInDate +
                " | Check-out: " + checkOutDate +
                " | Total: " + totalPrice + " CHF";
    }
}