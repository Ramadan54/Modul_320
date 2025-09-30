package m320.v3;

import java.math.BigDecimal;
import java.util.Map;

public class NyseExchange implements StockExchange {
    // Beispielpreise in USD
    private static final Map<String, BigDecimal> PRICES = Map.of(
            "MSFT", new BigDecimal("100.00"),
            "AAPL", new BigDecimal("110.00"),
            "TSLA", new BigDecimal("180.00")
    );

    @Override public BigDecimal getPrice(String symbol) {
        var p = PRICES.get(symbol.toUpperCase());
        if (p == null) throw new UnknownStockException("Unbekannt an NYSE: " + symbol);
        return p;
    }
    @Override public String getName() { return "NYSE (USD)"; }
}
