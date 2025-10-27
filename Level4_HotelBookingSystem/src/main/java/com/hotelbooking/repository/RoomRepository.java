package com.hotelbooking.repository;

import com.hotelbooking.model.Room;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements Repository<Room> {
    private List<Room> rooms;
    private String filename;

    public RoomRepository(String filename) {
        this.rooms = new ArrayList<>();
        this.filename = filename;
    }

    @Override
    public void save(Room room) {
        boolean exists = false;
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber() == room.getRoomNumber()) {
                rooms.set(i, room);
                exists = true;
                break;
            }
        }
        if (!exists) {
            rooms.add(room);
        }
        System.out.println("Room saved to repository.");
    }

    @Override
    public Room findById(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms);
    }

    @Override
    public void delete(int roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
        System.out.println("Room deleted from repository.");
    }

    @Override
    public void saveAll() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Room room : rooms) {
                // Simple format: roomNumber,type,basePrice,available
                String line = room.getRoomNumber() + "," +
                        room.getClass().getSimpleName() + "," +
                        room.getBasePrice() + "," +
                        room.isAvailable();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("All rooms saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving rooms to file: " + e.getMessage());
        }
    }

    @Override
    public void loadAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                count++;
            }
            System.out.println("Loaded " + count + " room entries from file: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("No existing room file found. Starting fresh.");
        } catch (IOException e) {
            System.err.println("Error loading rooms from file: " + e.getMessage());
        }
    }

    public int getRoomCount() {
        return rooms.size();
    }
}