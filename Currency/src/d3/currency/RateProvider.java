package d3.currency;

import java.math.BigDecimal;

public interface RateProvider {
    BigDecimal getRate(String from, String to)
            throws InvalidCurrencyException, RateUnavailableException;
}
