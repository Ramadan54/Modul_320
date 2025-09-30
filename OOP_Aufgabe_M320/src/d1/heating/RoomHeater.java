package d1.heating;

public class RoomHeater {

    private final String roomName;
    private double currentTemp;
    private double targetTemp;

    public RoomHeater(String roomName, double startTemp) {
        this.roomName = roomName;
        this.currentTemp = startTemp;
        this.targetTemp = startTemp;
    }

    public String getRoomName() { return roomName; }
    public double getCurrentTemp() { return currentTemp; }
    public double getTargetTemp() { return targetTemp; }

    public void setTargetTemp(double targetTemp) {
        if (targetTemp < 10 || targetTemp > 30) {
            System.out.println("Unrealistisches Ziel (" + targetTemp + "°C) für " + roomName);
            return;
        }
        this.targetTemp = targetTemp;
        System.out.println(roomName + ": neues Ziel " + targetTemp + "°C");
    }

    public void tick() {
        if (Math.abs(currentTemp - targetTemp) < 0.1) return;
        if (currentTemp < targetTemp) currentTemp += 0.5;
        else currentTemp -= 0.5;
        System.out.println(roomName + ": aktuelle Temp " + String.format("%.1f", currentTemp) + "°C (Ziel " + targetTemp + "°C)");
    }
}