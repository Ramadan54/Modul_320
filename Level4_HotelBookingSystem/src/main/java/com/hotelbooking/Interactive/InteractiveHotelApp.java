package com.hotelbooking.Interactive;

import com.hotelbooking.exception.*;
import com.hotelbooking.model.*;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.RoomRepository;
import com.hotelbooking.service.BookingService;
import com.hotelbooking.service.RoomService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interaktive Konsolen-Anwendung für das Hotel-Buchungssystem
 * Mit robuster Fehlerbehandlung und benutzerfreundlicher Navigation
 *
 * @author Ramadan Asani
 * @version 2.0
 */
public class InteractiveHotelApp {

    private static RoomService roomService;
    private static BookingService bookingService;
    private static RoomRepository roomRepository;
    private static BookingRepository bookingRepository;
    private static Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // Beispiel-Kunden für schnelles Testen
    private static Customer[] exampleCustomers;
    private static int customerIdCounter = 4;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            printWelcomeBanner();
            initializeSystem();
            runMainMenu();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void printWelcomeBanner() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                          ║");
        System.out.println("║          🏨  HOTEL BUCHUNGSSYSTEM DEMO  🏨              ║");
        System.out.println("║                                                          ║");
        System.out.println("║              Willkommen im Grand Hotel                  ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private static void initializeSystem() {
        System.out.println("\n⏳ System wird initialisiert...\n");

        roomRepository = new RoomRepository("rooms.csv");
        bookingRepository = new BookingRepository("bookings.csv");
        roomService = new RoomService();
        bookingService = new BookingService();

        roomRepository.loadAll();
        bookingRepository.loadAll();

        addSampleRooms();
        createExampleCustomers();

        System.out.println("✅ System erfolgreich initialisiert!\n");
    }

    private static void addSampleRooms() {
        System.out.println("📋 Füge Beispiel-Zimmer hinzu...");

        roomService.addRoom(new SingleRoom(101, 80.0));
        roomService.addRoom(new SingleRoom(102, 85.0));
        roomService.addRoom(new SingleRoom(103, 90.0));
        roomService.addRoom(new DoubleRoom(201, 120.0, true));
        roomService.addRoom(new DoubleRoom(202, 110.0, false));
        roomService.addRoom(new DoubleRoom(203, 125.0, true));
        roomService.addRoom(new Suite(301, 250.0, 3, true));
        roomService.addRoom(new Suite(302, 280.0, 4, true));
        roomService.addRoom(new Suite(303, 300.0, 5, true));

        System.out.println("   ✓ " + roomService.getAllRooms().size() + " Zimmer hinzugefügt");
    }

    private static void createExampleCustomers() {
        exampleCustomers = new Customer[3];
        exampleCustomers[0] = new Customer(1, "Max Mustermann", "max@example.com", "+41 79 123 45 67");
        exampleCustomers[1] = new Customer(2, "Anna Schmidt", "anna@example.com", "+41 78 999 88 77");
        exampleCustomers[2] = new Customer(3, "Peter Müller", "peter@example.com", "+41 76 555 66 77");
    }

    private static void runMainMenu() {
        boolean running = true;

        while (running) {
            printMainMenu();

            try {
                int choice = readIntInput("\n👉 Ihre Wahl: ", 0, 9);

                switch (choice) {
                    case 1: viewAllRooms(); break;
                    case 2: viewAvailableRooms(); break;
                    case 3: createNewBooking(); break;
                    case 4: viewAllBookings(); break;
                    case 5: manageBooking(); break;
                    case 6: demonstrateDecoratorPattern(); break;
                    case 7: quickDemo(); break;
                    case 8: saveAllData(); break;
                    case 9: showSystemInfo(); break;
                    case 0: running = confirmExit(); break;
                }

            } catch (Exception e) {
                System.err.println("\n❌ Unerwarteter Fehler: " + e.getMessage());
                if (scanner.hasNextLine()) {
                    System.out.println("Geben sie eine belibiege Zahl ein um fortzufahren...");
                    scanner.nextLine();
                } else {
                    running = false;
                }
            }
        }

        System.out.println("\n👋 Vielen Dank für die Nutzung des Hotel-Buchungssystems!");
        System.out.println("Auf Wiedersehen! 🏨\n");
    }

