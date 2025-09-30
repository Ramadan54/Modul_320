package d3.currency;

import java.math.BigDecimal;
import java.util.Scanner;

public class    ConsoleUI {
    private final CurrencyService service;
    private final Scanner in = new Scanner(System.in);

    public ConsoleUI(CurrencyService service) {
        this.service = service;
    }

    public void start() {
        System.out.println("=== Currency Converter (Mock) ===");
        boolean run = true;
        while (run) {
            System.out.println("\n1) Umrechnen  0) Ende");
            switch (read("Auswahl")) {
                case "1" -> convertFlow();
                case "0" -> run = false;
                default  -> System.out.println("Ungültig.");
            }
        }
        System.out.println("Ciao");
    }

    private void convertFlow() {
        try {
            String from = read("Von (CHF/EUR/USD/GBP)");
            String to   = read("Nach (CHF/EUR/USD/GBP)");
            BigDecimal amount = new BigDecimal(read("Betrag"));

            BigDecimal result = service.convert(from, to, amount);
            System.out.println(amount + " " + from.toUpperCase()
                    + " = " + result + " " + to.toUpperCase());
        } catch (NumberFormatException e) {
            System.out.println("Fehler: Betrag ist keine Zahl.");
        } catch (NegativeAmountException | InvalidCurrencyException | RateUnavailableException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private String read(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }
}
