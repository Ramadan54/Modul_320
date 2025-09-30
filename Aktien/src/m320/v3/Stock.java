package m320.v3;

public class Stock {
    private final String symbol;   // z.B. "MSFT"
    private int quantity;          // Anzahl Stück

    public Stock(String symbol, int quantity) {
        if (symbol == null || symbol.isBlank()) throw new IllegalArgumentException("Symbol fehlt");
        if (quantity <= 0) throw new IllegalArgumentException("Anzahl > 0 nötig");
        this.symbol = symbol.toUpperCase();
        this.quantity = quantity;
    }

    public String getSymbol()  { return symbol; }
    public int getQuantity()   { return quantity; }
    public void add(int more)  { if (more <= 0) throw new IllegalArgumentException(); quantity += more; }

    @Override public String toString() { return symbol + " x" + quantity; }
}