    private static void printMainMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                    HAUPTMENÜ                             ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║                                                          ║");
        System.out.println("║  📋  ZIMMER-VERWALTUNG                                  ║");
        System.out.println("║      1 - Alle Zimmer anzeigen                            ║");
        System.out.println("║      2 - Verfügbare Zimmer anzeigen                      ║");
        System.out.println("║                                                          ║");
        System.out.println("║  📅  BUCHUNGS-VERWALTUNG                                ║");
        System.out.println("║      3 - Neue Buchung erstellen                          ║");
        System.out.println("║      4 - Alle Buchungen anzeigen                         ║");
        System.out.println("║      5 - Buchung verwalten                               ║");
        System.out.println("║                                                          ║");
        System.out.println("║  🎨  FEATURES                                            ║");
        System.out.println("║      6 - Zusatzleistungen Demo (Decorator Pattern)      ║");
        System.out.println("║      7 - Schnell-Demo (Kompletter Ablauf)               ║");
        System.out.println("║                                                          ║");
        System.out.println("║  ⚙️   SYSTEM                                             ║");
        System.out.println("║      8 - Alle Daten speichern                            ║");
        System.out.println("║      9 - System-Informationen                            ║");
        System.out.println("║      0 - Beenden                                         ║");
        System.out.println("║                                                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private static void viewAllRooms() {
        printSectionHeader("Alle Zimmer");

        List<Room> rooms = roomService.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("❌ Keine Zimmer im System vorhanden.");
        } else {
            System.out.println("\nGesamtanzahl: " + rooms.size() + " Zimmer\n");
            System.out.println("┌──────────┬─────────────────────┬────────────┬────────────┐");
            System.out.println("│ Zimmer   │ Typ                 │ Preis/Nacht│ Status     │");
            System.out.println("├──────────┼─────────────────────┼────────────┼────────────┤");

            for (Room room : rooms) {
                String roomType = getRoomTypeName(room);
                String status = room.isAvailable() ? "✅ Frei" : "❌ Belegt";
                System.out.printf("│ %-8d │ %-19s │ %7.2f CHF│ %-10s │%n",
                        room.getRoomNumber(), roomType, room.calculatePrice(1), status);
            }

            System.out.println("└──────────┴─────────────────────┴────────────┴────────────┘");
        }

