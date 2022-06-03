package currencyExchange.dagger;


import javax.inject.Singleton;
import currencyExchange.CurrencyExchangeFrame;
import dagger.Component;

@Singleton
@Component(modules = {CurrencyExchangeModule.class})

public interface CurrencyExchangeComponent {
    CurrencyExchangeFrame getCurrencyExchangeFrame();

}