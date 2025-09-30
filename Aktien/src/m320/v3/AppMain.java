package m320.v3;

import java.util.Scanner;

public class AppMain {
    public static void main(String[] args) {
        Portfolio pf = new Portfolio();
        pf.add("MSFT", 10);
        pf.add("AAPL", 5);
        pf.add("TSLA", 2);

        Scanner in = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("""
                    
                    1) NYSE (USD)
                    2) SIX Swiss (CHF)
                    0) Ende
                    """);
            System.out.print("Börse wählen: ");
            String choice = in.nextLine().trim();
            StockExchange ex = switch (choice) {
                case "1" -> new NyseExchange();
                case "2" -> new SwissExchange();
                case "0" -> { run = false; yield null; }
                default -> null;
            };
            if (!run) break;
            if (ex == null) { System.out.println("Ungültig."); continue; }

            System.out.println("Positionen: " + pf.getPositions());
            try {
                System.out.println(ex.getName() + " → Portfolio-Wert: " + pf.totalValue(ex));
            } catch (UnknownStockException e) {
                System.out.println("Fehler: " + e.getMessage());
            }
        }
        System.out.println("Tschüss!");
    }
}
