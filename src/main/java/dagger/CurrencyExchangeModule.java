package dagger;

import dagger.Module;
import json.CurrencyExchangeService;
import dagger.Provides;
import json.CurrencyExchangeServiceFactory;

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
