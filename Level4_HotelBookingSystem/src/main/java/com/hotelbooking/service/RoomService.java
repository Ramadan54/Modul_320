package com.hotelbooking.service;

import com.hotelbooking.model.Room;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private List<Room> rooms;

    public RoomService() {
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room #" + room.getRoomNumber() + " added to system.");
    }

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void displayAllRooms() {
        System.out.println("=== All Rooms ===");
        for (Room room : rooms) {
            String status = room.isAvailable() ? "Available" : "Occupied";
            System.out.println(room.getDescription() + " - " + status);
        }
    }

    public void displayAvailableRooms() {
        System.out.println("=== Available Rooms ===");
        List<Room> available = getAvailableRooms();
        if (available.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            for (Room room : available) {
                System.out.println(room.getDescription());
            }
        }
    }
}