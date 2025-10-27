package com.hotelbooking.repository;

import com.hotelbooking.model.Booking;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements Repository<Booking> {
    private List<Booking> bookings;
    private String filename;

    public BookingRepository(String filename) {
        this.bookings = new ArrayList<>();
        this.filename = filename;
    }

    @Override
    public void save(Booking booking) {
        // Check if booking already exists
        boolean exists = false;
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == booking.getBookingId()) {
                bookings.set(i, booking);
                exists = true;
                break;
            }
        }
        if (!exists) {
            bookings.add(booking);
        }
        System.out.println("Booking saved to repository.");
    }

    @Override
    public Booking findById(int id) {
        for (Booking booking : bookings) {
            if (booking.getBookingId() == id) {
                return booking;
            }
        }
        return null;
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    @Override
    public void delete(int id) {
        bookings.removeIf(booking -> booking.getBookingId() == id);
        System.out.println("Booking deleted from repository.");
    }

    @Override
    public void saveAll() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Booking booking : bookings) {
                // Simple CSV format: id,checkIn,checkOut,roomNumber,customerId,totalPrice,status
                String line = booking.getBookingId() + "," +
                        booking.getCheckInDate() + "," +
                        booking.getCheckOutDate() + "," +
                        booking.getRoom().getRoomNumber() + "," +
                        booking.getCustomer().getCustomerId() + "," +
                        booking.getTotalPrice() + "," +
                        booking.getStatus();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("All bookings saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving bookings to file: " + e.getMessage());
        }
    }

    @Override
    public void loadAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                // Note: Full loading would require room and customer reconstruction
                // For now, we just count the entries
                count++;
            }
            System.out.println("Loaded " + count + " booking entries from file: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("No existing booking file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading bookings from file: " + e.getMessage());
        }
    }

    public int getBookingCount() {
        return bookings.size();
    }
}