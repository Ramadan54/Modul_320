package d1.heating;

public class HeatingMain {
    public static void main(String[] args) throws InterruptedException {
        RoomHeater wohnzimmer = new RoomHeater("Wohnzimmer", 19.0);
        RoomHeater schlafzimmer = new RoomHeater("Schlafzimmer", 17.5);

        CentralHeating zentral = new CentralHeating();
        zentral.addRoom(wohnzimmer);
        zentral.addRoom(schlafzimmer);

        zentral.setAllTargets(21.0);

        for (int i = 0; i < 6; i++) {
            zentral.tickAll();
            Thread.sleep(200);
        }

        System.out.println("Finale Temperaturen: "
                + wohnzimmer.getRoomName() + "=" + String.format("%.1f", wohnzimmer.getCurrentTemp()) + "°C, "
                + schlafzimmer.getRoomName() + "=" + String.format("%.1f", schlafzimmer.getCurrentTemp()) + "°C");
    }
}