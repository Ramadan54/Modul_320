# 🏨 Hotel Buchungssystem - M320 Projekt

**Autor:** Ramadan Asani  
**Modul:** M320 - Objektorientiert programmieren  
**Version:** 2.0  
**Datum:** November 2025  
**Java Version:** 17.0.12 LTS

---

## 📋 Inhaltsverzeichnis

1. [Projektbeschreibung](#-projektbeschreibung)
2. [Ziele und Anforderungen](#-ziele-und-anforderungen)
3. [Use Cases](#-use-cases)
4. [Architektur](#-architektur)
5. [Design Patterns](#-design-patterns)
6. [OOP-Konzepte](#-oop-konzepte)
7. [Clean Code](#-clean-code)
8. [UML-Diagramme](#-uml-diagramme)
9. [Installation und Start](#-installation-und-start)
10. [Verwendung](#-verwendung)
11. [Technische Details](#-technische-details)
12. [KI-Unterstützung](#-ki-unterstützung)
13. [Fazit und Lernprozess](#-fazit-und-lernprozess)

---

## 📖 Projektbeschreibung

Das **Hotel Buchungssystem** ist eine objektorientierte Java-Anwendung zur Verwaltung von Hotelbuchungen. Das System ermöglicht die Verwaltung von Zimmern, Kunden und Buchungen mit vollständiger Validierung und Fehlerbehandlung.

### 🎯 Hauptziel

Entwicklung eines vollständig objektorientierten Buchungssystems, das moderne Design Patterns implementiert und Clean Code Prinzipien folgt.

---

## ✅ Ziele und Anforderungen

### Muss-Kriterien (Erfüllt ✅)

1. ✅ **Objektorientierte Architektur**
   - 32 selbstgeschriebene Klassen (weit über Minimum von 8)
   - 2.500+ Zeilen Code (weit über Minimum von 800)

2. ✅ **Saubere Trennung**
   - User Interface (InteractiveHotelApp)
   - Geschäftslogik (Service Layer)
   - Persistente Daten (Repository Pattern mit CSV)

3. ✅ **Input-Validierung**
   - Exception Handling bei allen Benutzereingaben
   - 4 Custom Exceptions
   - BookingValidator für Datenvalidierung

4. ✅ **Design Patterns**
   - Decorator Pattern (Zusatzleistungen)
   - Repository Pattern (Datenpersistenz)
   - Service Layer Pattern (Geschäftslogik)

5. ✅ **Interfaces**
   - Bookable
   - AdditionalService
   - Repository<T>

6. ✅ **Vererbungshierarchie**
   - Room → SingleRoom, DoubleRoom, Suite
   - RoomDecorator → Breakfast, Parking, Wellness
   - BookingException → Spezialisierte Exceptions

7. ✅ **Clean Code**
   - Naming Conventions
   - Single Responsibility Principle
   - Kleine Methoden
   - JavaDoc Dokumentation
   - Keine Code-Duplikate

### Nice-to-Have (Implementiert ✅)

- ✅ Interaktives Menüsystem
- ✅ Automatische Schnell-Demo
- ✅ Decorator Pattern Demo
- ✅ Umfangreiche Beispieldaten
- ✅ Vollständige UML-Dokumentation

---

## 🎬 Use Cases

### UC1: Zimmer anzeigen
**Akteur:** Gast, Rezeptionist  
**Beschreibung:** Benutzer kann alle verfügbaren Zimmer mit Details anzeigen  
**Vorbedingung:** System ist initialisiert  
**Nachbedingung:** Liste der Zimmer wird angezeigt

**Ablauf:**
1. Benutzer wählt "Verfügbare Zimmer anzeigen"
2. System lädt alle Zimmer aus RoomService
3. System filtert verfügbare Zimmer
4. System zeigt Zimmer mit Details (Nummer, Typ, Preis, Status)

---

### UC2: Neue Buchung erstellen ⭐ (Hauptprozess)
**Akteur:** Gast, Rezeptionist  
**Beschreibung:** Benutzer erstellt eine neue Zimmerbuchung  
**Vorbedingung:** Mindestens ein Zimmer ist verfügbar  
**Nachbedingung:** Buchung ist erstellt und gespeichert

**Ablauf:**
1. Benutzer wählt "Neue Buchung erstellen"
2. System zeigt verfügbare Zimmer
3. Benutzer wählt ein Zimmer
4. System fordert Kundenauswahl (bestehend oder neu)
5. Benutzer gibt Check-in Datum ein
6. System validiert Datum (nicht in Vergangenheit, korrektes Format)
7. Benutzer gibt Check-out Datum ein
8. System validiert: Check-out nach Check-in
9. System prüft Zimmerverfügbarkeit
10. System erstellt Buchung
11. System setzt Zimmer auf "belegt"
12. System fügt Buchung zum Kunden hinzu
13. System speichert in Repository
14. System zeigt Bestätigung mit Gesamtpreis

**Alternative Abläufe:**
- **6a:** Datum ungültig → Fehlermeldung, zurück zu Schritt 5
- **8a:** Check-out vor Check-in → InvalidBookingDateException, zurück zu Schritt 7
- **9a:** Zimmer nicht verfügbar → RoomNotAvailableException, zurück zu Schritt 2

---

### UC3: Buchung verwalten
**Akteur:** Rezeptionist  
**Beschreibung:** Verwaltet bestehende Buchungen (bestätigen, check-in, check-out, stornieren)  
**Vorbedingung:** Buchung existiert  
**Nachbedingung:** Buchungsstatus wurde geändert

**Ablauf:**
1. Benutzer wählt "Buchung verwalten"
2. System zeigt alle Buchungen mit IDs
3. Benutzer gibt Buchungs-ID ein
4. System lädt Buchung
5. System zeigt Aktionsmenü (bestätigen, check-in, check-out, stornieren)
6. Benutzer wählt Aktion
7. System führt Aktion aus
8. System zeigt Erfolgsmeldung

**Alternative Abläufe:**
- **4a:** Buchung nicht gefunden → BookingNotFoundException

---

### UC4: Zusatzleistungen hinzufügen (Decorator Pattern)
**Akteur:** Gast, Rezeptionist  
**Beschreibung:** Dynamisches Hinzufügen von Services zu Zimmern  
**Vorbedingung:** Zimmer ist ausgewählt  
**Nachbedingung:** Services sind hinzugefügt, Preis angepasst

**Ablauf:**
1. System hat Basis-Zimmer
2. Benutzer wählt Frühstück → System erstellt Breakfast Decorator
3. Benutzer wählt Parkplatz → System erstellt Parking Decorator
4. Benutzer wählt Wellness → System erstellt Wellness Decorator
5. System berechnet Gesamtpreis rekursiv
6. System zeigt finale Beschreibung und Preis

---

### UC5: Daten speichern
**Akteur:** System, Administrator  
**Beschreibung:** Persistiert alle Daten in CSV-Dateien  
**Vorbedingung:** Daten wurden geändert  
**Nachbedingung:** Alle Daten in CSV gespeichert

**Ablauf:**
1. Benutzer wählt "Daten speichern"
2. System iteriert über alle Zimmer
3. System speichert jedes Zimmer in RoomRepository
4. System iteriert über alle Buchungen
5. System speichert jede Buchung in BookingRepository
6. System schreibt rooms.csv
7. System schreibt bookings.csv
8. System zeigt Erfolgsmeldung

---

### UC6: Schnell-Demo ausführen
**Akteur:** Demonstrator, Tester  
**Beschreibung:** Automatischer Durchlauf eines kompletten Buchungszyklus  
**Vorbedingung:** System läuft  
**Nachbedingung:** Kompletter Zyklus durchgeführt

**Ablauf:**
1. System erstellt Demo-Kunde
2. System wählt verfügbares Zimmer
3. System erstellt Buchung → Status: PENDING
4. Pause (1 Sekunde)
5. System bestätigt Buchung → Status: CONFIRMED
6. Pause (1 Sekunde)
7. System führt Check-in durch → Status: CHECKED_IN
8. Pause (1 Sekunde)
9. System führt Check-out durch → Status: CHECKED_OUT
10. System zeigt finale Details

---

### UC7: System-Informationen anzeigen
**Akteur:** Administrator, Entwickler  
**Beschreibung:** Zeigt Statistiken und verwendete Patterns/Konzepte  
**Vorbedingung:** System läuft  
**Nachbedingung:** Informationen angezeigt

---

### Weitere Use Cases

- **UC8:** Alle Buchungen anzeigen
- **UC9:** Alle Zimmer anzeigen (inkl. belegte)
- **UC10:** Decorator Pattern Demo
- **UC11:** Kunde erstellen
- **UC12:** Buchung suchen (nach ID)
- **UC13:** Daten laden (beim Start)
- **UC14:** Exception-Tests durchführen

---

## 🏗️ Architektur

Das System folgt einer **3-Schichten-Architektur**:

```
┌─────────────────────────────────────────┐
│     Presentation Layer (UI)             │
│  - InteractiveHotelApp (Menü)          │
│  - HotelApp (Demo)                      │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│     Business Logic Layer                │
│  - BookingService                       │
│  - RoomService                          │
│  - BookingValidator                     │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│     Data Access Layer                   │
│  - RoomRepository                       │
│  - BookingRepository                    │
│  - CSV Files (rooms.csv, bookings.csv) │
└─────────────────────────────────────────┘
```

### Package-Struktur

```
com.hotelbooking
├── model/                  # Domain-Modelle
│   ├── Room.java           # Abstract Basis-Klasse
│   ├── SingleRoom.java     # Konkrete Zimmer-Typen
│   ├── DoubleRoom.java
│   ├── Suite.java
│   ├── RoomDecorator.java  # Decorator Pattern
│   ├── Breakfast.java
│   ├── Parking.java
│   ├── Wellness.java
│   ├── Booking.java
│   ├── Customer.java
│   ├── BookingStatus.java  # Enum
│   ├── Bookable.java       # Interface
│   └── AdditionalService.java  # Interface
│
├── service/                # Geschäftslogik
│   ├── BookingService.java
│   └── RoomService.java
│
├── repository/             # Datenpersistenz
│   ├── Repository.java     # Generic Interface
│   ├── RoomRepository.java
│   └── BookingRepository.java
│
├── exception/              # Custom Exceptions
│   ├── BookingException.java
│   ├── BookingNotFoundException.java
│   ├── InvalidBookingDateException.java
│   └── RoomNotAvailableException.java
│
├── validator/              # Validierung
│   └── BookingValidator.java
│
├── util/                   # Hilfklassen
│   └── Result.java         # Generic Result Wrapper
│
└── InteractiveHotelApp.java  # Hauptprogramm (UI)
```

---

## 🎨 Design Patterns

### 1. Decorator Pattern ⭐

**Zweck:** Dynamisches Hinzufügen von Zusatzleistungen zu Zimmern

**Implementierung:**
```
Room (Interface: Bookable)
  ↑
  ├── SingleRoom
  ├── DoubleRoom
  ├── Suite
  └── RoomDecorator (Abstract)
        ↑
        ├── Breakfast
        ├── Parking
        └── Wellness
```

**Begründung:**
- ✅ Vermeidet Explosion von Subklassen (z.B. "SingleRoomWithBreakfast", "SingleRoomWithBreakfastAndParking", etc.)
- ✅ Erlaubt beliebige Kombinationen zur Laufzeit
- ✅ Folgt Open/Closed Principle (offen für Erweiterung, geschlossen für Änderung)

**Beispiel:**
```java
Room basicRoom = new SingleRoom(101, 80.0);
Room withBreakfast = new Breakfast(basicRoom);
Room withAll = new Wellness(new Parking(new Breakfast(basicRoom)));

// Preis wird rekursiv berechnet:
// 80 + 15 (Breakfast) + 10 (Parking) + 25 (Wellness) = 130 pro Nacht
double price = withAll.calculatePrice(3); // 390 CHF für 3 Nächte
```

---

### 2. Repository Pattern ⭐

**Zweck:** Trennung von Geschäftslogik und Datenzugriff

**Implementierung:**
```java
public interface Repository<T> {
    void save(T entity);
    T findById(int id);
    List<T> findAll();
    void delete(int id);
    void saveAll();
    void loadAll();
}
```

**Implementiert von:**
- RoomRepository
- BookingRepository

**Begründung:**
- ✅ Datenzugriff ist austauschbar (CSV → Datenbank ohne Änderung der Services)
- ✅ Testbarkeit durch Mock-Repositories
- ✅ Single Responsibility: Services kennen keine Datenbank-Details

**Beispiel:**
```java
RoomRepository roomRepo = new RoomRepository("rooms.csv");
roomRepo.save(room);
roomRepo.saveAll(); // Persistiert in CSV
```

---

### 3. Service Layer Pattern

**Zweck:** Kapselung der Geschäftslogik

**Implementierung:**
- BookingService: Alle Booking-Operationen
- RoomService: Alle Room-Operationen

**Begründung:**
- ✅ UI kennt keine Domain-Logik
- ✅ Wiederverwendbarkeit (Service kann von verschiedenen UIs genutzt werden)
- ✅ Zentrale Validierung

**Beispiel:**
```java
BookingService service = new BookingService();
Booking booking = service.createBooking(checkIn, checkOut, room, customer);
// Service validiert, erstellt, und verwaltet Buchung
```

---

## 🧬 OOP-Konzepte

### 1. Vererbung (Inheritance)

**Implementiert in:**

**Room-Hierarchie:**
```
Room (Abstract)
  ├── SingleRoom
  ├── DoubleRoom
  └── Suite
```

**Decorator-Hierarchie:**
```
RoomDecorator (Abstract)
  ├── Breakfast
  ├── Parking
  └── Wellness
```

**Exception-Hierarchie:**
```
BookingException
  ├── BookingNotFoundException
  ├── InvalidBookingDateException
  └── RoomNotAvailableException
```

**Begründung:**
- ✅ Code-Wiederverwendung (gemeinsame Funktionalität in Basis-Klasse)
- ✅ Erweiterbarkeit (neue Zimmer-Typen einfach hinzufügbar)
- ✅ Polymorphismus ermöglicht (siehe unten)

---

### 2. Polymorphismus

**Beispiel 1: calculatePrice()**
```java
Room room = new SingleRoom(101, 80.0);
double price = room.calculatePrice(3); // SingleRoom-Implementation

room = new DoubleRoom(201, 120.0, true);
price = room.calculatePrice(3); // DoubleRoom-Implementation

room = new Suite(301, 250.0, 4, true);
price = room.calculatePrice(3); // Suite-Implementation
```

**Beispiel 2: Mit Decorator**
```java
Room room = new Breakfast(new SingleRoom(101, 80.0));
double price = room.calculatePrice(3); 
// Ruft Breakfast.calculatePrice() → SingleRoom.calculatePrice()
```

**Vorteil:**
- ✅ Gleiche Methode, unterschiedliches Verhalten
- ✅ Zur Laufzeit entschieden (Dynamic Binding)

---

### 3. Abstraktion

**Abstract Classes:**
- `Room` - Definiert Vertrag, zwingt Subklassen zur Implementation
- `RoomDecorator` - Basis für alle Decorators

**Interfaces:**
- `Bookable` - Vertrag für buchbare Objekte
- `AdditionalService` - Vertrag für Services
- `Repository<T>` - Generic Vertrag für Persistenz

**Vorteil:**
- ✅ Erzwingt Konsistenz
- ✅ Programm gegen Interface, nicht Implementation

---

### 4. Kapselung (Encapsulation)

**Beispiel:**
```java
public class Booking {
    private int bookingId;           // Private!
    private LocalDate checkInDate;   // Private!
    private BookingStatus status;    // Private!
    
    public int getBookingId() {      // Public Getter
        return bookingId;
    }
    
    public void confirm() {          // Public, aber kontrolliert
        if (status == BookingStatus.PENDING) {
            this.status = BookingStatus.CONFIRMED;
        }
    }
}
```

**Vorteil:**
- ✅ Daten sind geschützt
- ✅ Kontrollierter Zugriff über Methoden
- ✅ Interne Änderungen haben keine Auswirkung auf Außenwelt

---

### 5. Interfaces

**Verwendete Interfaces:**

1. **Bookable**
   - Vertrag für alles was buchbar ist
   - Implementiert von Room und RoomDecorator

2. **AdditionalService**
   - Vertrag für Zusatzleistungen
   - Implementiert von Decorators

3. **Repository<T>**
   - Generic Interface für Persistenz
   - Implementiert von RoomRepository und BookingRepository

**Vorteil:**
- ✅ Lose Kopplung
- ✅ Austauschbare Implementierungen
- ✅ Testbarkeit (Mocks)

---

## 📏 Clean Code

### 1. Naming Conventions

**Klassen:**
```java
✅ BookingService          // PascalCase, beschreibend
✅ RoomRepository          // Zeigt Zweck klar
✅ InvalidBookingDateException  // Selbsterklärend
```

**Methoden:**
```java
✅ createBooking()         // Verb, zeigt Action
✅ isAvailable()           // Boolean mit "is"
✅ calculatePrice()        // Berechnung deutlich
```

**Variablen:**
```java
✅ checkInDate            // Klar, nicht "date1"
✅ roomNumber             // Nicht "room" oder "nr"
✅ bookingStatus          // Vollständig, nicht "status"
```

---

### 2. Single Responsibility Principle (SRP)

**Beispiele:**

```java
// ✅ Jede Klasse hat EINE Verantwortung:
BookingService      → Verwaltet Buchungen
RoomService         → Verwaltet Zimmer
BookingRepository   → Speichert/lädt Buchungen
BookingValidator    → Validiert Buchungsdaten
```

**Anti-Beispiel (vermieden):**
```java
// ❌ NICHT SO:
BookingService {
    createBooking()
    saveToDatabase()    // Zu viele Verantwortungen!
    sendEmail()
    validateInput()
}
```

---

### 3. No Hard Coding

**Beispiele:**

```java
// ❌ Schlecht:
String error = "Room 201 is not available";

// ✅ Gut:
String error = "Room #" + roomNumber + " is not available";
```

```java
// ❌ Schlecht:
if (dayOfWeek == 7) { ... }

// ✅ Gut (mit Enum):
if (status == BookingStatus.CONFIRMED) { ... }
```

---

### 4. Kleine Methoden

**Beispiele:**

```java
// ✅ Methode macht EINE Sache (3-10 Zeilen):
public void confirm() {
    if (status == BookingStatus.PENDING) {
        this.status = BookingStatus.CONFIRMED;
    }
}

// ✅ Aufgeteilt in kleine Methoden:
private static void runMainMenu() {
    printMainMenu();
    int choice = readIntInput();
    handleMenuChoice(choice);
}
```

---

### 5. JavaDoc Dokumentation

**Beispiel:**
```java
/**
 * Creates a new booking with validation
 * 
 * @param checkIn Check-in date
 * @param checkOut Check-out date
 * @param room Room to book
 * @param customer Customer making booking
 * @return Created booking
 * @throws InvalidBookingDateException if dates are invalid
 * @throws RoomNotAvailableException if room is not available
 */
public Booking createBooking(LocalDate checkIn, LocalDate checkOut, 
                              Room room, Customer customer) { ... }
```

---

### 6. Keine Code-Duplikate (DRY - Don't Repeat Yourself)

**Beispiel:**

```java
// ✅ Wiederverwendbare Methode:
private static int readIntInput(String prompt, int min, int max) {
    // Validierung einmal implementiert, überall nutzbar
}

// Verwendet an mehreren Stellen:
int choice = readIntInput("Ihre Wahl: ", 0, 9);
int roomNumber = readIntInput("Zimmer-Nummer: ", 1, 100);
```

---

## 📊 UML-Diagramme

### Klassendiagramm

Siehe separate Datei: `HOTEL_UML_KLASSENDIAGRAMM.txt`

**Zeigt:**
- Alle 32 Klassen mit Attributen und Methoden
- Alle Beziehungen (Vererbung, Implementierung, Assoziation)
- Packages (model, service, repository, exception, validator, main)
- Design Patterns visuell dargestellt

---

### Sequenzdiagramm: UC2 - Neue Buchung erstellen

```
Benutzer → InteractiveHotelApp: Wählt "Neue Buchung" (Option 3)
InteractiveHotelApp → RoomService: getAvailableRooms()
RoomService → InteractiveHotelApp: List<Room>
InteractiveHotelApp → Benutzer: Zeigt verfügbare Zimmer

Benutzer → InteractiveHotelApp: Wählt Zimmer (z.B. 1)
InteractiveHotelApp → RoomService: findRoomByNumber(201)
RoomService → Room: isAvailable()
Room → RoomService: true
RoomService → InteractiveHotelApp: Room

Benutzer → InteractiveHotelApp: Wählt Kunde (z.B. Max Mustermann)
Benutzer → InteractiveHotelApp: Check-in: 15.12.2025
InteractiveHotelApp → InteractiveHotelApp: readDateInput() - Validierung
Benutzer → InteractiveHotelApp: Check-out: 20.12.2025
InteractiveHotelApp → InteractiveHotelApp: readDateInput() - Validierung

InteractiveHotelApp → BookingService: createBooking(checkIn, checkOut, room, customer)
BookingService → BookingService: validateDates()
BookingService → Room: isAvailable()
Room → BookingService: true
BookingService → Booking: new Booking(...)
BookingService → Room: setAvailable(false)
BookingService → Customer: addBooking(booking)
BookingService → InteractiveHotelApp: booking

InteractiveHotelApp → BookingRepository: save(booking)
InteractiveHotelApp → RoomRepository: save(room)
InteractiveHotelApp → Benutzer: ✅ Buchung erfolgreich + Details
```

---

## 🚀 Installation und Start

### Voraussetzungen

- **Java:** Version 11 oder höher (getestet mit Java 17.0.12 LTS)
- **Keine** externen Dependencies
- **Optional:** Git für Repository-Verwaltung

### Projekt klonen

```bash
git clone https://github.com/[username]/Hotel-Buchungssystem.git
cd Hotel-Buchungssystem/Level4_HotelBookingSystem
```

### Kompilieren

**Windows (CMD):**
```cmd
javac -d bin -sourcepath src\main\java src\main\java\com\hotelbooking\InteractiveHotelApp.java src\main\java\com\hotelbooking\*\*.java
```

**Linux/Mac (Terminal):**
```bash
javac -d bin -sourcepath src/main/java $(find src/main/java -name "*.java")
```

**Oder mit Start-Skript:**
```bash
# Windows:
start.bat

# Linux/Mac:
./start.sh
```

---

### Starten

**Interaktive Version:**
```bash
java -cp bin com.hotelbooking.InteractiveHotelApp
```

**Original Demo-Version:**
```bash
java -cp bin com.hotelbooking.HotelApp
```

---

## 💻 Verwendung

### Schnellstart (30 Sekunden)

1. Programm starten
2. Drücke `7` → **Schnell-Demo**
3. Beobachte automatischen Buchungsablauf
4. Drücke `0` → Beenden

### Eigene Buchung erstellen (2 Minuten)

1. Programm starten
2. Drücke `3` → **Neue Buchung erstellen**
3. Drücke `1` → Zimmer 101 (Einzelzimmer)
4. Drücke `1` → Max Mustermann (Beispiel-Kunde)
5. Eingabe: `15.12.2025` → Check-in
6. Eingabe: `20.12.2025` → Check-out
7. ✅ **Buchung erfolgreich!**

### Decorator Pattern testen

1. Drücke `6` → **Zusatzleistungen Demo**
2. System zeigt:
   - Basis-Zimmer: 240 CHF (3 Nächte)
   - + Frühstück: 285 CHF
   - + Frühstück + Parkplatz: 315 CHF
   - + Vollpaket: 390 CHF

### Buchung verwalten

1. Drücke `4` → **Alle Buchungen anzeigen**
2. Notiere Buchungs-ID
3. Drücke `5` → **Buchung verwalten**
4. Gib ID ein
5. Wähle Aktion:
   - `1` → Bestätigen
   - `2` → Check-in
   - `3` → Check-out
   - `4` → Stornieren

---

## 🔧 Technische Details

### Statistiken

- **Klassen:** 32
- **Interfaces:** 3
- **Enums:** 1
- **Exceptions:** 4
- **Zeilen Code:** 2.500+
- **Packages:** 7
- **Design Patterns:** 3

### Verwendete Java Features

- ✅ **Java 8+:**
  - LocalDate (java.time)
  - Lambda Expressions (in SearchFilter)
  - Streams (intern in Collections)

- ✅ **OOP:**
  - Abstract Classes
  - Interfaces
  - Generics (Repository<T>, Result<T>)
  - Enums

- ✅ **Exception Handling:**
  - Custom Exceptions
  - Try-Catch Blocks
  - Exception Chaining

### Datenpersistenz

**Format:** CSV (Comma-Separated Values)

**Dateien:**
- `rooms.csv` - Zimmer-Daten
- `bookings.csv` - Buchungs-Daten

**Vorteil:**
- ✅ Einfach austauschbar gegen Datenbank
- ✅ Lesbar ohne Tools
- ✅ Keine externe Dependency

---

## 🤖 KI-Unterstützung

### Wo KI verwendet wurde

**1. Clean Code Kommentare (70%):**
- JavaDoc Dokumentation
- Erklärung von Design Patterns
- Best Practices Beschreibungen

**2. Code-Struktur Reviews (20%):**
- Überprüfung der Exception-Hierarchie
- Validierung des Repository Patterns
- Optimierung der Service-Klassen

**3. Komplexe Logik (10%):**
- Decorator Pattern Implementation
- Generic Repository Design
- Input-Validierung mit Scanner

### Was NICHT mit KI gemacht wurde

- ❌ Grundlegende Klassen-Struktur (manuell)
- ❌ Domain-Modelle (Booking, Room, Customer)
- ❌ Geschäftslogik (BookingService, RoomService)
- ❌ UI-Logik (InteractiveHotelApp Menü-System)

### KI-Tools

- **Claude (Anthropic)** - Code Reviews, Clean Code Kommentare
- **GitHub Copilot** - Auto-Completion (minimal)

### Deklaration im Code

Alle Klassen enthalten Kommentare:
```java
/**
 * KI-UNTERSTÜTZUNG:
 * [Beschreibung wo und wie KI geholfen hat]
 */
```

---

## 🎓 Fazit und Lernprozess

### Was ich gelernt habe

**1. Design Patterns in der Praxis:**
- Decorator Pattern ist mächtiger als gedacht - ermöglicht unendlich viele Kombinationen
- Repository Pattern macht Code wartbar - Umstieg auf Datenbank wäre einfach
- Service Layer entkoppelt UI von Logik perfekt

**2. Clean Code ist wichtig:**
- Sprechende Namen machen Code selbst-dokumentierend
- Kleine Methoden (SRP) sind leichter zu testen und zu verstehen
- Keine Magic Numbers - immer mit Konstanten/Enums arbeiten

**3. Exception Handling:**
- Custom Exceptions kommunizieren klar was schief ging
- Exception-Hierarchie hilft beim gezielten Fangen
- Validation sollte früh passieren (fail-fast)

**4. OOP-Konzepte:**
- Vererbung spart Code, aber nicht übertreiben
- Interfaces ermöglichen Austauschbarkeit
- Polymorphismus macht Code flexibel

**5. Generics:**
- Repository<T> macht Code wiederverwendbar
- Type Safety zur Compile-Zeit ist Gold wert
- Generic Methods (Result<T>) reduzieren Boilerplate

### Herausforderungen

**1. Decorator Pattern Verständnis:**
- Anfangs kompliziert: "Warum nicht einfach Subklassen?"
- Nach Implementation: Genial! Beliebige Kombinationen möglich
- Gelernt: Rekursive Strukturen durchdenken

**2. CSV-Persistenz:**
- Herausforderung: Objekte in CSV serialisieren
- Lösung: Einfache toString() / parse() Logik
- Gelernt: Trennung von Speicher-Format und Domain-Logik

**3. Input-Validierung:**
- Problem: Scanner-Input kann alles sein
- Lösung: Wrapper-Methoden mit Try-Catch
- Gelernt: Defensive Programmierung ist wichtig

**4. UML-Diagramme:**
- Herausforderung: Komplexität visuell darstellen
- Lösung: Mehrere Diagramme (vollständig + vereinfacht)
- Gelernt: UML hilft beim Design vor der Implementation

### Was ich anders machen würde

**1. Früher mit UML starten:**
- Hätte Design-Entscheidungen früher visualisiert
- Weniger Refactoring nötig gewesen

**2. TDD (Test-Driven Development):**
- Tests vor Implementation schreiben
- Hätte Bugs früher gefunden

**3. Mehr Logging:**
- Debugging wäre einfacher gewesen
- Produktions-Code sollte immer Logging haben

### Ausblick

**Mögliche Erweiterungen:**
- Web-UI mit Spring Boot
- Echte Datenbank (PostgreSQL)
- REST API für Mobile Apps
- Mehrsprachigkeit (i18n)
- Reporting (PDF-Rechnungen)
- Email-Benachrichtigungen
- Online-Zahlung Integration

### Persönliches Fazit

Dieses Projekt hat mir gezeigt, wie wichtig **saubere Architektur** und **Design Patterns** sind. Am Anfang erschien vieles überkompliziert ("Warum nicht einfacher?"), aber im Lauf des Projekts wurde klar: **Erweiterbarkeit und Wartbarkeit** sind Gold wert.

Die **KI-Unterstützung** war hilfreich für Clean Code Kommentare und Reviews, aber das **grundlegende Verständnis** von OOP und Design Patterns musste ich selbst aufbauen. KI ist ein Tool, kein Ersatz für Lernen.

**Wichtigste Erkenntnis:** Guter Code ist Code, den man in 6 Monaten noch versteht! 🎯

---

## 📞 Kontakt

**Autor:** Ramadan Asani  
**E-Mail:** [deine-email]  
**GitHub:** [dein-github]  
**Modul:** M320 - Objektorientiert programmieren  

---

## 📄 Lizenz

Dieses Projekt wurde als Teil des Moduls M320 erstellt und dient ausschließlich zu Bildungszwecken.

---

**Erstellt mit ❤️ und Clean Code**

*"Any fool can write code that a computer can understand. Good programmers write code that humans can understand."* - Martin Fowler

---

**Ende der Dokumentation**
