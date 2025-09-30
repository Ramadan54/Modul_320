package m320.v3;

import java.math.BigDecimal;
import java.util.Map;

public class SwissExchange implements StockExchange {
    // Beispielpreise in CHF
    private static final Map<String, BigDecimal> PRICES = Map.of(
            "MSFT", new BigDecimal("120.00"),
            "AAPL", new BigDecimal("130.00"),
            "TSLA", new BigDecimal("200.00")
    );

    @Override public BigDecimal getPrice(String symbol) {
        var p = PRICES.get(symbol.toUpperCase());
        if (p == null) throw new UnknownStockException("Unbekannt an SIX: " + symbol);
        return p;
    }
    @Override public String getName() { return "SIX Swiss (CHF)"; }
}
