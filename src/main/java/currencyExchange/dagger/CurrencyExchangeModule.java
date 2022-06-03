package currencyExchange.dagger;

import dagger.Module;
import currencyExchange.json.CurrencyExchangeService;
import dagger.Provides;
import currencyExchange.json.CurrencyExchangeServiceFactory;

import javax.inject.Singleton;


@Module
public class CurrencyExchangeModule {

    @Singleton
    @Provides
    public CurrencyExchangeService providesCurrencyExchangeService(
            CurrencyExchangeServiceFactory factory
    ){
        return factory.getInstance();
    }


}
