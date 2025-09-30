package m320.v3;

import java.math.BigDecimal;

/** Vertrag für Börsen: liefert den Preis einer Aktie. */
public interface StockExchange {
    /** Preis für 1 Aktie (gleiche Währung je Börse). */
    BigDecimal getPrice(String symbol) throws UnknownStockException;

    /** Name der Börse (z.B. "NYSE"). */
    String getName();
}
