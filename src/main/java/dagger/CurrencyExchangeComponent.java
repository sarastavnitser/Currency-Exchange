package dagger;


import javax.inject.Singleton;
import CurrencyExchangeFrame;

@Singleton
@Component(modules = {CurrencyExchangeModule.class})

public interface CurrencyExchangeComponent {
    CurrencyExchangeFrame getCurrencyExchangeFrame();

}