package d3.currency;

public class InvalidCurrencyException extends Exception {
    public InvalidCurrencyException(String message) { super(message); }
    public InvalidCurrencyException(String message, Throwable cause) { super(message, cause); }
}