package d3.currency;

public class RateUnavailableException extends Exception {
    public RateUnavailableException(String message) { super(message); }
    public RateUnavailableException(String message, Throwable cause) { super(message, cause); }
}
