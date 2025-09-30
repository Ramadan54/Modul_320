package d3.currency;

public class AppMain {
    public static void main(String[] args) {
        RateProvider provider = new MockRateProvider(); // Mock aktiv
        CurrencyService service = new CurrencyService(provider);
        new ConsoleUI(service).start();
    }
}

