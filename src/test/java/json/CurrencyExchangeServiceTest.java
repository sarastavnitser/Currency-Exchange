package json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyExchangeServiceTest {

    @Test
    void getCurrencyExchange() {
        // given
        CurrencyExchangeServiceFactory factory = new CurrencyExchangeServiceFactory();
        CurrencyExchangeService service = factory.getInstance();

        // when
        CurrencyExchange currencyExchange = service.getCurrencyExchange(1.0, "USD", "USD").blockingGet();

        // then
        assertTrue(currencyExchange.getRate() > 0);
        assertTrue(currencyExchange.getResult() > 0);
        assertNotNull(currencyExchange.getDate());
    }

    @Test
    void getCurrencySymbols() {
        // given
        CurrencyExchangeServiceFactory factory = new CurrencyExchangeServiceFactory();
        CurrencyExchangeService service = factory.getInstance();

        // when
        CurrencyExchange currencyExchange = service.getCurrencySymbols().blockingGet();

        // then
        assertNotNull(currencyExchange.getSymbols());
    }
}