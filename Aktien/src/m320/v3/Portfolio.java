package m320.v3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Portfolio {
    private final List<Stock> positions = new ArrayList<>();

    /** Fügt hinzu; gleiche Symbole werden zusammengezogen. */
    public void add(String symbol, int qty) {
        for (Stock s : positions) {
            if (s.getSymbol().equalsIgnoreCase(symbol)) { s.add(qty); return; }
        }
        positions.add(new Stock(symbol, qty));
    }

    public List<Stock> getPositions() { return List.copyOf(positions); }

    /** Wert des Portfolios mit *beliebiger* Börse (Polymorphismus!). */
    public BigDecimal totalValue(StockExchange exchange) {
        BigDecimal total = BigDecimal.ZERO;
        for (Stock s : positions) {
            BigDecimal price = exchange.getPrice(s.getSymbol());
            total = total.add(price.multiply(BigDecimal.valueOf(s.getQuantity())));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
