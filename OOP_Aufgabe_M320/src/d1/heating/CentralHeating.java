package d1.heating;

import java.util.ArrayList;
import java.util.List;

public class CentralHeating {
    private final List<RoomHeater> rooms = new ArrayList<>();

    public void addRoom(RoomHeater room) {
        if (room != null) rooms.add(room);
    }

    public void setAllTargets(double target) {
        System.out.println("Zentrale: setze alle Ziel-Temperaturen auf " + target + "°C");
        for (RoomHeater r : rooms) {
            r.setTargetTemp(target);
        }
    }

    public void tickAll() {
        for (RoomHeater r : rooms) {
            r.tick();
        }
    }
}