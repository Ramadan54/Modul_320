package d3.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class CurrencyService {
    private final RateProvider rateProvider;
    private static final Set<String> SUPPORTED = Set.of("CHF","EUR","USD","GBP");

    public CurrencyService(RateProvider rateProvider) {
        this.rateProvider = rateProvider;
    }

    public BigDecimal convert(String from, String to, BigDecimal amount)
            throws NegativeAmountException, InvalidCurrencyException, RateUnavailableException {

        if (amount == null || amount.signum() < 0)
            throw new NegativeAmountException("Betrag darf nicht negativ sein.");

        String f = from.toUpperCase();
        String t = to.toUpperCase();

        if (!SUPPORTED.contains(f) || !SUPPORTED.contains(t))
            throw new InvalidCurrencyException("Unterstützt sind: " + SUPPORTED);

        BigDecimal rate = rateProvider.getRate(f, t);
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
