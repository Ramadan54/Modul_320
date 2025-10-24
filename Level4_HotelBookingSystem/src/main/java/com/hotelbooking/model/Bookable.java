package com.hotelbooking.model;

import java.time.LocalDate;

public interface Bookable {
    boolean isAvailable(LocalDate checkIn, LocalDate checkOut);
    double calculatePrice(int numberOfDays);
    int getIdentifier();
}