        pauseForUser();
    }

    private static void viewAvailableRooms() {
        printSectionHeader("Verfügbare Zimmer");
        roomService.displayAvailableRooms();
        pauseForUser();
    }

    private static void createNewBooking() {
        printSectionHeader("Neue Buchung erstellen");

        try {
            List<Room> availableRooms = roomService.getAvailableRooms();

            if (availableRooms.isEmpty()) {
                System.out.println("❌ Keine verfügbaren Zimmer vorhanden.");
                pauseForUser();
                return;
            }

            System.out.println("\n📋 Verfügbare Zimmer:\n");
            for (int i = 0; i < availableRooms.size(); i++) {
                Room room = availableRooms.get(i);
                System.out.printf("  %d) Zimmer %d - %s (%.2f CHF/Nacht)%n",
                        i + 1, room.getRoomNumber(), getRoomTypeName(room), room.calculatePrice(1));
            }

            int roomChoice = readIntInput("\n👉 Wählen Sie ein Zimmer (Nummer): ", 1, availableRooms.size());
            Room selectedRoom = availableRooms.get(roomChoice - 1);

            System.out.println("\n✓ Zimmer " + selectedRoom.getRoomNumber() + " ausgewählt");

            Customer customer = selectOrCreateCustomer();

            System.out.println("\n📅 Buchungsdaten:");
            LocalDate checkIn = readDateInput("   Check-in Datum (z.B. 15.12.2025): ");
            LocalDate checkOut = readDateInput("   Check-out Datum (z.B. 20.12.2025): ");

            System.out.println("\n⏳ Erstelle Buchung...");
            Booking booking = bookingService.createBooking(checkIn, checkOut, selectedRoom, customer);

            bookingRepository.save(booking);
            roomRepository.save(selectedRoom);

            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║              ✅ BUCHUNG ERFOLGREICH                      ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            System.out.println("\n" + booking);
            System.out.println("\nGesamtpreis: " + booking.getTotalPrice() + " CHF");

        } catch (RoomNotAvailableException e) {
            System.err.println("\n❌ Fehler: " + e.getMessage());
            System.out.println("Bitte wählen Sie ein anderes Zimmer.");
        } catch (InvalidBookingDateException e) {
            System.err.println("\n❌ Fehler: " + e.getMessage());
            System.out.println("Bitte geben Sie gültige Daten ein.");
        }

        pauseForUser();
    }

    private static void viewAllBookings() {
        printSectionHeader("Alle Buchungen");

        List<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("❌ Keine Buchungen vorhanden.");
        } else {
            System.out.println("\nGesamtanzahl: " + bookings.size() + " Buchung(en)\n");

            for (Booking booking : bookings) {
                System.out.println("─────────────────────────────────────────────────────────");
                System.out.println(booking);
                System.out.println("Gesamtpreis: " + booking.getTotalPrice() + " CHF");
            }
            System.out.println("─────────────────────────────────────────────────────────");
        }

        pauseForUser();
    }

    private static void manageBooking() {
        printSectionHeader("Buchung verwalten");

        List<Booking> bookings = bookingService.getAllBookings();

        if (bookings.isEmpty()) {
            System.out.println("❌ Keine Buchungen vorhanden.");
            pauseForUser();
            return;
        }

        System.out.println("\n📋 Vorhandene Buchungen:\n");
        for (Booking booking : bookings) {
            System.out.printf("  ID %d: %s - Zimmer %d - Status: %s%n",
                    booking.getBookingId(),
                    booking.getCustomer().getName(),
                    booking.getRoom().getRoomNumber(),
                    booking.getStatus());
        }

        int bookingId = readIntInput("\n👉 Geben Sie die Buchungs-ID ein: ", 1, Integer.MAX_VALUE);

        try {
            Booking booking = bookingService.findBookingById(bookingId);
            if (booking == null) {
                throw new BookingNotFoundException(bookingId);
            }

            boolean back = false;
            while (!back) {
                System.out.println("\n╔══════════════════════════════════════════════════════════╗");
                System.out.println("║           Buchung #" + bookingId + " - Aktionen                     ║");
                System.out.println("╚══════════════════════════════════════════════════════════╝");
                System.out.println("\n" + booking);
                System.out.println("\n1 - Buchung bestätigen");
                System.out.println("2 - Check-in durchführen");
                System.out.println("3 - Check-out durchführen");
                System.out.println("4 - Buchung stornieren");
                System.out.println("0 - Zurück");

                int action = readIntInput("\n👉 Ihre Wahl: ", 0, 4);

                try {
                    switch (action) {
                        case 1:
                            bookingService.confirmBooking(bookingId);
                            System.out.println("\n✅ Buchung wurde bestätigt!");
                            break;
                        case 2:
                            bookingService.checkInBooking(bookingId);
                            System.out.println("\n✅ Check-in durchgeführt!");
                            break;
                        case 3:
                            bookingService.checkOutBooking(bookingId);
                            System.out.println("\n✅ Check-out durchgeführt!");
                            break;
                        case 4:
                            bookingService.cancelBooking(bookingId);
                            System.out.println("\n✅ Buchung wurde storniert!");
                            break;
                        case 0:
                            back = true;
                            break;
                    }

                    if (action != 0) {
                        pauseForUser();
                    }

                } catch (BookingNotFoundException e) {
                    System.err.println("\n❌ Fehler: " + e.getMessage());
                    pauseForUser();
                }
            }

        } catch (BookingNotFoundException e) {
            System.err.println("\n❌ " + e.getMessage());
            pauseForUser();
        }
    }

    private static void demonstrateDecoratorPattern() {
        printSectionHeader("Decorator Pattern - Zusatzleistungen Demo");

        System.out.println("Diese Demo zeigt, wie Zusatzleistungen dynamisch zu Zimmern");
        System.out.println("hinzugefügt werden können (Decorator Design Pattern).\n");

        Room basicRoom = new SingleRoom(999, 80.0);
        int nights = 3;

        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           Beispiel: 3 Nächte im Einzelzimmer            ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        System.out.println("1️⃣  BASIS-ZIMMER");
        System.out.println("    " + basicRoom.getDescription());
        System.out.printf("    Preis: %.2f CHF%n%n", basicRoom.calculatePrice(nights));

        Room withBreakfast = new Breakfast(basicRoom);
        System.out.println("2️⃣  + FRÜHSTÜCK");
        System.out.println("    " + withBreakfast.getDescription());
        System.out.printf("    Preis: %.2f CHF%n", withBreakfast.calculatePrice(nights));
        System.out.printf("    (+%.2f CHF)%n%n",
                withBreakfast.calculatePrice(nights) - basicRoom.calculatePrice(nights));

        Room withBreakfastParking = new Parking(new Breakfast(basicRoom));
        System.out.println("3️⃣  + FRÜHSTÜCK + PARKPLATZ");
        System.out.println("    " + withBreakfastParking.getDescription());
        System.out.printf("    Preis: %.2f CHF%n", withBreakfastParking.calculatePrice(nights));
        System.out.printf("    (+%.2f CHF)%n%n",
                withBreakfastParking.calculatePrice(nights) - basicRoom.calculatePrice(nights));

        Room fullPackage = new Wellness(new Parking(new Breakfast(basicRoom)));
        System.out.println("4️⃣  VOLLPAKET (Frühstück + Parkplatz + Wellness)");
        System.out.println("    " + fullPackage.getDescription());
        System.out.printf("    Preis: %.2f CHF%n", fullPackage.calculatePrice(nights));
        System.out.printf("    (+%.2f CHF)%n%n",
                fullPackage.calculatePrice(nights) - basicRoom.calculatePrice(nights));

        System.out.println("✨ Decorator Pattern ermöglicht flexible Kombinationen!");

        pauseForUser();
    }

    private static void quickDemo() {
        printSectionHeader("Schnell-Demo - Kompletter Buchungsablauf");

        System.out.println("Diese Demo zeigt einen kompletten Buchungsablauf:");
        System.out.println("Erstellen → Bestätigen → Check-in → Check-out\n");

        try {
            Customer demoCustomer = new Customer(999, "Demo User", "demo@hotel.com", "+41 79 999 99 99");
            Room demoRoom = roomService.findRoomByNumber(102);

            if (demoRoom == null || !demoRoom.isAvailable()) {
                System.out.println("❌ Demo-Zimmer nicht verfügbar. Bitte wählen Sie ein anderes Zimmer im Hauptmenü.");
                pauseForUser();
                return;
            }

            LocalDate checkIn = LocalDate.now().plusDays(1);
            LocalDate checkOut = LocalDate.now().plusDays(4);

            System.out.println("1️⃣  BUCHUNG ERSTELLEN");
            Booking booking = bookingService.createBooking(checkIn, checkOut, demoRoom, demoCustomer);
            System.out.println("    Status: " + booking.getStatus());
            System.out.println("    ✓ Erfolgreich erstellt\n");
            Thread.sleep(1000);

            System.out.println("2️⃣  BUCHUNG BESTÄTIGEN");
            bookingService.confirmBooking(booking.getBookingId());
            System.out.println("    Status: " + booking.getStatus());
            System.out.println("    ✓ Erfolgreich bestätigt\n");
            Thread.sleep(1000);

            System.out.println("3️⃣  CHECK-IN DURCHFÜHREN");
            bookingService.checkInBooking(booking.getBookingId());
            System.out.println("    Status: " + booking.getStatus());
            System.out.println("    ✓ Gast eingecheckt\n");
            Thread.sleep(1000);

            System.out.println("4️⃣  CHECK-OUT DURCHFÜHREN");
            bookingService.checkOutBooking(booking.getBookingId());
            System.out.println("    Status: " + booking.getStatus());
            System.out.println("    ✓ Gast ausgecheckt\n");

            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("║           ✅ DEMO ERFOLGREICH ABGESCHLOSSEN              ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝");

            System.out.println("\nFinale Buchungs-Details:");
            System.out.println(booking);
            System.out.println("Gesamtpreis: " + booking.getTotalPrice() + " CHF");

        } catch (Exception e) {
            System.err.println("\n❌ Demo-Fehler: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void saveAllData() {
        printSectionHeader("Daten speichern");

        System.out.println("💾 Speichere alle Daten...\n");

        try {
            System.out.print("   Zimmer... ");
            for (Room room : roomService.getAllRooms()) {
                roomRepository.save(room);
            }
            roomRepository.saveAll();
            System.out.println("✓");

            System.out.print("   Buchungen... ");
            for (Booking booking : bookingService.getAllBookings()) {
                bookingRepository.save(booking);
            }
            bookingRepository.saveAll();
            System.out.println("✓");

            System.out.println("\n✅ Alle Daten erfolgreich in CSV-Dateien gespeichert!");
            System.out.println("   - rooms.csv");
            System.out.println("   - bookings.csv");

        } catch (Exception e) {
            System.err.println("\n❌ Fehler beim Speichern: " + e.getMessage());
        }

        pauseForUser();
    }

    private static void showSystemInfo() {
        printSectionHeader("System-Informationen");

        List<Room> allRooms = roomService.getAllRooms();
        List<Room> availableRooms = roomService.getAvailableRooms();
        List<Booking> allBookings = bookingService.getAllBookings();

        System.out.println("📊 STATISTIKEN\n");
        System.out.println("   Zimmer gesamt:        " + allRooms.size());
        System.out.println("   Verfügbare Zimmer:    " + availableRooms.size());
        System.out.println("   Belegte Zimmer:       " + (allRooms.size() - availableRooms.size()));
        System.out.println("   Buchungen gesamt:     " + allBookings.size());

        System.out.println("\n🎨 VERWENDETE DESIGN PATTERNS\n");
        System.out.println("   ✓ Decorator Pattern    (Zusatzleistungen)");
        System.out.println("   ✓ Repository Pattern   (Datenpersistenz)");
        System.out.println("   ✓ Service Layer        (Geschäftslogik)");

        System.out.println("\n⚙️  FEATURES\n");
        System.out.println("   ✓ Vererbung            (Room-Hierarchie)");
        System.out.println("   ✓ Polymorphismus       (calculatePrice Override)");
        System.out.println("   ✓ Interfaces           (Bookable, Repository)");
        System.out.println("   ✓ Exception Handling   (4 Custom Exceptions)");
        System.out.println("   ✓ Validierung          (BookingValidator)");
        System.out.println("   ✓ Clean Architecture   (Model-Service-Repository)");

        System.out.println("\n📚 OBJEKTORIENTIERTE PROGRAMMIERUNG (M320)\n");
        System.out.println("   ✓ Klassen und Objekte");
        System.out.println("   ✓ Abstraktion");
        System.out.println("   ✓ Kapselung");
        System.out.println("   ✓ Vererbung");
        System.out.println("   ✓ Polymorphismus");

        pauseForUser();
    }

    private static Customer selectOrCreateCustomer() {
        System.out.println("\n👤 Kunde auswählen:");
        System.out.println("\n   Beispiel-Kunden:");

        for (int i = 0; i < exampleCustomers.length; i++) {
            System.out.printf("   %d) %s (%s)%n",
                    i + 1,
                    exampleCustomers[i].getName(),
                    exampleCustomers[i].getEmail());
        }

        System.out.println("   " + (exampleCustomers.length + 1) + ") Neuen Kunden erstellen");

        int choice = readIntInput("\n👉 Ihre Wahl: ", 1, exampleCustomers.length + 1);

        if (choice <= exampleCustomers.length) {
            return exampleCustomers[choice - 1];
        } else {
            return createNewCustomer();
        }
    }

    private static Customer createNewCustomer() {
        System.out.println("\n📝 Neuer Kunde:");

        String name = readStringInput("   Name: ");
        String email = readStringInput("   E-Mail: ");
        String phone = readStringInput("   Telefon: ");

        Customer customer = new Customer(customerIdCounter++, name, email, phone);
        System.out.println("\n✓ Kunde erstellt: " + customer.getName());
        return customer;
    }

    private static int readIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("⚠️  Eingabe darf nicht leer sein. Bitte erneut versuchen.");
                    continue;
                }

                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.printf("⚠️  Bitte geben Sie eine Zahl zwischen %d und %d ein.%n", min, max);
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("⚠️  Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
            }
        }
    }

    private static String readStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("⚠️  Eingabe darf nicht leer sein. Bitte erneut versuchen.");
                continue;
            }

            return input;
        }
    }

    private static LocalDate readDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("⚠️  Eingabe darf nicht leer sein. Bitte erneut versuchen.");
                    continue;
                }

                LocalDate date = LocalDate.parse(input, DATE_FORMATTER);
                return date;

            } catch (DateTimeParseException e) {
                System.out.println("⚠️  Ungültiges Datum. Bitte verwenden Sie das Format: TT.MM.JJJJ (z.B. 15.12.2025)");
            }
        }
    }

    private static void printSectionHeader(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-55s║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }

    private static void pauseForUser() {
        System.out.println("\n💡 Drücken Sie Enter um fortzufahren...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static boolean confirmExit() {
        System.out.print("\n❓ Möchten Sie wirklich beenden? (j/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("j") || input.equals("ja") || input.equals("y") || input.equals("yes");
    }

    private static String getRoomTypeName(Room room) {
        if (room instanceof Suite) {
            return "Suite";
        } else if (room instanceof DoubleRoom) {
            return "Doppelzimmer";
        } else if (room instanceof SingleRoom) {
            return "Einzelzimmer";
        } else {
            return "Unbekannt";
        }
    }
}