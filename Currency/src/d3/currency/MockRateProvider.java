package d3.currency;

import java.math.BigDecimal;
import java.util.Map;

public class MockRateProvider implements RateProvider {
    // Beispielkurse relativ zu CHF (stabil/offline)
    private static final Map<String, BigDecimal> BASE_CHF = Map.of(
            "CHF", BigDecimal.valueOf(1.00),
            "EUR", BigDecimal.valueOf(1.02),
            "USD", BigDecimal.valueOf(1.10),
            "GBP", BigDecimal.valueOf(0.88)
    );

    @Override
    public BigDecimal getRate(String from, String to)
            throws InvalidCurrencyException {
        String f = from.toUpperCase();
        String t = to.toUpperCase();

        if (!BASE_CHF.containsKey(f) || !BASE_CHF.containsKey(t)) {
            throw new InvalidCurrencyException("Unbekannte Währung: " + f + " oder " + t);
        }
        if (f.equals(t)) return BigDecimal.ONE;

        // 1 FROM = (CHF/FROM) * (TO/CHF)
        var chfPerFrom = BigDecimal.ONE.divide(BASE_CHF.get(f), 8, java.math.RoundingMode.HALF_UP);
        var toPerChf   = BASE_CHF.get(t);
        return chfPerFrom.multiply(toPerChf);
    }
}